package com.owen1212055.biomevisuals.parsers;

import com.google.gson.*;
import com.owen1212055.biomevisuals.*;
import com.owen1212055.biomevisuals.nms.*;
import com.owen1212055.biomevisuals.nms.hooks.*;
import com.owen1212055.biomevisuals.parsers.booleans.*;
import org.bukkit.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class OverrideParser {


    public static List<Map<HookType, List<KeyedOverride>>> readOverrides() {
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
                        Map<HookType, List<KeyedOverride>> registry = new EnumMap<>(HookType.class);

                        for (String key : data.keySet()) {
                            HookType type = HookType.getType(NamespacedKey.fromString(key));
                            if (type == null) {
                                throw new IllegalArgumentException("Invalid hook provided, pick: " + HookType.getKeys());
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
