package com.owen1212055.biomevisuals.parsers.booleans;

import com.google.gson.JsonObject;
import com.owen1212055.biomevisuals.Main;
import com.owen1212055.biomevisuals.parsers.booleans.impl.DateBooleanProvider;
import com.owen1212055.biomevisuals.parsers.booleans.impl.StaticBooleanProvider;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class BooleanProviderRegistry {

    private final static Map<NamespacedKey, BooleanProvider> REGISTRY = new HashMap<>();

    static {
        register(new DateBooleanProvider());
        register(new StaticBooleanProvider());
    }

    private static void register(BooleanProvider provider) {
        REGISTRY.put(NamespacedKey.fromString(provider.getKey(), Main.getInstance()), provider);
    }

    public static BooleanProvider getProvider(NamespacedKey key) {
        return REGISTRY.get(key);
    }

    public static BooleanSupplier parse(JsonObject booleanCondition) {
        if (booleanCondition == null) {
            return () -> true;
        }

        NamespacedKey key = NamespacedKey.fromString(booleanCondition.get("type").getAsString());
        BooleanProvider provider = getProvider(key);
        if (provider == null) {
            throw new IllegalArgumentException("Couldn't find provider named: " + key);
        }

        return () -> provider.parse(booleanCondition);
    }
}
