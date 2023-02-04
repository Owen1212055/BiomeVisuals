package com.owen1212055.biomevisuals.api.types.biome.effect;

import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AmbientParticle {

    @NotNull
    private final Particle particle;
    @Nullable
    private final Object data;
    private final float probability;

    private AmbientParticle(@NotNull Particle particle, @Nullable Object data, float probability) {
        this.particle = particle;
        this.data = data;
        this.probability = probability;
    }

    @NotNull
    public static AmbientParticle of(@NotNull Particle particle, float probability, @Nullable Object data) {
        return new AmbientParticle(particle, data, probability);
    }

    @NotNull
    public static AmbientParticle of(@NotNull Particle particle, float probability) {
        return new AmbientParticle(particle, null, probability);
    }

    @NotNull
    public Particle getParticle() {
        return particle;
    }

    @Nullable
    public Object getData() {
        return data;
    }

    public float getProbability() {
        return probability;
    }

}
