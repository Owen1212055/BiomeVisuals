package com.owen1212055.biomevisuals.nms;

import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import com.owen1212055.biomevisuals.api.types.biome.effect.*;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_19_R2.*;
import org.bukkit.craftbukkit.v1_19_R2.util.CraftNamespacedKey;

import java.util.Objects;

public class ApiEntityConverter {

    public static JsonElement serialize(AdditionSound sound) {
        AmbientAdditionsSettings settings = new AmbientAdditionsSettings(Holder.direct(CraftSound.getSoundEffect(sound.soundEvent())), sound.tickChance());

        return AmbientAdditionsSettings.CODEC.encode(settings, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().orThrow();
    }

    public static AdditionSound deserializeAdditionSound(JsonElement sound) {
        AmbientAdditionsSettings settings = AmbientAdditionsSettings.CODEC.decode(JsonOps.INSTANCE, sound).map(Pair::getFirst).result().orElseThrow();

        return new AdditionSound(Objects.requireNonNull(nmsSoundEventToBukkit(settings.getSoundEvent().value())), settings.getTickChance());
    }

    public static JsonElement serialize(AmbientParticle particle) {
        AmbientParticleSettings settings = new AmbientParticleSettings(CraftParticle.toNMS(particle.particle(), particle.data()), particle.probability());

        return AmbientParticleSettings.CODEC.encode(settings, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().orThrow();
    }

    public static AmbientParticle deserializeAmbientParticle(JsonElement particle) {
        AmbientParticleSettings settings = AmbientParticleSettings.CODEC.decode(JsonOps.INSTANCE, particle).map(Pair::getFirst).result().orElseThrow();

        try {
            // FIXME the particle data object is not serialized as such, so we can't deserialize it back
            var probabilityField = AmbientParticleSettings.class.getDeclaredField("c");
            probabilityField.setAccessible(true);
            return new AmbientParticle(CraftParticle.toBukkit(settings.getOptions()), (float) probabilityField.get(settings));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonElement serialize(AmbientSound ambientSound) {
        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(new ResourceLocation(ambientSound.soundEvent().getKey().toString()));

        return SoundEvent.CODEC.encode(Holder.direct(soundEvent), JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().orThrow();
    }

    public static AmbientSound deserializeAmbientSound(JsonElement ambientSound) {
        Holder<SoundEvent> soundEvent = SoundEvent.CODEC.decode(JsonOps.INSTANCE, ambientSound).map(Pair::getFirst).result().orElseThrow();

        return new AmbientSound(Objects.requireNonNull(nmsSoundEventToBukkit(soundEvent.value())));
    }

    public static JsonElement serialize(MoodSound moodSound) {
        AmbientMoodSettings settings = new AmbientMoodSettings(Holder.direct(CraftSound.getSoundEffect(moodSound.soundEvent())), moodSound.tickDelay(), moodSound.blockSearchExtent(), moodSound.soundPositionOffset());

        return AmbientMoodSettings.CODEC.encode(settings, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().orThrow();
    }

    public static MoodSound deserializeMoodSound(JsonElement moodSound) {
        AmbientMoodSettings settings = AmbientMoodSettings.CODEC.decode(JsonOps.INSTANCE, moodSound).map(Pair::getFirst).result().orElseThrow();

        return new MoodSound(Objects.requireNonNull(nmsSoundEventToBukkit(settings.getSoundEvent().value())), settings.getTickDelay(), settings.getBlockSearchExtent(), settings.getSoundPositionOffset());
    }

    public static JsonElement serialize(Music music) {
        net.minecraft.sounds.Music nmsMusic = new net.minecraft.sounds.Music(Holder.direct(CraftSound.getSoundEffect(music.soundEvent())), music.minDelay(), music.maxDelay(), music.replaceCurrentMusic());

        return net.minecraft.sounds.Music.CODEC.encode(nmsMusic, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().orThrow();
    }

    public static Music deserializeMusic(JsonElement music) {
        net.minecraft.sounds.Music nmsMusic = net.minecraft.sounds.Music.CODEC.decode(JsonOps.INSTANCE, music).map(Pair::getFirst).result().orElseThrow();

        return new Music(Objects.requireNonNull(nmsSoundEventToBukkit(nmsMusic.getEvent().value())), nmsMusic.getMinDelay(), nmsMusic.getMaxDelay(), nmsMusic.replaceCurrentMusic());
    }

    // Workaround method for avoiding NPEs thrown when CraftSound#getBukkit tries to access the vanilla
    // registries (maybe due to async context or being called too early?)
    private static Sound nmsSoundEventToBukkit(SoundEvent soundEvent) {
        return Registry.SOUNDS.get(Objects.requireNonNull(CraftNamespacedKey.fromMinecraft(soundEvent.getLocation())));
    }
}
