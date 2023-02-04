package com.owen1212055.biomevisuals.api.types.biome;

import java.util.HashMap;
import java.util.Map;

public enum TemperatureModifier {

    /**
     * Temperature remains the same within the biome.
     */
    NONE("none"),
    /**
     * Changes the biome based on a frozen noise source.
     * Used in frozen oceans.
     */
    FROZEN("frozen");

    private static final Map<String, TemperatureModifier> TEMPERATURE_MODIFIER_MAP = new HashMap<>(values().length);

    static {
        for (TemperatureModifier type : values()) {
            TEMPERATURE_MODIFIER_MAP.put(type.key, type);
        }
    }

    private final String key;

    TemperatureModifier(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static TemperatureModifier getTemperatureModifier(String key) {
        return TEMPERATURE_MODIFIER_MAP.get(key);
    }

}
