package com.owen1212055.biomevisuals.api.events;

import com.owen1212055.biomevisuals.api.types.biome.BiomeData;
import org.bukkit.NamespacedKey;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Event fired when a biome registry is about to be sent to a client during player login.
 *
 * @see RegistrySendEvent
 */
public final class BiomeRegistrySendEvent extends RegistrySendEvent<BiomeData> {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Map<NamespacedKey, BiomeData> registryEntries;

    @ApiStatus.Internal
    public BiomeRegistrySendEvent(final @NotNull Map<NamespacedKey, BiomeData> registryEntries) {
        this.registryEntries = registryEntries;
    }

    @Override
    public BiomeData getRegistryEntry(NamespacedKey key) {
        return registryEntries.get(key);
    }

    @Override
    public void setRegistryEntry(NamespacedKey key, BiomeData data) {
        registryEntries.put(key, data);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
