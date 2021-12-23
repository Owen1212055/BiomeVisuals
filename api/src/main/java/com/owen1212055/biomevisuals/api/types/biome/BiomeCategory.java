package com.owen1212055.biomevisuals.api.types.biome;

public enum BiomeCategory {
    NONE("none"),
    TAIGA("taiga"),
    EXTREME_HILLS("extreme_hills"),
    JUNGLE("jungle"),
    MESA("mesa"),
    PLAINS("plains"),
    SAVANNA("savanna"),
    ICY("icy"),
    THE_END("the_end"),
    BEACH("beach"),
    FOREST("forest"),
    OCEAN("ocean"),
    DESERT("desert"),
    RIVER("river"),
    SWAMP("swamp"),
    MUSHROOM("mushroom"),
    NETHER("nether"),
    UNDERGROUND("underground"),
    MOUNTAIN("mountain");

    private final String key;

    BiomeCategory(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
