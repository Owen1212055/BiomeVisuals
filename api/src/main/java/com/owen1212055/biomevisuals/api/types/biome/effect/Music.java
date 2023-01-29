package com.owen1212055.biomevisuals.api.types.biome.effect;

import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

public record Music(@NotNull Sound soundEvent, int minDelay, int maxDelay, boolean replaceCurrentMusic) {
}
