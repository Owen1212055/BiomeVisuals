package com.owen1212055.biomevisuals.api.types.biome.effect;

import com.google.gson.JsonObject;
import org.bukkit.Particle;

public interface ParticleDataSerializer {

    static ParticleDataSerializer of() {
        return ParticleDataSerializerProvider.INSTANCE;
    }

    JsonObject serialize(Particle particle, Object data);

}
