package com.owen1212055.biomevisuals.api;

import com.owen1212055.biomevisuals.api.types.biome.BiomeData;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum RegistryType {
    BIOME(NamespacedKey.minecraft("worldgen/biome"), BiomeData.class),
    //DIMENSION(NamespacedKey.minecraft("dimension_type"), DimensionData.class),
    ;

    private static final Map<NamespacedKey, RegistryType> TYPE_MAP = new HashMap<>(values().length);

    static {
        for (RegistryType type : values()) {
            TYPE_MAP.put(type.key, type);
        }
    }

    private final NamespacedKey key;
    private final Class<?> dataType;

    RegistryType(NamespacedKey key, Class<?> dataType) {
        this.key = key;
        this.dataType = dataType;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public Class<?> getDataType() {
        return dataType;
    }

    public static RegistryType getType(NamespacedKey key) {
        return TYPE_MAP.get(key);
    }

    public static Set<NamespacedKey> getKeys() {
        return TYPE_MAP.keySet();
    }
}
