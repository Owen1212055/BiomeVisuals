package com.owen1212055.biomevisuals.api.types.biome.effect;

import com.google.gson.annotations.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public record BiomeEffect(
        @SerializedName("fog_color") Color fogColor,
        @SerializedName("water_color") Color waterColor,
        @SerializedName("water_fog_color") Color waterFogColor,
        @SerializedName("sky_color") Color skyColor,
        @SerializedName("foliage_color") @Nullable Color foliageColorOverride,
        @SerializedName("grass_color") @Nullable Color grassColorOverride,
        @SerializedName("grass_color_modifier") GrassModifier grassColorModifier,
        @SerializedName("particle") @Nullable AmbientParticle ambientParticleSettings,
        @SerializedName("ambient_sound") @Nullable Sound ambientSound,
        @SerializedName("mood_sound") @Nullable MoodSound ambientMoodSettings,
        @SerializedName("additions_sound") @Nullable AdditionSound ambientAdditionsSettings,
        @SerializedName("music") @Nullable Music backgroundMusic) {

}
