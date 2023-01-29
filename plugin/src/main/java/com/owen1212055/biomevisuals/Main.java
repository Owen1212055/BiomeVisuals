package com.owen1212055.biomevisuals;

import com.owen1212055.biomevisuals.api.OverrideRegistry;
import com.owen1212055.biomevisuals.api.RegistryType;
import com.owen1212055.biomevisuals.api.types.biome.BiomeData;
import com.owen1212055.biomevisuals.commands.BiomeVisualCommand;
import com.owen1212055.biomevisuals.listeners.RegistryListener;
import com.owen1212055.biomevisuals.nms.JsonAdapter;
import com.owen1212055.biomevisuals.nms.KeyedOverride;
import com.owen1212055.biomevisuals.nms.hooks.RegistryHook;
import com.owen1212055.biomevisuals.parsers.OverrideParser;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.AdvancedPie;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class Main extends JavaPlugin implements OverrideRegistry, Listener {

    public static boolean HOOK_ACTIVE = false;
    public static Logger LOGGER;
    private static Main instance;

    private final Map<RegistryType, List<KeyedOverride>> registeredOverrides = new EnumMap<>(RegistryType.class);

    public static Main getInstance() {
        return instance;
    }

    public static Path getDataPath() {
        return getInstance().getDataFolder().toPath().resolve("overrides");
    }

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
            List<Map<RegistryType, List<KeyedOverride>>> overrides = OverrideParser.readOverrides();

            int overrideCount = 0;
            for (Map<RegistryType, List<KeyedOverride>> map : overrides) {
                this.registeredOverrides.putAll(map);
                for (RegistryType type : map.keySet()) {
                    overrideCount += map.get(type).size();
                }
            }

            LOGGER.info("Added {} override(s) from BiomeVisual plugin directory.", overrideCount);
        } catch (Exception e) {
            LOGGER.error("Error registering/reading custom overrides", e);
        }

        Bukkit.getCommandMap().register("bv", new BiomeVisualCommand(this));
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new RegistryListener(this), this);

        Metrics metrics = new Metrics(this, 13696);
        metrics.addCustomChart(new AdvancedPie("overridden_registries", () -> {
            Map<String, Integer> hookedRegisteries = new HashMap<>(this.registeredOverrides.size());
            for (Map.Entry<RegistryType, List<KeyedOverride>> value : this.registeredOverrides.entrySet()) {
                hookedRegisteries.put(value.getKey().getKey().toString(), value.getValue().size());
            }

            return hookedRegisteries;
        }));
        metrics.addCustomChart(new AdvancedPie("overridden_types", () -> {
            Map<String, Integer> hookedRegisteries = new HashMap<>(this.registeredOverrides.size());
            for (List<KeyedOverride> value : this.registeredOverrides.values()) {
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
        registeredOverrides.clear();
        HOOK_ACTIVE = false;
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
    @Override
    public void registerBiomeOverride(NamespacedKey biomeKey, BiomeData data, BooleanSupplier isValid) {
        List<KeyedOverride> overrides = registeredOverrides.computeIfAbsent(RegistryType.BIOME, k -> new ArrayList<>());
        overrides.add(new KeyedOverride(biomeKey, JsonAdapter.adapt(data), isValid, false));
    }

    public Map<RegistryType, List<KeyedOverride>> getRegisteredOverrides() {
        return registeredOverrides;
    }
}
