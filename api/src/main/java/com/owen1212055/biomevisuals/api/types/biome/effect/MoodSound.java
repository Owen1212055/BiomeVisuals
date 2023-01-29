package com.owen1212055.biomevisuals.api.types.biome.effect;

import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

public record MoodSound(@NotNull Sound soundEvent, int tickDelay, int blockSearchExtent, double soundPositionOffset) {
}
