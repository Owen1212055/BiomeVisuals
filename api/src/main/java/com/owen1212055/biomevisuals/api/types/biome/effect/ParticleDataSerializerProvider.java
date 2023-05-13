package com.owen1212055.biomevisuals.api.types.biome.effect;

import java.util.ServiceLoader;

class ParticleDataSerializerProvider {

    static final ParticleDataSerializer INSTANCE = ServiceLoader.load(ParticleDataSerializer.class, ParticleDataSerializer.class.getClassLoader())
            .findFirst()
            .orElseThrow();
}
