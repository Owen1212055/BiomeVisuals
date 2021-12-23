package com.owen1212055.biomevisuals.api.types.biome.effect;

import org.jetbrains.annotations.*;

public enum GrassModifier {

    /**
     * Grass color remains the same within the biome.
     */
    NONE("none"),
    /**
     * Grass color is slightly darkened within the biome.
     */
    DARK_FOREST("dark_forest"),
    /**
     * Grass color is instead determined by a biome noise source, switching between two main colors.
     */
    SWAMP("swamp"),
    ;

    private final String key;

    GrassModifier(String key) {
        this.key = key;
    }

    @NotNull
    public String getKey() {
        return key;
    }
}
