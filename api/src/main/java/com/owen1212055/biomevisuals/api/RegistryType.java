package com.owen1212055.biomevisuals.api;

import org.bukkit.*;

import java.util.*;

public enum RegistryType {
    BIOME(NamespacedKey.minecraft("worldgen/biome")),
    //DIMENSION(NamespacedKey.minecraft("dimension_type")),
    ;

    private static final Map<NamespacedKey, RegistryType> HOOK_MAP = new HashMap<>(values().length);

    static {
        for (RegistryType type : values()) {
            HOOK_MAP.put(type.key, type);
        }
    }

    private final NamespacedKey key;

    RegistryType(NamespacedKey key) {
        this.key = key;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public static RegistryType getType(NamespacedKey key) {
        return HOOK_MAP.get(key);
    }

    public static Set<NamespacedKey> getKeys() {
        return HOOK_MAP.keySet();
    }
}
