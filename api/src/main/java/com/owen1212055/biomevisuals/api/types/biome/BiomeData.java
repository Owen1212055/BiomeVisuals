package com.owen1212055.biomevisuals.api.types.biome;

import com.google.gson.annotations.*;
import com.owen1212055.biomevisuals.api.types.biome.effect.*;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record BiomeData(
        PrecipitationType precipitation, Float temperature,
        @SerializedName("temperature_modifier") TemperatureModifier temperatureModifier,
        Float downfall, BiomeCategory category, BiomeEffect effects) {
}
