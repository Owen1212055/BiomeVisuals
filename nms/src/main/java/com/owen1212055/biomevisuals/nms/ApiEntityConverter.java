package com.owen1212055.biomevisuals.nms;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import com.owen1212055.biomevisuals.api.types.biome.effect.AdditionSound;
import com.owen1212055.biomevisuals.api.types.biome.effect.AmbientParticle;
import com.owen1212055.biomevisuals.api.types.biome.effect.AmbientSound;
import com.owen1212055.biomevisuals.api.types.biome.effect.MoodSound;
import com.owen1212055.biomevisuals.api.types.biome.effect.Music;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_19_R3.CraftParticle;
import org.bukkit.craftbukkit.v1_19_R3.CraftSound;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;

import java.lang.reflect.Field;
import java.util.Objects;

public class ApiEntityConverter {

    private static final Field probabilityField;
    static {
        try {
            probabilityField = AmbientParticleSettings.class.getDeclaredField(Mappings.AMBIENT_PARTICLE_PROBABILITY);
            probabilityField.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonElement serialize(AdditionSound sound) {
        AmbientAdditionsSettings settings = new AmbientAdditionsSettings(Holder.direct(CraftSound.getSoundEffect(sound.getSoundEvent())), sound.getTickChance());

        return AmbientAdditionsSettings.CODEC.encode(settings, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().orThrow();
    }

    public static AdditionSound deserializeAdditionSound(JsonElement sound) {
        AmbientAdditionsSettings settings = AmbientAdditionsSettings.CODEC.decode(JsonOps.INSTANCE, sound).map(Pair::getFirst).result().orElseThrow();

        return AdditionSound.of(Objects.requireNonNull(nmsSoundEventToBukkit(settings.getSoundEvent().value())), settings.getTickChance());
    }

    public static JsonElement serialize(AmbientParticle particle) {
        AmbientParticleSettings settings = new AmbientParticleSettings(CraftParticle.toNMS(particle.getParticle(), particle.getData()), particle.getProbability());

        return AmbientParticleSettings.CODEC.encode(settings, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().orThrow();
    }

    public static AmbientParticle deserializeAmbientParticle(JsonElement particle) {
        AmbientParticleSettings settings = AmbientParticleSettings.CODEC.decode(JsonOps.INSTANCE, particle).map(Pair::getFirst).result().orElseThrow();

        try {
            return AmbientParticle.of(CraftParticle.toBukkit(settings.getOptions()), (float) probabilityField.get(settings));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonElement serialize(AmbientSound ambientSound) {
        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(new ResourceLocation(ambientSound.getSoundEvent().getKey().toString()));

        return SoundEvent.CODEC.encode(Holder.direct(soundEvent), JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().orThrow();
    }

    public static AmbientSound deserializeAmbientSound(JsonElement ambientSound) {
        Holder<SoundEvent> soundEvent = SoundEvent.CODEC.decode(JsonOps.INSTANCE, ambientSound).map(Pair::getFirst).result().orElseThrow();

        return AmbientSound.of(Objects.requireNonNull(nmsSoundEventToBukkit(soundEvent.value())));
    }

    public static JsonElement serialize(MoodSound moodSound) {
        AmbientMoodSettings settings = new AmbientMoodSettings(Holder.direct(CraftSound.getSoundEffect(moodSound.getSoundEvent())), moodSound.getTickDelay(), moodSound.getBlockSearchExtent(), moodSound.getSoundPositionOffset());

        return AmbientMoodSettings.CODEC.encode(settings, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().orThrow();
    }

    public static MoodSound deserializeMoodSound(JsonElement moodSound) {
        AmbientMoodSettings settings = AmbientMoodSettings.CODEC.decode(JsonOps.INSTANCE, moodSound).map(Pair::getFirst).result().orElseThrow();

        return MoodSound.of(Objects.requireNonNull(nmsSoundEventToBukkit(settings.getSoundEvent().value())), settings.getTickDelay(), settings.getBlockSearchExtent(), settings.getSoundPositionOffset());
    }

    public static JsonElement serialize(Music music) {
        net.minecraft.sounds.Music nmsMusic = new net.minecraft.sounds.Music(Holder.direct(CraftSound.getSoundEffect(music.getSoundEvent())), music.getMinDelay(), music.getMaxDelay(), music.replaceCurrentMusic());

        return net.minecraft.sounds.Music.CODEC.encode(nmsMusic, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().orThrow();
    }

    public static Music deserializeMusic(JsonElement music) {
        net.minecraft.sounds.Music nmsMusic = net.minecraft.sounds.Music.CODEC.decode(JsonOps.INSTANCE, music).map(Pair::getFirst).result().orElseThrow();

        return Music.of(Objects.requireNonNull(nmsSoundEventToBukkit(nmsMusic.getEvent().value())), nmsMusic.getMinDelay(), nmsMusic.getMaxDelay(), nmsMusic.replaceCurrentMusic());
    }

    // Workaround method for avoiding NPEs thrown when CraftSound#getBukkit tries to access the vanilla
    // registries (maybe due to async context or being called too early?)
    private static Sound nmsSoundEventToBukkit(SoundEvent soundEvent) {
        return Registry.SOUNDS.get(Objects.requireNonNull(CraftNamespacedKey.fromMinecraft(soundEvent.getLocation())));
    }
}
