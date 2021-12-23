package com.owen1212055.biomevisuals.api.types.biome;

public enum PrecipitationType {

    NONE("none"),
    RAIN("rain"),
    SNOW("snow"),
    ;

    private final String key;

    PrecipitationType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
