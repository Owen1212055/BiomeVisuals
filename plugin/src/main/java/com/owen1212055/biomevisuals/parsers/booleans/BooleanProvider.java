package com.owen1212055.biomevisuals.parsers.booleans;

import com.google.gson.JsonObject;

public interface BooleanProvider {

    String getKey();

    boolean parse(JsonObject object);
}
