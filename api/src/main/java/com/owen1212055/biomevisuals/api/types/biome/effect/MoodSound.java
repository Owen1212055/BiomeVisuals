package com.owen1212055.biomevisuals.api.types.biome.effect;

import org.bukkit.*;
import org.jetbrains.annotations.*;

public record MoodSound(@NotNull Sound soundEvent, int tickDelay, int blockSearchExtent, double soundPositionOffset) {
}
