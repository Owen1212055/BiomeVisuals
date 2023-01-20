package com.owen1212055.biomevisuals.nms.hooks;

import org.bukkit.*;

import java.util.*;

public enum HookType {
    BIOME(NamespacedKey.minecraft("worldgen/biome")),
    //DIMENSION(NamespacedKey.minecraft("dimension_type")),
    ;

    private static final Map<NamespacedKey, HookType> HOOK_MAP = new HashMap<>(values().length);

    static {
        for (HookType type : values()) {
            HOOK_MAP.put(type.key, type);
        }
    }

    private final NamespacedKey key;

    HookType(NamespacedKey key) {
        this.key = key;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public static HookType getType(NamespacedKey key) {
        return HOOK_MAP.get(key);
    }

    public static Set<NamespacedKey> getKeys() {
        return HOOK_MAP.keySet();
    }
}
