package com.owen1212055.biomevisuals.api.types.biome.effect;

import com.google.gson.JsonObject;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AmbientParticle {

    @NotNull
    private final JsonObject particle;
    private final float probability;

    private AmbientParticle( @NotNull JsonObject particleData, float probability) {
        this.particle = particleData;
        this.probability = probability;
    }

    @NotNull
    public static AmbientParticle of(float probability, @NotNull JsonObject particleData) {
        return new AmbientParticle(particleData, probability);
    }

    @NotNull
    public static AmbientParticle of(@NotNull Particle particle, float probability, @Nullable Object data) {
        return new AmbientParticle(ParticleDataSerializer.of().serialize(particle, data), probability);
    }

    @NotNull
    public static AmbientParticle of(@NotNull Particle particle, float probability) {
        return new AmbientParticle(ParticleDataSerializer.of().serialize(particle, null), probability);
    }

    public float getProbability() {
        return probability;
    }

    @NotNull
    public JsonObject getParticle() {
        return particle;
    }
}
