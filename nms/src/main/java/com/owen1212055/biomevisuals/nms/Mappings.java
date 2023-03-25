package com.owen1212055.biomevisuals.nms;

import net.minecraft.core.RegistrySynchronization;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import xyz.jpenilla.reflectionremapper.ReflectionRemapper;

public class Mappings {

    public static final String NETWORK_CODEC;
    public static final String AMBIENT_PARTICLE_PROBABILITY;

    static {
        final ReflectionRemapper reflectionRemapper = ReflectionRemapper.forReobfMappingsInPaperJar();
        NETWORK_CODEC = reflectionRemapper.remapFieldName(RegistrySynchronization.class, "NETWORK_CODEC");
        AMBIENT_PARTICLE_PROBABILITY = reflectionRemapper.remapFieldName(AmbientParticleSettings.class, "probability");
    }

}
