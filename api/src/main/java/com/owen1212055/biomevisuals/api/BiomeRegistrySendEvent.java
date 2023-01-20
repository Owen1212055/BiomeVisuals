package com.owen1212055.biomevisuals.api;

import com.owen1212055.biomevisuals.api.types.biome.BiomeData;
import org.bukkit.NamespacedKey;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

/**
 * Event fired when a biome registry is about to be sent to a client during player login.
 *
 * @see RegistrySendEvent
 */
public final class BiomeRegistrySendEvent extends RegistrySendEvent<BiomeData> {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Map<NamespacedKey, BiomeData> registryEntries;

    public static @NotNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public BiomeRegistrySendEvent(final @NotNull Map<NamespacedKey, BiomeData> registryEntries) {
        this.registryEntries = registryEntries;
    }

    @Override
    public @NotNull Map<NamespacedKey, BiomeData> getRegistryEntries() {
        return registryEntries;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    @Override
    public String toString() {
        return "BiomeRegistrySendEvent{" +
                "registryEntries=" + registryEntries +
                ", isCancelled=" + isCancelled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BiomeRegistrySendEvent that = (BiomeRegistrySendEvent) o;
        return isCancelled == that.isCancelled && registryEntries.equals(that.registryEntries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registryEntries, isCancelled);
    }

}
