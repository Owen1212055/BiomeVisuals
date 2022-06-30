package com.owen1212055.biomevisuals.nms;

import com.google.gson.*;
import com.mojang.serialization.*;
import com.owen1212055.biomevisuals.api.types.biome.effect.*;
import net.minecraft.world.level.biome.*;
import org.bukkit.craftbukkit.v1_19_R1.*;

public class ApiEntityConverter {

    public static JsonElement convert(AdditionSound sound) {
        AmbientAdditionsSettings settings = new AmbientAdditionsSettings(CraftSound.getSoundEffect(sound.getSoundEvent()), sound.getTickChance());

        return AmbientAdditionsSettings.CODEC.encode(settings, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().orThrow();
    }

    public static JsonElement convert(AmbientParticle particle) {
        AmbientParticleSettings settings = new AmbientParticleSettings(CraftParticle.toNMS(particle.getParticle(), particle.getData()), particle.getProbability());

        return AmbientParticleSettings.CODEC.encode(settings, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().orThrow();
    }

    public static JsonElement convert(MoodSound moodSound) {
        AmbientMoodSettings settings = new AmbientMoodSettings(CraftSound.getSoundEffect(moodSound.getSoundEvent()), moodSound.getTickDelay(), moodSound.getBlockSearchExtent(), moodSound.getSoundPositionOffset());

        return AmbientMoodSettings.CODEC.encode(settings, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().orThrow();
    }

    public static JsonElement convert(Music music) {
        net.minecraft.sounds.Music nmsMusic = new net.minecraft.sounds.Music(CraftSound.getSoundEffect(music.getSound()), music.getMinDelay(), music.getMaxDelay(), music.replaceCurrentMusic());

        return net.minecraft.sounds.Music.CODEC.encode(nmsMusic, JsonOps.INSTANCE, JsonOps.INSTANCE.empty()).get().orThrow();
    }
}
