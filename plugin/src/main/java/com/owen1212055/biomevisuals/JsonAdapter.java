package com.owen1212055.biomevisuals;

import com.google.gson.*;
import com.owen1212055.biomevisuals.api.types.biome.*;
import com.owen1212055.biomevisuals.api.types.biome.effect.*;
import com.owen1212055.biomevisuals.api.types.dimension.*;
import com.owen1212055.biomevisuals.nms.*;
import org.bukkit.*;

public class JsonAdapter {

    private static final Gson GSON;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(NamespacedKey.class, (JsonSerializer<NamespacedKey>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()));
        builder.registerTypeAdapter(Color.class, (JsonSerializer<Color>) (src, typeOfSrc, context) -> new JsonPrimitive(src.getRGB()));
        builder.registerTypeAdapter(AdditionSound.class, (JsonSerializer<AdditionSound>) (src, typeOfSrc, context) -> ApiEntityConverter.convert(src));
        builder.registerTypeAdapter(AmbientParticle.class, (JsonSerializer<AmbientParticle>) (src, typeOfSrc, context) -> ApiEntityConverter.convert(src));
        builder.registerTypeAdapter(GrassModifier.class, (JsonSerializer<GrassModifier>) (src, typeOfSrc, context) -> new JsonPrimitive(src.getKey()));
        builder.registerTypeAdapter(MoodSound.class, (JsonSerializer<MoodSound>) (src, typeOfSrc, context) -> ApiEntityConverter.convert(src));
        builder.registerTypeAdapter(Music.class, (JsonSerializer<Music>) (src, typeOfSrc, context) -> ApiEntityConverter.convert(src));
        builder.registerTypeAdapter(BiomeCategory.class, (JsonSerializer<BiomeCategory>) (src, typeOfSrc, context) -> new JsonPrimitive(src.getKey()));
        builder.registerTypeAdapter(PrecipitationType.class, (JsonSerializer<PrecipitationType>) (src, typeOfSrc, context) -> new JsonPrimitive(src.getKey()));
        builder.registerTypeAdapter(TemperatureModifier.class, (JsonSerializer<TemperatureModifier>) (src, typeOfSrc, context) -> new JsonPrimitive(src.getKey()));

        GSON = builder.create();
    }


    public static JsonObject adapt(BiomeData data) {
        return GSON.toJsonTree(data).getAsJsonObject();
    }

    public static JsonObject adapt(DimensionData data) {
        return GSON.toJsonTree(data).getAsJsonObject();
    }
}
