package com.owen1212055.biomevisuals.api.types.biome.effect;

import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

public final class AmbientSound {

    @NotNull
    private final Sound soundEvent;

    private AmbientSound(@NotNull Sound soundEvent) {
        this.soundEvent = soundEvent;
    }

    public static AmbientSound of(@NotNull Sound soundEvent) {
        return new AmbientSound(soundEvent);
    }

    public Sound getSoundEvent() {
        return soundEvent;
    }

}
