<h1 align="center">BiomeVisuals</h1>
<p align="center">BiomeVisuals allows you to override biome data before it is sent to the client, allowing you to display special effects.</p>

![2021-12-22_15 52 37](https://user-images.githubusercontent.com/23108066/147176650-2585395b-6ab0-4936-9815-6af428354689.png)

# Documentation

### Information
Minecraft currently allows you to register your own biomes via datapacks which allow for adding custom features, caves, mountains, and much more. This had the benefit of making all of the current Minecraft biomes being able to be modified using datapacks as well.

This plugin simply allows you to change the biome data before it is sent to the client, allowing you to modify things such as the color of the biome. 

### Adding Overrides
Currently, there are two ways to define overrides. You currently are only able to add biome overrides, however it is possible in the future this can be expanded to more things.

#### API
The plugins offers a built in api that can be used.

The override registry must be fetched with this.
```java
OverrideRegistry registry = (OverrideRegistry) Bukkit.getPluginManager().getPlugin("BiomeVisuals");
```

Then, you can register the biome override. This signifies that the plains biome will have the override with the biome data specified.
```java
registry.registerBiomeOverride(NamespacedKey.minecraft("plains"), BiomeDataBuilder.newBuilder()
                .precipitation(PrecipitationType.SNOW)
                .effect(
                        BiomeEffectBuilder.newBuilder()
                                .foliageColorOverride(Color.BLACK)
                                .build()
                )
                .build());
 ```
 
 You also have the ability to manually set overrides by listening to an event. Note, this will not merge with the pre-existing entry, but will instead override it entirely.
 ```java
 @EventHandler
    public void onRegistrySend(final @NotNull BiomeRegistrySendEvent event) {
        event.setRegistryEntry(NamespacedKey.minecraft("plains"),
                BiomeDataBuilder.newBuilder()
                        .temperature(5f)
                        .precipitation(PrecipitationType.SNOW)
                        .temperatureModifier(TemperatureModifier.FROZEN)
                        .downfall(5F)
                        .category(BiomeCategory.BEACH)
                        .effect(
                                BiomeEffectBuilder.newBuilder()
                                        .foliageColorOverride(Color.BLACK)
                                        .grassColorOverride(Color.OLIVE)
                                        .skyColor(Color.GREEN)
                                        .waterColor(Color.FUCHSIA)
                                        .waterFogColor(Color.AQUA)
                                        .fogColor(Color.BLUE)
                                        .grassColorModifier(GrassModifier.DARK_FOREST)
                                        .ambientParticle(AmbientParticle.of(Particle.ASH, 5, null))
                                        .ambientSound(AmbientSound.of(Sound.ITEM_GOAT_HORN_SOUND_0))
                                        .moodSound(MoodSound.of(Sound.ITEM_GOAT_HORN_SOUND_0, 1, 1, 1))
                                        .additionSound(AdditionSound.of(Sound.ITEM_GOAT_HORN_SOUND_2, 5))
                                        .music(Music.of(Sound.AMBIENT_BASALT_DELTAS_ADDITIONS, 1, 1, true))
                                        .build()
                        )
                        .build());
```
 
 
#### File Importing
The plugin will automatically try to import overrides that are defined in the ``overrides`` directory located in the plugin directory. (``BiomeVisuals/overrides``)
Inorder to add an override, you must first define the registry type that you are overriding. This is incase if any new overrides are added in the future.
Simply create a new file, for example, ``my_cool_override.json``.

##### Currently, the supported override types are: ``worldgen/biome``.

Then, you will add the json object for an override, which is:
```json
{
  "key": "minecraft:plains",
  "override": {},
  "condition": {
    "type": ""
  }
}
 ```
#### The **key** represents the key of item being overwritten.
This usually represents the namespace of something, in this case, a biome.
#### The **override** field represents all fields that will be replaced in the original object.
See https://minecraft.fandom.com/wiki/Biome/JSON_format for fields that can be replaced.
For converting colors, I recommend using http://www.shodor.org/stella2java/rgbint.html.
#### The **condition** field represents a predicate, there is a chance that this may change in the future but my goal was to be able to assign "holidays" for example.

##### Current valid conditions
```json
"condition": {
        "type": "biomevisuals:static",
        "value": true
}
 ```
 Value will always return as defined in the value field, in this case ``true``.
 ```json
"condition": {
        "type": "biomevisuals:date_range",
        "min_date": "2021-12-20",
        "max_date": "2021-12-31",
        "ignore_year": true
      }
 ```
 Value will return true if the current date is within the range of the two given dates.
 Ignore year causes the year in the min/max date field to be ignored, useful for re-occuring dates.


#### JSON Example
```json
{
  "minecraft:worldgen/biome": [
    {
      "key": "minecraft:plains",
      "override": {
        "effects": {
          "sky_color": 1
        }
      },
      "condition": {
        "type": "biomevisuals:static",
        "value": true
      }
    },
    {
      "key": "minecraft:forest",
      "override": {
        "effects": {
          "sky_color": 15138811,
          "foliage_color": 13434879,
          "grass_color": 13434879,
          "particle": {
            "options": {
              "type": "minecraft:snowflake"
            },
            "probability": 0.01428
          }
        }
      },
      "condition": {
        "type": "biomevisuals:date_range",
        "min_date": "2021-12-20",
        "max_date": "2021-12-31",
        "ignore_year": true
      }
    }
  ]
}
```

<h1 align="center">Need help? Feel free to join my discord for help: https://discord.gg/APaZK9tvkw</h1> 
