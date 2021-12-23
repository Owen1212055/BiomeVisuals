package com.owen1212055.biomevisuals.api.types.biome.effect;

import org.bukkit.*;
import org.jetbrains.annotations.*;

public final class AdditionSound {

    @NotNull
    private final Sound soundEvent;
    private final double tickChance;

    private AdditionSound(@NotNull Sound soundEvent, double tickChance) {
        this.soundEvent = soundEvent;
        this.tickChance = tickChance;
    }

    @NotNull
    public static AdditionSound of(@NotNull Sound soundEvent, double tickChance) {
        return new AdditionSound(soundEvent, tickChance);
    }

    @NotNull
    public Sound getSoundEvent() {
        return soundEvent;
    }

    public double getTickChance() {
        return tickChance;
    }
}
