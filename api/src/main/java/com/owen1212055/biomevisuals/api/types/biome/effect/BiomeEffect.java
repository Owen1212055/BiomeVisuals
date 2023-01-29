package com.owen1212055.biomevisuals.api.types.biome.effect;

import com.google.gson.annotations.SerializedName;
import org.bukkit.Color;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public record BiomeEffect(
        @SerializedName("fog_color") @Nullable Color fogColor,
        @SerializedName("water_color") @Nullable Color waterColor,
        @SerializedName("water_fog_color") @Nullable Color waterFogColor,
        @SerializedName("sky_color") @Nullable Color skyColor,
        @SerializedName("foliage_color") @Nullable Color foliageColorOverride,
        @SerializedName("grass_color") @Nullable Color grassColorOverride,
        @SerializedName("grass_color_modifier") GrassModifier grassColorModifier,
        @SerializedName("particle") @Nullable AmbientParticle ambientParticleSettings,
        @SerializedName("ambient_sound") @Nullable AmbientSound ambientSound,
        @SerializedName("mood_sound") @Nullable MoodSound ambientMoodSettings,
        @SerializedName("additions_sound") @Nullable AdditionSound ambientAdditionsSettings,
        @SerializedName("music") @Nullable Music backgroundMusic) {

}
