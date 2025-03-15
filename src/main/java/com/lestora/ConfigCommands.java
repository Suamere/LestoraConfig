package com.lestora;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
class ConfigCommands {

    @SubscribeEvent
    static void onRegisterClientCommands(RegisterCommandsEvent event) {
        var root = Commands.literal("lestora");

        listLightConfigs(root);

        event.getDispatcher().register(root);
    }

    static void listLightConfigs(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(Commands.literal("config")
                .then(Commands.literal("lighting")
                        .then(Commands.literal("list")
                                .executes(ctx -> {
                                    var lightConfigs = LestoraConfig.getLightLevels();
                                    // Send header message to the player
                                    ctx.getSource().sendSuccess(
                                            () -> Component.literal("§bLestora Config -- Light Configurations"),
                                            false
                                    );
                                    for (var entry : lightConfigs.entrySet()) {
                                        ctx.getSource().sendSuccess(
                                                () -> Component.literal("§b" + entry.getKey().getResource() + "(" + entry.getKey().getAmount() + ") = " + entry.getValue()),
                                                false
                                        );
                                    }
                                    return 1;
                                })
                        )
                )
        );
    }
}