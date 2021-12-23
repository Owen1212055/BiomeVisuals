package com.owen1212055.biomevisuals.nms;

import com.google.gson.*;
import org.bukkit.*;

import java.util.function.*;

public record KeyedOverride(NamespacedKey key, JsonObject object, BooleanSupplier valid, boolean fromFile) {
}
