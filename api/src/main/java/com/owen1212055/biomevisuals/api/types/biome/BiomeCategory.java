package com.owen1212055.biomevisuals.api.types.biome;

import java.util.HashMap;
import java.util.Map;

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

    private static final Map<String, BiomeCategory> BIOME_CATEGORY_MAP = new HashMap<>(values().length);

    static {
        for (BiomeCategory type : values()) {
            BIOME_CATEGORY_MAP.put(type.key, type);
        }
    }

    private final String key;

    BiomeCategory(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static BiomeCategory getBiomeCategory(String key) {
        return BIOME_CATEGORY_MAP.get(key);
    }

}
