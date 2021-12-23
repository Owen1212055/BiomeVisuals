package com.owen1212055.biomevisuals.api.types.biome.effect;

import org.bukkit.*;
import org.jetbrains.annotations.*;

public final class MoodSound {

    @NotNull
    private final Sound soundEvent;
    private final int tickDelay;
    private final int blockSearchExtent;
    private final double soundPositionOffset;

    MoodSound(@NotNull Sound soundEvent, int tickDelay, int blockSearchExtent, double soundPositionOffset) {
        this.soundEvent = soundEvent;
        this.tickDelay = tickDelay;
        this.blockSearchExtent = blockSearchExtent;
        this.soundPositionOffset = soundPositionOffset;
    }

    public static MoodSound of(@NotNull Sound soundEvent, int tickDelay, int blockSearchExtent, double soundPositionOffset) {
        return new MoodSound(soundEvent, tickDelay, blockSearchExtent, soundPositionOffset);
    }

    public Sound getSoundEvent() {
        return soundEvent;
    }

    public int getTickDelay() {
        return tickDelay;
    }

    public int getBlockSearchExtent() {
        return blockSearchExtent;
    }

    public double getSoundPositionOffset() {
        return soundPositionOffset;
    }
}
