package com.owen1212055.biomevisuals.listeners;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.owen1212055.biomevisuals.Main;
import com.owen1212055.biomevisuals.api.RegistryType;
import com.owen1212055.biomevisuals.api.events.BiomeRegistrySendEvent;
import com.owen1212055.biomevisuals.api.types.biome.BiomeData;
import com.owen1212055.biomevisuals.nms.JsonAdapter;
import com.owen1212055.biomevisuals.nms.KeyedOverride;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class RegistryListener implements Listener {

    private final Main main;

    public RegistryListener(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onRegistrySend(final @NotNull BiomeRegistrySendEvent event) {
        List<KeyedOverride> keyedOverrides = main.getRegisteredOverrides().get(RegistryType.BIOME);
        if (keyedOverrides == null) {
            return;
        }

        Map<NamespacedKey, BiomeData> registryEntries = event.getRegistryEntries();
        for (KeyedOverride keyedOverride : keyedOverrides) {
            if (!keyedOverride.valid().getAsBoolean()) {
                continue;
            }

            BiomeData registryEntry = registryEntries.get(keyedOverride.key());
            if (registryEntry == null) {
                continue;
            }

            // Convert the registry data holder to a JSON object for merging.
            // Then convert the merged JSON object back to a data holder object
            JsonObject registryEntryObject = JsonAdapter.adapt(registryEntry);
            mergeObject(registryEntryObject, keyedOverride.object());
            registryEntries.put(keyedOverride.key(), JsonAdapter.adapt(registryEntryObject, BiomeData.class));
        }
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
