package com.owen1212055.biomevisuals.api.types.biome.effect;

import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

public record AdditionSound(@NotNull Sound soundEvent, double tickChance) {
}
