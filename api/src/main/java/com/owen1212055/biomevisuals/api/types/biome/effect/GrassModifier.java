package com.owen1212055.biomevisuals.api.types.biome.effect;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

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
    SWAMP("swamp");

    private static final Map<String, GrassModifier> GRASS_MODIFIER_MAP = new HashMap<>(values().length);

    static {
        for (GrassModifier type : values()) {
            GRASS_MODIFIER_MAP.put(type.key, type);
        }
    }

    private final String key;

    GrassModifier(String key) {
        this.key = key;
    }

    @NotNull
    public String getKey() {
        return key;
    }

    public static GrassModifier getGrassModifier(String key) {
        return GRASS_MODIFIER_MAP.get(key);
    }

}
