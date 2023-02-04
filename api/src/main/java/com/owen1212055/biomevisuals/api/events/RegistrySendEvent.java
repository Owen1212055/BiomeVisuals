package com.owen1212055.biomevisuals.api.events;

import com.owen1212055.biomevisuals.api.OverrideRegistry;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * Event fired when a registry is about to be sent to a client during player login.
 * <p>
 * Event listeners can inspect the registry entries and modify them. Any changes
 * will modify the registry data sent to the player that is logging in, unless the
 * event is cancelled.
 * <p>
 * This is a relatively low-level event meant for advanced use cases. For a simpler
 * registry field overriding API, check out {@link OverrideRegistry}.
 */
public abstract class RegistrySendEvent<T> extends Event implements Cancellable {

    protected boolean isCancelled = false;

    protected RegistrySendEvent() {
        // The NMS network codec may trigger this soundEvent hierarchy in non-main threads.
        super(true);
    }

    public abstract T getRegistryEntry(NamespacedKey key);

    public abstract void setRegistryEntry(NamespacedKey key, T data);

    @Override
    public final boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public final void setCancelled(final boolean cancel) {
        this.isCancelled = cancel;
    }

}
