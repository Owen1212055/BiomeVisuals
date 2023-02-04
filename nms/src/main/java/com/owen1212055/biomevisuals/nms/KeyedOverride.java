package com.owen1212055.biomevisuals.nms;

import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;

import java.util.function.BooleanSupplier;

public record KeyedOverride(NamespacedKey key, JsonObject object, BooleanSupplier valid, boolean fromFile) {
}
