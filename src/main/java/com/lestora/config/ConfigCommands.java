package com.lestora.config;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

@Mod.EventBusSubscriber
class ConfigCommands {

    @SubscribeEvent
    static void onRegisterClientCommands(RegisterCommandsEvent event) {
        var root = Commands.literal("lestora");

        listDefaultLightConfigs(root);
        listUserLightConfigs(root);
        listUniqueLightConfigs(root);
        registerWhatAmIHolding(root);

        event.getDispatcher().register(root);
    }

    static void listDefaultLightConfigs(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("config")
                .then(Commands.literal("lighting")
                        .then(Commands.literal("listDefaultConfig")
                                .executes(ctx -> {
                                    ctx.getSource().sendSuccess(() -> Component.literal("§bLestora Config -- Default Light Configurations"), false);
                                    for (var entry : LightConfig.getDefaultLightLevels().entrySet())
                                        ctx.getSource().sendSuccess(() -> Component.literal("§b" + entry.getKey().getResource() + "(" + entry.getKey().getAmount() + ") = " + entry.getValue()), false);
                                    return 1;
                                })
                        )
                )
        );
    }

    static void listUserLightConfigs(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("config")
                .then(Commands.literal("lighting")
                        .then(Commands.literal("listUserConfig")
                                .executes(ctx -> {
                                    ctx.getSource().sendSuccess(() -> Component.literal("§bLestora Config -- User Light Configurations"), false);
                                    for (var entry : LightConfig.getLightLevels().entrySet())
                                        ctx.getSource().sendSuccess(() -> Component.literal("§b" + entry.getKey().getResource() + "(" + entry.getKey().getAmount() + ") = " + entry.getValue()), false);
                                    return 1;
                                })
                        )
                )
        );
    }

    static void listUniqueLightConfigs(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("config")
                .then(Commands.literal("lighting")
                        .then(Commands.literal("listUniqueConfig")
                                .executes(ctx -> {
                                    ctx.getSource().sendSuccess(() -> Component.literal("§bLestora Config -- Unique Light Configurations"), false);
                                    for (var entry : LightConfig.getUniqueLightLevels().entrySet())
                                        ctx.getSource().sendSuccess(() -> Component.literal("§b" + entry.getKey().getResource() + "(" + entry.getKey().getAmount() + ") = " + entry.getValue()), false);
                                    return 1;
                                })
                        )
                )
        );
    }

    private static void registerWhatAmIHolding(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("config")
                .then(Commands.literal("whatAmIHolding")
                        .executes(ctx -> {
                            var player = Minecraft.getInstance().player;
                            if (player == null) {
                                ctx.getSource().sendFailure(Component.literal("This command can only be run by a player."));
                                return 0;
                            }

                            var mainStack = player.getMainHandItem();
                            var offStack = player.getOffhandItem();

                            var mainRL = ForgeRegistries.ITEMS.getKey(mainStack.getItem());
                            var offRL = ForgeRegistries.ITEMS.getKey(offStack.getItem());

                            String mainMsg = "Main Hand: " + (mainRL != null ? mainRL.toString() : "Empty");
                            String offMsg = "Off Hand: " + (offRL != null ? offRL.toString() : "Empty");

                            ctx.getSource().sendSuccess(() -> Component.literal(mainMsg), false);
                            ctx.getSource().sendSuccess(() -> Component.literal(offMsg), false);
                            return 1;
                        })
                ));
    }
}