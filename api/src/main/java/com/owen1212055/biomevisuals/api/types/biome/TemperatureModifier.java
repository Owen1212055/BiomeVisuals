package com.owen1212055.biomevisuals.api.types.biome;

public enum TemperatureModifier {

    /**
     * Temperature remains the same within the biome.
     */
    NONE("none"),
    /**
     * Changes the biome based on a frozen noise source.
     * Used in frozen oceans.
     */
    FROZEN("frozen"),
    ;

    private final String key;

    TemperatureModifier(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
