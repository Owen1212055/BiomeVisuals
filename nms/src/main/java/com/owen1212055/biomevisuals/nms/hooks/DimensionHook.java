package com.owen1212055.biomevisuals.nms.hooks;

/*
Scrapped implemention, the reason being that the dimension type data also needs to be sent when the client
joins/respawns in that dimension.

This has the issue that it isn't exactly easy to determine what type that dimension actually is, because it's sent separately.
Jank, so I have to delay this and figure out another solution.
 */
public class DimensionHook {

    // // Custom codec that wraps around the pre-existing codec.
    //        // This is meant to basically allow us to encode our own values before it is sent to the client.
    //        LevelStem.CODEC.getClass(); // Initilize codec
    //        Codec<Supplier<DimensionType>> CAPTURED_OTHER_CODEC = DimensionType.CODEC;  // Capture the codec stored in the variable, we will be replacing it.
    //        Codec<Supplier<DimensionType>> CUSTOM_DIMENSIONTYPE_CODEC = new Codec<>() {
    //
    //            @Override
    //            public <T> DataResult<T> encode(Supplier<DimensionType> input, DynamicOps<T> ops, T prefix) {
    //                DataResult<JsonElement> result = CAPTURED_OTHER_CODEC.encode(input, JsonOps.INSTANCE, JsonOps.INSTANCE.empty());
    //                result.error().ifPresent((partialresult) -> {
    //                    throw new IllegalStateException("Failed to decode packet data to json: " + partialresult.message());
    //                });
    //                JsonElement element = result.get().orThrow();
    //                System.out.println(element);
    //
    //                var dataresult = CAPTURED_OTHER_CODEC.decode(JsonOps.INSTANCE, element);
    //                var error = dataresult.error();
    //                if (error.isPresent()) {
    //                    System.out.println("Failed to decode json object, skipping encode." + error.get().message());
    //                    return CAPTURED_OTHER_CODEC.encode(input, ops, prefix);
    //                } else {
    //                    Supplier<DimensionType> holder = dataresult.map(Pair::getFirst).result().orElseThrow();
    //                    return CAPTURED_OTHER_CODEC.encode(holder, ops, prefix);
    //                }
    //            }
    //
    //            @Override
    //            public <T> DataResult<Pair<Supplier<DimensionType>, T>> decode(DynamicOps<T> ops, T input) {
    //                return CAPTURED_OTHER_CODEC.decode(ops, input);
    //            }
    //        };
    //
    //        try {
    //            ReflectionRemapper reflectionRemapper = ReflectionRemapper.forReobfMappingsInPaperJar();
    //            Class<?> registryClass = DimensionType.class;
    //            String networkField = reflectionRemapper.remapFieldName(registryClass, "CODEC");
    //
    //            UnsafeUtils.unsafeStaticSet(registryClass.getDeclaredField(networkField), CUSTOM_DIMENSIONTYPE_CODEC);
    //        } catch (Exception e) {
    //            throw new RuntimeException("Failed to inject codec, effects will not be present", e);
    //        }
}
