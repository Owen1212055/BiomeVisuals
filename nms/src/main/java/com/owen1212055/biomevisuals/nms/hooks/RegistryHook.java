package com.owen1212055.biomevisuals.nms.hooks;

import com.google.gson.*;
import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import com.owen1212055.biomevisuals.nms.*;
import net.minecraft.core.*;
import org.slf4j.*;
import xyz.jpenilla.reflectionremapper.*;

import java.util.*;

public class RegistryHook {

    private static Logger LOGGER;

    // Custom codec that wraps around the pre-existing codec.
    public static void injectCodec(Map<HookType, List<KeyedOverride>> hooks, Logger logger) throws Exception {
        LOGGER = logger;
        LOGGER.info("Injecting custom codec for registry overriding...");

        Codec<RegistryAccess.RegistryHolder> CAPTURED_CODEC = RegistryAccess.RegistryHolder.NETWORK_CODEC;  // Capture the codec stored in the variable, we will be replacing it.

        // This is meant to basically allow us to encode our own values before it is sent to the client.
        Codec<RegistryAccess.RegistryHolder> INJECTED_CODEC = new Codec<>() {

            @Override
            public <T> DataResult<T> encode(RegistryAccess.RegistryHolder input, DynamicOps<T> ops, T prefix) {
                DataResult<JsonElement> result = CAPTURED_CODEC.encode(input, JsonOps.INSTANCE, JsonOps.INSTANCE.empty());
                var optionalError = result.error();
                if (optionalError.isPresent()) {
                    LOGGER.warn("Failed to encode to JSON: " + optionalError.get().message());
                    LOGGER.info("Sending client default data instead to circumvent this.");
                    return CAPTURED_CODEC.encode(input, ops, prefix);
                }

                JsonObject mainRegistry = result.get().orThrow().getAsJsonObject();
                // Iterate through active override types
                for (var entry : hooks.entrySet()) {
                    // Retrieve the registry array
                    JsonArray registry = mainRegistry.get(entry.getKey().getKey().toString()).getAsJsonObject().getAsJsonArray("value");

                    // Populate it and key it by its identifier, this is because they are stored in an array and this allows us to fetch them nicely.
                    Map<String, JsonObject> registeredObjects = new HashMap<>(registry.size());
                    for (JsonElement jsonElement : registry) {
                        registeredObjects.put(jsonElement.getAsJsonObject().get("name").getAsString(), jsonElement.getAsJsonObject().getAsJsonObject("element"));
                    }

                    // Iterate over the overrides
                    for (KeyedOverride override : entry.getValue()) {
                        String overrideKey = override.key().toString();
                        JsonObject toOverride = registeredObjects.get(overrideKey);
                        if (toOverride == null) {
                            LOGGER.warn("Couldn't find override for {} {}", overrideKey, entry.getKey());
                            continue;
                        }

                        try {
                            if (override.valid().getAsBoolean()) {
                                mergeObject(toOverride, override.object());
                            }
                        } catch (Exception e) {
                            LOGGER.error("Error parsing boolean provider", e);
                        }
                    }

                }

                // Decode it back into NMS type from json
                var dataresult = CAPTURED_CODEC.decode(JsonOps.INSTANCE, mainRegistry);
                // Fail?
                if (dataresult.error().isPresent()) {
                    LOGGER.warn("Failed to encode biome hook: {} ", dataresult.error().get().message());
                    LOGGER.info("Sending client default data instead to circumvent this.");
                    return CAPTURED_CODEC.encode(input, ops, prefix);
                } else {
                    RegistryAccess.RegistryHolder holder = dataresult.map(Pair::getFirst).result().orElseThrow();

                    // If we good encode it to whatever
                    return CAPTURED_CODEC.encode(holder, ops, prefix);
                }
            }

            @Override
            public <T> DataResult<Pair<RegistryAccess.RegistryHolder, T>> decode(DynamicOps<T> ops, T input) {
                return CAPTURED_CODEC.decode(ops, input);
            }
        };

        ReflectionRemapper reflectionRemapper = ReflectionRemapper.forReobfMappingsInPaperJar();
        Class<?> registryClass = RegistryAccess.RegistryHolder.class;
        String networkField = reflectionRemapper.remapFieldName(registryClass, "NETWORK_CODEC");

        UnsafeUtils.unsafeStaticSet(RegistryAccess.RegistryHolder.class.getDeclaredField(networkField), INJECTED_CODEC);
    }

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
