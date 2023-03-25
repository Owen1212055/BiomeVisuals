package com.owen1212055.biomevisuals.api.types.biome;

import com.google.gson.annotations.SerializedName;
import com.owen1212055.biomevisuals.api.types.biome.effect.BiomeEffect;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record BiomeData(
        @SerializedName("has_precipitation") boolean hasPrecipitation, Float temperature,
        @SerializedName("temperature_modifier") TemperatureModifier temperatureModifier,
        Float downfall, BiomeCategory category, BiomeEffect effects) {
}
