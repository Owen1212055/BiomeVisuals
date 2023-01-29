package com.owen1212055.biomevisuals.api.types.dimension;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

// Not Implemented
@Deprecated
public class DimensionDataBuilder {

    private Integer logicalHeight;
    private NamespacedKey infiniburn;
    private NamespacedKey dimensionEffect;
    private Float ambientLight;
    private Boolean respawnAnchorWorks;
    private Boolean hasRaids;
    private Integer minY;
    private Integer height;
    private Boolean natural;
    private Integer coordinateScale;
    private Boolean piglinSafe;
    private Long fixedTime;
    private Boolean bedWorks;
    private Boolean hasSkylight;
    private Boolean hasCeiling;
    private Boolean isUltraWarm;

    @Deprecated
    public static DimensionDataBuilder newBuilder() {
        return new DimensionDataBuilder();
    }

    /**
     * Sets the logical height of this dimension.
     * Has no effect on the client
     *
     * @param logicalHeight logical height
     * @return self
     */
    public DimensionDataBuilder logicalHeight(int logicalHeight) {
        this.logicalHeight = logicalHeight;
        return this;
    }

    /**
     * Sets what blocks burn infinitely in this dimension.
     * Has no effect on the client
     *
     * @param infiniburn key
     * @return self
     */
    public DimensionDataBuilder infiniburn(NamespacedKey infiniburn) {
        this.infiniburn = infiniburn;
        return this;
    }

    /**
     * Sets the dimension effects for this biome.
     * <p>Currently this is predefined on the client, determines the following:
     * <p>- Sunrise color
     * <p>- Cloud level
     * <p>- Has a ground
     * <p>- Sky type
     * <p>- Forced Brightness
     * <p>- Constant ambient light
     *
     * @param dimensionEffect key
     * @return self
     */
    public DimensionDataBuilder dimensionEffect(NamespacedKey dimensionEffect) {
        this.dimensionEffect = dimensionEffect;
        return this;
    }

    /**
     * Sets the ambient light for this biome.
     *
     * @param ambientLight ambient light
     * @return self
     */
    public DimensionDataBuilder setAmbientLight(float ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets if respawn anchors work in this dimension.
     * Has no effect on the client
     *
     * @param respawnAnchorWorks respawn anchors work
     * @return self
     */
    public DimensionDataBuilder setRespawnAnchorWorks(boolean respawnAnchorWorks) {
        this.respawnAnchorWorks = respawnAnchorWorks;
        return this;
    }

    /**
     * Sets if raids occur in this dimension.
     * Has no effect on the client
     *
     * @param hasRaids has raids
     * @return self
     */
    public DimensionDataBuilder setHasRaids(boolean hasRaids) {
        this.hasRaids = hasRaids;
        return this;
    }

    /**
     * Sets the min height for this dimension.
     * This will have some unintended consequences, only touch if you know what you are doing.
     *
     * @param minY min height
     * @return self
     */
    public DimensionDataBuilder setMinY(int minY) {
        this.minY = minY;
        return this;
    }

    /**
     * Sets the height for this dimension.
     * This will have some unintended consequences, only touch if you know what you are doing.
     *
     * @param height height
     * @return self
     */
    public DimensionDataBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * Sets if this dimension is natural.
     * If not natural, clocks/compasses will not work.
     *
     * @param natural natural
     * @return self
     */
    public DimensionDataBuilder setNatural(boolean natural) {
        this.natural = natural;
        return this;
    }

    /**
     * Sets the coordinate scale of the dimension relative to the overworld.
     * Has no effect on the client.
     *
     * @param coordinateScale scale
     * @return self
     */
    public DimensionDataBuilder setCoordinateScale(int coordinateScale) {
        this.coordinateScale = coordinateScale;
        return this;
    }

    /**
     * Determines if piglins are safe in this dimension or not.
     * This causes them to shake in this dimension if they are not immune to zombification.
     *
     * @param piglinSafe piglin safe
     * @return self
     */
    public DimensionDataBuilder setPiglinSafe(boolean piglinSafe) {
        this.piglinSafe = piglinSafe;
        return this;
    }

    /**
     * Sets if beds work in this dimension.
     * Has no effect on the client
     *
     * @param bedWorks bed works
     * @return self
     */
    public DimensionDataBuilder setBedWorks(boolean bedWorks) {
        this.bedWorks = bedWorks;
        return this;
    }

    /**
     * Sets the fixed time for this dimension.
     * Has no effect on the client
     *
     * @param fixedTime fixed time
     * @return self
     */
    public DimensionDataBuilder setFixedTime(long fixedTime) {
        this.fixedTime = fixedTime;
        return this;
    }

    /**
     * Determines if this dimension has skylight.
     * Disabling this prevents thunder and skylight from rendering.
     *
     * @param hasSkylight has skylight
     * @return self
     */
    public DimensionDataBuilder setHasSkylight(boolean hasSkylight) {
        this.hasSkylight = hasSkylight;
        return this;
    }

    /**
     * Determines if this dimension has a ceiling.
     * Disabling this prevents thunder.
     *
     * @param hasCeiling has as ceiling
     * @return self
     */
    public DimensionDataBuilder setHasCeiling(boolean hasCeiling) {
        this.hasCeiling = hasCeiling;
        return this;
    }

    /**
     * Determines if this dimension is ultra warm.
     *
     * @param isUltraWarm is ultra warm
     * @return self
     */
    public DimensionDataBuilder setIsUltraWarm(boolean isUltraWarm) {
        this.isUltraWarm = isUltraWarm;
        return this;
    }

    @NotNull
    public DimensionData build() {
        return new DimensionData(logicalHeight, infiniburn, dimensionEffect, ambientLight, respawnAnchorWorks, hasRaids, minY, height, natural, coordinateScale, piglinSafe, fixedTime, bedWorks, hasSkylight, hasCeiling, isUltraWarm);
    }
}