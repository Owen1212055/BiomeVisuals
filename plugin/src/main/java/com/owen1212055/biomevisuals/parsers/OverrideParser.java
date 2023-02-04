package com.owen1212055.biomevisuals.parsers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.owen1212055.biomevisuals.Main;
import com.owen1212055.biomevisuals.api.RegistryType;
import com.owen1212055.biomevisuals.nms.KeyedOverride;
import com.owen1212055.biomevisuals.parsers.booleans.BooleanProviderRegistry;
import org.bukkit.NamespacedKey;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class OverrideParser {


    public static List<Map<RegistryType, List<KeyedOverride>>> readOverrides() {
        Path mainPath = Main.getDataPath();
        try {
            if (!Files.exists(mainPath)) {
                Files.createDirectories(mainPath);
            }
        } catch (Exception e) {
            Main.LOGGER.error("Failed to create override directly.", e);
        }

        try {
            return Files.walk(mainPath)
                    .filter((file) -> {
                        return file.getFileName().toString().endsWith(".json");
                    })
                    .map((file) -> {
                        JsonObject data;
                        try {
                            data = (JsonObject) JsonParser.parseString(Files.readString(file));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Map<RegistryType, List<KeyedOverride>> registry = new EnumMap<>(RegistryType.class);

                        for (String key : data.keySet()) {
                            RegistryType type = RegistryType.getType(NamespacedKey.fromString(key));
                            if (type == null) {
                                throw new IllegalArgumentException("Invalid hook provided, pick: " + RegistryType.getKeys());
                            }
                            List<KeyedOverride> overrideRegistry = registry.computeIfAbsent(type, k -> new ArrayList<>());

                            JsonArray array = data.getAsJsonArray(key);
                            for (JsonElement element : array) {
                                JsonObject innerObject = element.getAsJsonObject();
                                NamespacedKey namespacedKey = NamespacedKey.fromString(innerObject.get("key").getAsString());
                                JsonObject override = innerObject.getAsJsonObject("override");
                                JsonObject booleanCondition = innerObject.getAsJsonObject("condition");

                                overrideRegistry.add(new KeyedOverride(namespacedKey, override, BooleanProviderRegistry.parse(booleanCondition), true));
                            }
                        }
                        return registry;
                    }).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
