package com.owen1212055.biomevisuals.api;

import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

/**
 * Event fired when a registry is about to be sent to a client during player login.
 * <p>
 * Event listeners can inspect the registry entries and modify them. Any changes
 * will modify the registry data sent to the player that is logging in, unless the
 * event is cancelled.
 * <p>
 * This is a relatively low-level event meant for advanced use cases. For a simpler
 * registry field overriding API, check out {@link OverrideRegistry}.
 *
 * @see <a href="https://wiki.vg/Protocol#Login_.28play.29">Clientbound login packet, codec registry field</a>
 */
public final class RegistrySendEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final NamespacedKey registryType;
    private final Map<NamespacedKey, JsonObject> registryEntries;
    private boolean isCancelled = false;

    public static @NotNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public RegistrySendEvent(final @NotNull NamespacedKey registryType, final @NotNull Map<NamespacedKey, JsonObject> registryEntries) {
        // The NMS network codec may trigger this event in a non-main thread.
        super(true);

        this.registryType = registryType;
        this.registryEntries = registryEntries;
    }

    public @NotNull NamespacedKey getRegistryType() {
        return registryType;
    }

    public @NotNull Map<NamespacedKey, JsonObject> getRegistryEntries() {
        return registryEntries;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.isCancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    @Override
    public String toString() {
        return "RegistrySendEvent{" +
                "registryType=" + registryType +
                ", registryEntries=" + registryEntries +
                ", isCancelled=" + isCancelled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrySendEvent that = (RegistrySendEvent) o;
        return isCancelled == that.isCancelled && registryType.equals(that.registryType) && registryEntries.equals(that.registryEntries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registryType, registryEntries, isCancelled);
    }

}
