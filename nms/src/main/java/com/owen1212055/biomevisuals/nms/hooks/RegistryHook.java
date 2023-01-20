package com.owen1212055.biomevisuals.nms.hooks;

import com.google.gson.*;
import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import com.owen1212055.biomevisuals.api.*;
import com.owen1212055.biomevisuals.nms.*;
import net.minecraft.core.*;
import org.bukkit.*;
import org.slf4j.*;

import java.util.*;

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
                var optionalError = result.error();
                if (optionalError.isPresent()) {
                    LOGGER.warn("Failed to encode to JSON: " + optionalError.get().message());
                    LOGGER.info("Sending client default data instead to circumvent this.");
                    return CAPTURED_CODEC.encode(input, ops, prefix);
                }

                JsonObject mainRegistry = result.get().orThrow().getAsJsonObject();
                // Iterate through hooked registry types
                for (var hookedTypeKey : HookType.getKeys()) {
                    // Retrieve the registry array
                    JsonArray registry = mainRegistry.get(hookedTypeKey.toString()).getAsJsonObject().getAsJsonArray("value");

                    // Populate maps keyed by identifiers, because they are stored in an array and this allows us to fetch them nicely.
                    Map<NamespacedKey, JsonObject> registryEntries = new HashMap<>(registry.size());
                    Map<NamespacedKey, JsonObject> registryEntryHolders = new HashMap<>(registry.size());
                    for (JsonElement jsonElement : registry) {
                        var namespacedKey = NamespacedKey.fromString(jsonElement.getAsJsonObject().get("name").getAsString());

                        // Event listeners are handed a copy of the JSON structure, so any changes to it won't affect the decoded registry yet.
                        registryEntries.put(namespacedKey, jsonElement.getAsJsonObject().getAsJsonObject("element").deepCopy());
                        registryEntryHolders.put(namespacedKey, jsonElement.getAsJsonObject());
                    }

                    // Let event handlers observe and modify the registry data, but not add or remove new entries in the registry.
                    var sendEvent = new RegistrySendEvent(hookedTypeKey, Collections.unmodifiableMap(registryEntries));
                    Bukkit.getPluginManager().callEvent(sendEvent);

                    // Apply changes made to the registry entries map only if the registry override event was not cancelled.
                    if (sendEvent.isCancelled()) {
                        continue;
                    }

                    for (var overriddenEntry : registryEntries.entrySet()) {
                        JsonObject registryEntryHolder = registryEntryHolders.get(overriddenEntry.getKey());
                        registryEntryHolder.add("element", overriddenEntry.getValue());
                    }

                }

                // Decode it back into NMS type from json
                var dataresult = CAPTURED_CODEC.decode(JsonOps.INSTANCE, mainRegistry);
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
        UnsafeUtils.unsafeStaticSet(RegistrySynchronization.class.getDeclaredField("a"), INJECTED_CODEC);
    }

}
