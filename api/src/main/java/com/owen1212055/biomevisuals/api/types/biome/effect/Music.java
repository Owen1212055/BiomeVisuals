package com.owen1212055.biomevisuals.api.types.biome.effect;

import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

public final class Music {

    @NotNull
    private final Sound soundEvent;
    private final int minDelay;
    private final int maxDelay;
    private final boolean replaceCurrentMusic;

    private Music(@NotNull Sound soundEvent, int minDelay, int maxDelay, boolean replaceCurrentMusic) {
        this.soundEvent = soundEvent;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.replaceCurrentMusic = replaceCurrentMusic;
    }

    @NotNull
    public static Music of(@NotNull Sound sound, int tickMinDelay, int tickMaxDelay, boolean replaceCurrentMusic) {
        return new Music(sound, tickMinDelay, tickMaxDelay, replaceCurrentMusic);
    }

    @NotNull
    public Sound getSoundEvent() {
        return soundEvent;
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
