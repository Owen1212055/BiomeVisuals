package com.owen1212055.biomevisuals.api.types.dimension;

import com.google.gson.annotations.*;
import org.bukkit.*;

// Not Implemented
@Deprecated
public record DimensionData(
        @SerializedName("logical_height") Integer logicalHeight,
        NamespacedKey infiniburn,
        @SerializedName("effects") NamespacedKey dimensionEffect,
        @SerializedName("ambient_light") Float ambientLight,
        @SerializedName("respawn_anchor_works") Boolean respawnAnchorWorks,
        @SerializedName("has_raids") Boolean hasRaids,
        @SerializedName("min_y") Integer minY,
        Integer height,
        Boolean natural,
        @SerializedName("coordinate_scale") Integer coordinateScale,
        @SerializedName("piglin_safe") Boolean piglinSafe,
        @SerializedName("fixed_time") Long fixedTime,
        @SerializedName("bed_works") Boolean bedWorks,
        @SerializedName("has_skylight") Boolean hasSkylight,
        @SerializedName("has_ceiling") Boolean hasCeiling,
        @SerializedName("ultrawarm") Boolean isUltraWarm
) {

}
