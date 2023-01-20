package com.owen1212055.biomevisuals.api.types.biome.effect;

import org.bukkit.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BiomeEffectBuilder {

    private Color fogColor;
    private Color waterColor;
    private Color waterFogColor;
    private Color skyColor;

    @Nullable
    private Color foliageColorOverride;
    @Nullable
    private Color grassColorOverride;
    private GrassModifier grassColorModifier;
    @Nullable
    private AmbientParticle ambientParticleSettings;
    @Nullable
    private AmbientSound ambientSound;
    @Nullable
    private MoodSound ambientMoodSettings;
    @Nullable
    private AdditionSound ambientAdditionsSettings;
    @Nullable
    private Music backgroundMusic;

    private BiomeEffectBuilder() {
    }

    public static BiomeEffectBuilder newBuilder() {
        return new BiomeEffectBuilder();
    }

    /**
     * Sets fog color for this biome.
     *
     * @param fogColor color
     * @return self
     */
    public BiomeEffectBuilder fogColor(Color fogColor) {
        this.fogColor = fogColor;
        return this;
    }

    /**
     * Sets water color for this biome.
     *
     * @param waterColor color
     * @return self
     */
    public BiomeEffectBuilder waterColor(Color waterColor) {
        this.waterColor = waterColor;
        return this;
    }

    /**
     * Sets water fog color for this biome.
     *
     * @param waterFogColor color
     * @return self
     */
    public BiomeEffectBuilder waterFogColor(Color waterFogColor) {
        this.waterFogColor = waterFogColor;
        return this;
    }

    /**
     * Sets sky color for this biome.
     *
     * @param skyColor color
     * @return self
     */
    public BiomeEffectBuilder skyColor(Color skyColor) {
        this.skyColor = skyColor;
        return this;
    }

    /**
     * Overrides the color of the grass for this biome.
     *
     * @param override color
     * @return self
     */
    public BiomeEffectBuilder grassColorOverride(@Nullable Color override) {
        this.grassColorOverride = override;
        return this;
    }

    /**
     * Overrides the color of the foliage for this biome.
     *
     * @param override color
     * @return self
     */
    public BiomeEffectBuilder foliageColorOverride(@Nullable Color override) {
        this.foliageColorOverride = override;
        return this;
    }

    /**
     * Sets the grass color modifier for this biome.
     * <p>
     * This effects how the grass color appears at certain coordinates.
     *
     * @param grassColorModifier modifier
     * @return self
     */
    public BiomeEffectBuilder grassColorModifier(GrassModifier grassColorModifier) {
        this.grassColorModifier = grassColorModifier;
        return this;
    }

    /**
     * Sets ambient particle settings for this biome.
     *
     * @param particle ambient particle settings
     * @return self
     */
    public BiomeEffectBuilder ambientParticle(@Nullable AmbientParticle particle) {
        this.ambientParticleSettings = particle;
        return this;
    }

    /**
     * Sets ambient sound for this biome.
     *
     * @param ambientSound ambient sound
     * @return self
     */
    public BiomeEffectBuilder ambientSound(@Nullable AmbientSound ambientSound) {
        this.ambientSound = ambientSound;
        return this;
    }

    /**
     * Sets mood sound for this biome.
     *
     * @param moodSound mood sound
     * @return self
     */
    public BiomeEffectBuilder moodSound(@Nullable MoodSound moodSound) {
        this.ambientMoodSettings = moodSound;
        return this;
    }

    /**
     * Sets addition sound for this biome.
     *
     * @param additionSound mood sound
     * @return self
     */
    public BiomeEffectBuilder additionSound(@Nullable AdditionSound additionSound) {
        this.ambientAdditionsSettings = additionSound;
        return this;
    }

    /**
     * Sets music for this biome.
     *
     * @param music music
     * @return self
     */
    public BiomeEffectBuilder music(@Nullable Music music) {
        this.backgroundMusic = music;
        return this;
    }

    @NotNull
    public BiomeEffect build() {
        return new BiomeEffect(
                fogColor,
                waterColor,
                waterFogColor,
                skyColor,
                foliageColorOverride,
                grassColorOverride,
                grassColorModifier,
                ambientParticleSettings,
                ambientSound,
                ambientMoodSettings,
                ambientAdditionsSettings,
                backgroundMusic);
    }

}
