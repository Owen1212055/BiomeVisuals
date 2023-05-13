package com.owen1212055.biomevisuals.nms;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.owen1212055.biomevisuals.api.types.biome.effect.*;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
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

        return encode(AmbientAdditionsSettings.CODEC, settings);
    }

    public static AdditionSound deserializeAdditionSound(JsonElement sound) {
        AmbientAdditionsSettings settings = decode(AmbientAdditionsSettings.CODEC, sound);

        return AdditionSound.of(Objects.requireNonNull(nmsSoundEventToBukkit(settings.getSoundEvent().value())), settings.getTickChance());
    }

    public static JsonElement serialize(AmbientParticle particle) {
        ParticleOptions options = decode(ParticleTypes.CODEC, particle.getParticle());
        AmbientParticleSettings settings = new AmbientParticleSettings(options, particle.getProbability());

        return encode(AmbientParticleSettings.CODEC, settings);
    }

    public static AmbientParticle deserializeAmbientParticle(JsonElement particle) {
        AmbientParticleSettings settings = decode(AmbientParticleSettings.CODEC, particle);

        try {
            return AmbientParticle.of((float) probabilityField.get(settings), (JsonObject) encode(ParticleTypes.CODEC, settings.getOptions()));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonElement serialize(AmbientSound ambientSound) {
        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(new ResourceLocation(ambientSound.getSoundEvent().getKey().toString()));

        return encode(SoundEvent.CODEC, Holder.direct(soundEvent));
    }

    public static AmbientSound deserializeAmbientSound(JsonElement ambientSound) {
        Holder<SoundEvent> soundEvent = decode(SoundEvent.CODEC, ambientSound);

        return AmbientSound.of(Objects.requireNonNull(nmsSoundEventToBukkit(soundEvent.value())));
    }

    public static JsonElement serialize(MoodSound moodSound) {
        AmbientMoodSettings settings = new AmbientMoodSettings(Holder.direct(CraftSound.getSoundEffect(moodSound.getSoundEvent())), moodSound.getTickDelay(), moodSound.getBlockSearchExtent(), moodSound.getSoundPositionOffset());

        return encode(AmbientMoodSettings.CODEC, settings);
    }

    public static MoodSound deserializeMoodSound(JsonElement moodSound) {
        AmbientMoodSettings settings = decode(AmbientMoodSettings.CODEC, moodSound);

        return MoodSound.of(Objects.requireNonNull(nmsSoundEventToBukkit(settings.getSoundEvent().value())), settings.getTickDelay(), settings.getBlockSearchExtent(), settings.getSoundPositionOffset());
    }

    public static JsonElement serialize(Music music) {
        net.minecraft.sounds.Music nmsMusic = new net.minecraft.sounds.Music(Holder.direct(CraftSound.getSoundEffect(music.getSoundEvent())), music.getMinDelay(), music.getMaxDelay(), music.replaceCurrentMusic());

        return encode(net.minecraft.sounds.Music.CODEC, nmsMusic);
    }

    public static Music deserializeMusic(JsonElement music) {
        net.minecraft.sounds.Music nmsMusic = decode(net.minecraft.sounds.Music.CODEC, music);

        return Music.of(Objects.requireNonNull(nmsSoundEventToBukkit(nmsMusic.getEvent().value())), nmsMusic.getMinDelay(), nmsMusic.getMaxDelay(), nmsMusic.replaceCurrentMusic());
    }

    // Workaround method for avoiding NPEs thrown when CraftSound#getBukkit tries to access the vanilla
    // registries (maybe due to async context or being called too early?)
    private static Sound nmsSoundEventToBukkit(SoundEvent soundEvent) {
        return Registry.SOUNDS.get(Objects.requireNonNull(CraftNamespacedKey.fromMinecraft(soundEvent.getLocation())));
    }

    private static <T> JsonElement encode(Codec<T> type, T object) {
        return type.encode(object, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().orThrow();
    }

    private static <T> T decode(Codec<T> type, JsonElement object) {
        return type.decode(JsonOps.INSTANCE, object).map(Pair::getFirst).result().orElseThrow();
    }
}
