package com.owen1212055.biomevisuals.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.owen1212055.biomevisuals.Main;
import com.owen1212055.biomevisuals.api.RegistryType;
import com.owen1212055.biomevisuals.nms.KeyedOverride;
import com.owen1212055.biomevisuals.parsers.OverrideParser;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class BiomeVisualCommand extends Command {

    private static final String USAGE_MESSAGE = "/biomevisuals <reload|overrides>";
    private static final TextColor MAIN_COLOR = TextColor.color(100, 155, 255);
    private static final TextColor ACCENT_COLOR = TextColor.color(100, 195, 230);
    private static final TextColor TEXT_COLOR = TextColor.color(1, 105, 200);

    private static final Component PREFIX = Component.text(">> ", ACCENT_COLOR).append(Component.text("BiomeVisuals: ", MAIN_COLOR));

    private final Main main;

    public BiomeVisualCommand(Main main) {
        super("biomevisuals", "Main command for some utilities with the biome visual plugin.", USAGE_MESSAGE, List.of());
        this.main = main;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Component.text("Invalid usage! " + USAGE_MESSAGE, NamedTextColor.RED));
            return true;
        }

        switch (args[0]) {
            case "reload": {
                reload(sender);
                return true;
            }
            case "overrides": {
                overrides(sender);
                return true;
            }

        }

        return true;
    }

    public void reload(CommandSender sender) {
        for (List<KeyedOverride> value : this.main.getRegisteredOverrides().values()) {
            value.removeIf(KeyedOverride::fromFile);
        }

        try {
            List<Map<RegistryType, List<KeyedOverride>>> overrides = OverrideParser.readOverrides();

            int overrideCount = 0;
            for (Map<RegistryType, List<KeyedOverride>> map : overrides) {
                this.main.getRegisteredOverrides().putAll(map);
                for (RegistryType type : map.keySet()) {
                    overrideCount += map.get(type).size();
                }
            }

            sender.sendMessage(
                    PREFIX.append(Component.text("Reloaded file overrides, registering %s overrides.".formatted(overrideCount), TEXT_COLOR))
            );
        } catch (Exception e) {
            Main.LOGGER.error("Failed to reload.", e);
            sender.sendMessage(PREFIX.append(Component.text("Failed to reload!", TEXT_COLOR)));
        }
    }

    public void overrides(CommandSender sender) {
        sender.sendMessage(PREFIX.append(Component.text("Overrides", TEXT_COLOR)));
        sender.sendMessage(join(Component.text("Hook Active:", ACCENT_COLOR), Component.text(Main.HOOK_ACTIVE, TEXT_COLOR)));
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();


        for (Map.Entry<RegistryType, List<KeyedOverride>> entry : this.main.getRegisteredOverrides().entrySet()) {
            sender.sendMessage(Component.text("Hook Type:", ACCENT_COLOR, TextDecoration.BOLD));
            sender.sendMessage(Component.text(entry.getKey().getKey().toString(), TEXT_COLOR));

            sender.sendMessage(Component.text("Overrides:", ACCENT_COLOR, TextDecoration.BOLD));

            int i = 0;
            for (KeyedOverride type : entry.getValue()) {
                i++;

                sender.sendMessage(Component.text(i + ":", ACCENT_COLOR, TextDecoration.BOLD));
                sender.sendMessage(join(Component.text("Overrides:", ACCENT_COLOR), Component.text(type.key().toString(), TEXT_COLOR)));
                sender.sendMessage(join(Component.text("Valid:", ACCENT_COLOR), Component.text(type.valid().getAsBoolean(), TEXT_COLOR)));
                sender.sendMessage(join(Component.text("From File:", ACCENT_COLOR), Component.text(type.fromFile(), TEXT_COLOR)));
                sender.sendMessage(join(
                        Component.text("Value:", ACCENT_COLOR),
                        Component.text("(Hover to View)", TEXT_COLOR)
                                .hoverEvent(Component.text(gson.toJson(type.object()), ACCENT_COLOR))
                ));
            }
        }
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args, @Nullable Location location) throws IllegalArgumentException {
        return List.of("reload", "overrides");
    }

    private static Component join(Component... components) {
        return Component.join(JoinConfiguration.separator(Component.space()), components);
    }
}
