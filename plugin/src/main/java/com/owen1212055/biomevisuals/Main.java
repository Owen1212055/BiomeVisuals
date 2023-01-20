package com.owen1212055.biomevisuals;

import com.google.gson.*;
import com.owen1212055.biomevisuals.api.*;
import com.owen1212055.biomevisuals.api.types.biome.*;
import com.owen1212055.biomevisuals.commands.*;
import com.owen1212055.biomevisuals.nms.*;
import com.owen1212055.biomevisuals.nms.hooks.*;
import com.owen1212055.biomevisuals.parsers.*;
import org.bstats.bukkit.*;
import org.bstats.charts.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.*;
import org.jetbrains.annotations.*;
import org.slf4j.*;

import java.nio.file.*;
import java.util.*;
import java.util.function.*;

public class Main extends JavaPlugin implements OverrideRegistry, Listener {

    public static boolean HOOK_ACTIVE = false;
    private static Main instance;
    public static Logger LOGGER;

    public static Main getInstance() {
        return instance;
    }

    public static Path getDataPath() {
        return getInstance().getDataFolder().toPath().resolve("overrides");
    }

    public static final Map<HookType, List<KeyedOverride>> OVERRIDES = new EnumMap<>(HookType.class);

    @Override
    public void onEnable() {
        instance = this;
        LOGGER = getSLF4JLogger();
        try {
            RegistryHook.injectCodec(LOGGER);
            HOOK_ACTIVE = true;
        } catch (Exception e) {
            LOGGER.warn("Failed to inject hook, overrides will not be applied to players.");
            HOOK_ACTIVE = false;
        }

        try {
            List<Map<HookType, List<KeyedOverride>>> overrides = OverrideParser.readOverrides();

            int overrideCount = 0;
            for (Map<HookType, List<KeyedOverride>> map : overrides) {
                Main.OVERRIDES.putAll(map);
                for (HookType type : map.keySet()) {
                    overrideCount += map.get(type).size();
                }
            }

            LOGGER.info("Added {} override(s) from BiomeVisual plugin directory.", overrideCount);
        } catch (Exception e) {
            LOGGER.error("Error registering/reading custom overrides", e);
        }

        Bukkit.getCommandMap().register("bv", new BiomeVisualCommand());

        Bukkit.getPluginManager().registerEvents(this, this);

        Metrics metrics = new Metrics(this, 13696);
        metrics.addCustomChart(new AdvancedPie("overridden_registries", () -> {
            Map<String, Integer> hookedRegisteries = new HashMap<>(Main.OVERRIDES.size());
            for (Map.Entry<HookType, List<KeyedOverride>> value : Main.OVERRIDES.entrySet()) {
                hookedRegisteries.put(value.getKey().getKey().toString(), value.getValue().size());
            }

            return hookedRegisteries;
        }));
        metrics.addCustomChart(new AdvancedPie("overridden_types", () -> {
            Map<String, Integer> hookedRegisteries = new HashMap<>(Main.OVERRIDES.size());
            for (List<KeyedOverride> value : Main.OVERRIDES.values()) {
                for (KeyedOverride override : value) {
                    String key = override.key().toString();
                    int previous = hookedRegisteries.getOrDefault(key, 1);

                    hookedRegisteries.put(key, previous);
                }
            }

            return hookedRegisteries;
        }));
    }

    @Override
    public void onDisable() {
        OVERRIDES.clear();

        HOOK_ACTIVE = false;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onRegistrySend(final @NotNull RegistrySendEvent event) {
        List<KeyedOverride> keyedOverrides;
        if ((keyedOverrides = OVERRIDES.get(HookType.getType(event.getRegistryType()))) == null) {
            return;
        }

        for (var keyedOverride : keyedOverrides) {
            if (!keyedOverride.valid().getAsBoolean()) {
                continue;
            }

            JsonObject registryEntries;
            if ((registryEntries = event.getRegistryEntries().get(keyedOverride.key())) != null) {
                mergeObject(registryEntries, keyedOverride.object());
            }
        }
    }

    @Override
    public void registerBiomeOverride(NamespacedKey biomeKey, BiomeData data, BooleanSupplier isValid) {
        List<KeyedOverride> overrides = OVERRIDES.computeIfAbsent(HookType.BIOME, k -> new ArrayList<>());

        overrides.add(new KeyedOverride(biomeKey, JsonAdapter.adapt(data), isValid, false));
    }

//    @Override
//    public void registerDimensionOverride(NamespacedKey dimensionKey, DimensionData data, BooleanSupplier isValid) {
//        List<KeyedOverride> overrides;
//        if (OVERRIDES.containsKey(HookType.DIMENSION)) {
//            overrides = OVERRIDES.get("dimension_type");
//        } else {
//            overrides = new ArrayList<>();
//            OVERRIDES.put("dimension_type", overrides);
//        }
//
//        overrides.add(new KeyedOverride(dimensionKey, GSON.toJsonTree(data).getAsJsonObject()));
//    }

    private static void mergeObject(JsonObject into, JsonObject merging) {
        for (String overrideKey : merging.keySet()) {
            JsonElement element = merging.get(overrideKey);

            if (element.isJsonObject()) {
                JsonElement original = into.get(overrideKey);
                if (original != null && original.isJsonObject()) {
                    mergeObject(original.getAsJsonObject(), element.getAsJsonObject());
                } else {
                    into.add(overrideKey, element);
                }
            } else {
                into.add(overrideKey, element);
            }

        }
    }

}
