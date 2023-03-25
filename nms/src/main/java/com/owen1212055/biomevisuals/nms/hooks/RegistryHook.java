package com.owen1212055.biomevisuals.nms.hooks;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.owen1212055.biomevisuals.api.RegistryType;
import com.owen1212055.biomevisuals.api.events.BiomeRegistrySendEvent;
import com.owen1212055.biomevisuals.api.events.RegistrySendEvent;
import com.owen1212055.biomevisuals.api.types.biome.BiomeData;
import com.owen1212055.biomevisuals.nms.JsonAdapter;
import com.owen1212055.biomevisuals.nms.Mappings;
import com.owen1212055.biomevisuals.nms.UnsafeUtils;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySynchronization;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.Entry;

public class RegistryHook {

    private static Logger LOGGER;

    // Custom codec that wraps around the pre-existing codec.
    public static void injectCodec(Logger logger) throws Exception {
        LOGGER = logger;
        LOGGER.info("Injecting custom codec for registry overriding...");

        Codec<RegistryAccess> CAPTURED_CODEC = RegistrySynchronization.NETWORK_CODEC;  // Capture the codec stored in the variable, we will be replacing it.

        // This is meant to basically allow us to encode our own values before it is sent to the client.
        Codec<RegistryAccess> INJECTED_CODEC = new Codec<>() {

            @Override
            public <T> DataResult<T> encode(RegistryAccess input, DynamicOps<T> ops, T prefix) {
                DataResult<JsonElement> result = CAPTURED_CODEC.encode(input, JsonOps.INSTANCE, JsonOps.INSTANCE.empty());
                Optional<DataResult.PartialResult<JsonElement>> optionalError = result.error();
                if (optionalError.isPresent()) {
                    LOGGER.warn("Failed to encode to JSON: " + optionalError.get().message());
                    LOGGER.info("Sending client default data instead to circumvent this.");
                    return CAPTURED_CODEC.encode(input, ops, prefix);
                }

                JsonObject mainRegistry = result.get().orThrow().getAsJsonObject();
                // Iterate through hooked registry types
                try {
                    hookIntoMainRegistry(mainRegistry);
                } catch (Throwable e) {
                    e.printStackTrace();
                    LOGGER.warn("Failed to encode hooked data: {}", e.getMessage());
                    LOGGER.info("Sending client default data instead to circumvent this.");
                    return CAPTURED_CODEC.encode(input, ops, prefix);
                }

                // Decode it back into NMS type from json
                DataResult<Pair<RegistryAccess, JsonElement>> dataresult = CAPTURED_CODEC.decode(JsonOps.INSTANCE, mainRegistry);
                // Fail?
                if (dataresult.error().isPresent()) {
                    LOGGER.warn("Failed to encode hooked data: {}", dataresult.error().get().message());
                    LOGGER.info("Sending client default data instead to circumvent this.");
                    return CAPTURED_CODEC.encode(input, ops, prefix);
                } else {
                    // The captured codec of MC 1.19.3 expects an instance of ImmutableRegistryAccess
                    RegistryAccess holder = dataresult.map(Pair::getFirst).result().orElseThrow().freeze();

                    // If we good encode it to whatever
                    return CAPTURED_CODEC.encode(holder, ops, prefix);
                }
            }

            @Override
            public <T> DataResult<Pair<RegistryAccess, T>> decode(DynamicOps<T> ops, T input) {
                return CAPTURED_CODEC.decode(ops, input);
            }
        };

        // This declared field name comes from the Mojang-provided server JAR mappings. Verified to work
        UnsafeUtils.unsafeStaticSet(RegistrySynchronization.class.getDeclaredField(Mappings.NETWORK_CODEC), INJECTED_CODEC);
    }

    public static void hookIntoMainRegistry(JsonObject mainRegistry) {
        for (RegistryType hookedType : RegistryType.values()) {
            // Retrieve the registry array
            JsonArray registry = mainRegistry.get(hookedType.getKey().toString()).getAsJsonObject().getAsJsonArray("value");

            // Populate maps keyed by identifiers, because they are stored in an array and this allows us to fetch them nicely.
            Map<NamespacedKey, Object> registryEntries = new HashMap<>(registry.size());
            Map<NamespacedKey, JsonObject> registryEntryHolders = new HashMap<>(registry.size());
            Class<?> registryEntryDataType = hookedType.getDataType();
            for (JsonElement jsonElement : registry) {
                NamespacedKey namespacedKey = NamespacedKey.fromString(jsonElement.getAsJsonObject().get("name").getAsString());
                JsonObject registryEntry = jsonElement.getAsJsonObject().getAsJsonObject("element");
                Object registryEntryData = JsonAdapter.adapt(registryEntry, registryEntryDataType);

                registryEntries.put(namespacedKey, registryEntryData);
                registryEntryHolders.put(namespacedKey, jsonElement.getAsJsonObject());

                LOGGER.debug("Deserializing registry entry to strongly typed object: {}, {} -> {}", namespacedKey, registryEntry, registryEntryData);
            }

            // Let soundEvent handlers observe and modify the registry data.
            RegistrySendEvent<?> sendEvent;
            if (registryEntryDataType == BiomeData.class) {
                sendEvent = new BiomeRegistrySendEvent(uncheckedCastMap(registryEntries));
            } else {
                LOGGER.error("Unimplemented event for registry data type: {}. This is an internal plugin error that must be fixed", registryEntryDataType.getName());
                LOGGER.info("Sending client default data instead to circumvent this.");
                throw new UnsupportedOperationException();
            }

            Bukkit.getPluginManager().callEvent(sendEvent);

            // Apply changes made to the registry entries map only if the registry override soundEvent was not cancelled.
            if (sendEvent.isCancelled()) {
                continue;
            }

            for (Entry<NamespacedKey, Object> overriddenEntry : registryEntries.entrySet()) {
                JsonObject registryEntryHolder = registryEntryHolders.get(overriddenEntry.getKey());

                // Only replace if present to prevent handlers that add new registry entries from having any effect.
                if (registryEntryHolder != null) {
                    registryEntryHolder.add("element", JsonAdapter.adapt(overriddenEntry.getValue()));
                }
            }

        }
    }

    @SuppressWarnings("unchecked")
    private static <K, V> Map<K, V> uncheckedCastMap(Map<?, ?> map) {
        return (Map<K, V>) map;
    }

}
