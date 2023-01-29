package com.owen1212055.biomevisuals.api.types.biome.effect;

import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record AmbientParticle(@NotNull Particle particle, @Nullable Object data, float probability) {

    public AmbientParticle(@NotNull Particle particle, @Nullable Object data, float probability) {
        this.particle = particle;
        this.data = data;
        this.probability = probability;
    }

    public AmbientParticle(@NotNull Particle particle, float probability) {
        this(particle, null, probability);
    }

}
