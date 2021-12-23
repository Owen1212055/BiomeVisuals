package com.owen1212055.biomevisuals.api.types.biome.effect;

import org.bukkit.*;
import org.jetbrains.annotations.*;

public final class Music {

    @NotNull
    private final Sound event;
    private final int minDelay;
    private final int maxDelay;
    private final boolean replaceCurrentMusic;

    private Music(@NotNull Sound event, int minDelay, int maxDelay, boolean replaceCurrentMusic) {
        this.event = event;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.replaceCurrentMusic = replaceCurrentMusic;
    }

    @NotNull
    public static Music of(@NotNull Sound sound, int tickMinDelay, int tickMaxDelay, boolean replaceCurrentMusic) {
        return new Music(sound, tickMinDelay, tickMaxDelay, replaceCurrentMusic);
    }

    @NotNull
    public Sound getSound() {
        return event;
    }

    public int getMinDelay() {
        return minDelay;
    }

    public int getMaxDelay() {
        return maxDelay;
    }

    public boolean replaceCurrentMusic() {
        return replaceCurrentMusic;
    }
}
