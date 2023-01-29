package com.owen1212055.biomevisuals.api;

import com.owen1212055.biomevisuals.api.types.biome.BiomeData;
import org.bukkit.NamespacedKey;

import java.util.function.BooleanSupplier;

public interface OverrideRegistry {

    /**
     * Registers a custom biome override that will be applied
     * when players join the server.
     *
     * @param biomeKey biome key
     * @param data     biome override data
     */
    default void registerBiomeOverride(NamespacedKey biomeKey, BiomeData data) {
        registerBiomeOverride(biomeKey, data, () -> true);
    }

    /**
     * Registers a custom biome override, where if the boolean supplier is valid it will
     * apply it to the player.
     *
     * @param biomeKey biome key
     * @param data     biome override data
     * @param isValid  boolean supplier
     */
    void registerBiomeOverride(NamespacedKey biomeKey, BiomeData data, BooleanSupplier isValid);

//    default void registerDimensionOverride(NamespacedKey dimensionKey, DimensionData data) {
//        registerDimensionOverride(dimensionKey, data, () -> true);
//    }
//
//    void registerDimensionOverride(NamespacedKey dimensionKey, DimensionData data, BooleanSupplier isValid);
//


}
