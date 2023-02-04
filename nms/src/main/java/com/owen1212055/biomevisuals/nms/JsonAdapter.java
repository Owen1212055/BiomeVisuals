package com.owen1212055.biomevisuals.nms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.owen1212055.biomevisuals.api.types.biome.BiomeCategory;
import com.owen1212055.biomevisuals.api.types.biome.PrecipitationType;
import com.owen1212055.biomevisuals.api.types.biome.TemperatureModifier;
import com.owen1212055.biomevisuals.api.types.biome.effect.AdditionSound;
import com.owen1212055.biomevisuals.api.types.biome.effect.AmbientParticle;
import com.owen1212055.biomevisuals.api.types.biome.effect.AmbientSound;
import com.owen1212055.biomevisuals.api.types.biome.effect.GrassModifier;
import com.owen1212055.biomevisuals.api.types.biome.effect.MoodSound;
import com.owen1212055.biomevisuals.api.types.biome.effect.Music;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;

import java.lang.reflect.Type;
import java.util.Objects;

public class JsonAdapter {

    private static final Gson GSON;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(NamespacedKey.class, new JsonDeserializerAndSerializer<NamespacedKey>() {

            @Override
            public JsonElement serialize(NamespacedKey src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.toString());
            }

            @Override
            public NamespacedKey deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    return NamespacedKey.fromString(json.getAsJsonPrimitive().getAsString());
                } catch (Exception e) {
                    throw new JsonParseException(e);
                }
            }

        });
        builder.registerTypeAdapter(Color.class, new JsonDeserializerAndSerializer<Color>() {

            @Override
            public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.asRGB());
            }

            @Override
            public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    return Color.fromRGB(json.getAsJsonPrimitive().getAsInt());
                } catch (Exception e) {
                    throw new JsonParseException(e);
                }
            }

        });
        builder.registerTypeAdapter(AdditionSound.class, new JsonDeserializerAndSerializer<AdditionSound>() {

            @Override
            public JsonElement serialize(AdditionSound src, Type typeOfSrc, JsonSerializationContext context) {
                return ApiEntityConverter.serialize(src);
            }

            @Override
            public AdditionSound deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    return ApiEntityConverter.deserializeAdditionSound(json);
                } catch (Exception e) {
                    throw new JsonParseException(e);
                }
            }

        });
        builder.registerTypeAdapter(AmbientParticle.class, new JsonDeserializerAndSerializer<AmbientParticle>() {

            @Override
            public JsonElement serialize(AmbientParticle src, Type typeOfSrc, JsonSerializationContext context) {
                return ApiEntityConverter.serialize(src);
            }

            @Override
            public AmbientParticle deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    return ApiEntityConverter.deserializeAmbientParticle(json);
                } catch (Exception e) {
                    throw new JsonParseException(e);
                }
            }

        });
        builder.registerTypeAdapter(GrassModifier.class, new JsonDeserializerAndSerializer<GrassModifier>() {

            @Override
            public JsonElement serialize(GrassModifier src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.getKey());
            }

            @Override
            public GrassModifier deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    return Objects.requireNonNull(GrassModifier.getGrassModifier(json.getAsJsonPrimitive().getAsString()));
                } catch (Exception e) {
                    throw new JsonParseException(e);
                }
            }

        });
        builder.registerTypeAdapter(AmbientSound.class, new JsonDeserializerAndSerializer<AmbientSound>() {

            @Override
            public JsonElement serialize(AmbientSound src, Type typeOfSrc, JsonSerializationContext context) {
                return ApiEntityConverter.serialize(src);
            }

            @Override
            public AmbientSound deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    return ApiEntityConverter.deserializeAmbientSound(json);
                } catch (Exception e) {
                    throw new JsonParseException(e);
                }
            }

        });
        builder.registerTypeAdapter(MoodSound.class, new JsonDeserializerAndSerializer<MoodSound>() {

            @Override
            public JsonElement serialize(MoodSound src, Type typeOfSrc, JsonSerializationContext context) {
                return ApiEntityConverter.serialize(src);
            }

            @Override
            public MoodSound deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    return ApiEntityConverter.deserializeMoodSound(json);
                } catch (Exception e) {
                    throw new JsonParseException(e);
                }
            }

        });
        builder.registerTypeAdapter(Music.class, new JsonDeserializerAndSerializer<Music>() {

            @Override
            public JsonElement serialize(Music src, Type typeOfSrc, JsonSerializationContext context) {
                return ApiEntityConverter.serialize(src);
            }

            @Override
            public Music deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    return ApiEntityConverter.deserializeMusic(json);
                } catch (Exception e) {
                    throw new JsonParseException(e);
                }
            }

        });
        builder.registerTypeAdapter(BiomeCategory.class, new JsonDeserializerAndSerializer<BiomeCategory>() {

            @Override
            public JsonElement serialize(BiomeCategory src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.getKey());
            }

            @Override
            public BiomeCategory deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    return Objects.requireNonNull(BiomeCategory.getBiomeCategory(json.getAsJsonPrimitive().getAsString()));
                } catch (Exception e) {
                    throw new JsonParseException(e);
                }
            }

        });
        builder.registerTypeAdapter(PrecipitationType.class, new JsonDeserializerAndSerializer<PrecipitationType>() {

            @Override
            public JsonElement serialize(PrecipitationType src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.getKey());
            }

            @Override
            public PrecipitationType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    return Objects.requireNonNull(PrecipitationType.getPrecipitationType(json.getAsJsonPrimitive().getAsString()));
                } catch (Exception e) {
                    throw new JsonParseException(e);
                }
            }

        });
        builder.registerTypeAdapter(TemperatureModifier.class, new JsonDeserializerAndSerializer<TemperatureModifier>() {

            @Override
            public JsonElement serialize(TemperatureModifier src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.getKey());
            }

            @Override
            public TemperatureModifier deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    return Objects.requireNonNull(TemperatureModifier.getTemperatureModifier(json.getAsJsonPrimitive().getAsString()));
                } catch (Exception e) {
                    throw new JsonParseException(e);
                }
            }

        });

        GSON = builder.create();
    }

    public static JsonObject adapt(Object data) {
        return GSON.toJsonTree(data).getAsJsonObject();
    }

    public static <T> T adapt(JsonObject data, Class<T> dataType) {
        return GSON.fromJson(data, dataType);
    }

    private interface JsonDeserializerAndSerializer<T> extends JsonDeserializer<T>, JsonSerializer<T> {
    }

}
