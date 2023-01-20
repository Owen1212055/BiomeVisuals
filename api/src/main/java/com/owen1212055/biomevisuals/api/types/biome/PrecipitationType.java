package com.owen1212055.biomevisuals.api.types.biome;

import java.util.*;

public enum PrecipitationType {

    NONE("none"),
    RAIN("rain"),
    SNOW("snow");

    private static final Map<String, PrecipitationType> PRECIPITATION_TYPE_MAP = new HashMap<>(values().length);

    static {
        for (PrecipitationType type : values()) {
            PRECIPITATION_TYPE_MAP.put(type.key, type);
        }
    }

    private final String key;

    PrecipitationType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static PrecipitationType getPrecipitationType(String key) {
        return PRECIPITATION_TYPE_MAP.get(key);
    }

}
