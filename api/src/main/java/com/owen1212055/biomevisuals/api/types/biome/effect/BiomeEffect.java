package com.owen1212055.biomevisuals.api.types.biome.effect;

import com.google.gson.annotations.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

@ApiStatus.Internal
public record BiomeEffect(
        @SerializedName("fog_color") @Nullable Integer fogColor,
        @SerializedName("water_color") @Nullable Integer waterColor,
        @SerializedName("water_fog_color") @Nullable Integer waterFogColor,
        @SerializedName("sky_color") @Nullable Integer skyColor,
        @SerializedName("foliage_color") @Nullable Integer foliageColorOverride,
        @SerializedName("grass_color") @Nullable Integer grassColorOverride,
        @SerializedName("grass_color_modifier") GrassModifier grassColorModifier,
        @SerializedName("particle") @Nullable AmbientParticle ambientParticleSettings,
        @SerializedName("ambient_sound") @Nullable Sound ambientSound,
        @SerializedName("mood_sound") @Nullable MoodSound ambientMoodSettings,
        @SerializedName("additions_sound") @Nullable AdditionSound ambientAdditionsSettings,
        @SerializedName("music") @Nullable Music backgroundMusic) {

}
