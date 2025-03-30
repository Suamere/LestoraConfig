package com.lestora.config;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("lestora_config")
public class LestoraConfigMod {
    final String BLUE = "\033[34m";
    final String WHITE = "\033[37m";
    final String RESET = "\033[0m";

    public LestoraConfigMod(FMLJavaModLoadingContext constructContext) {
        ModNetworking.register();
        constructContext.registerConfig(ModConfig.Type.COMMON, SharedConfigHandler.LIGHTING_CONFIG, "lestora-lighting.toml");

        LightConfig.subscribe((rl, oldVal, newVal) -> {
            if (!LightConfig.configFirstLoaded()) return;

            if (oldVal == null && newVal != null) {
                System.out.println(
                        BLUE + "Added Light Level configuration for: " + rl.getResource() +
                                (rl.getAmount() > 1 ? BLUE + " (group of " + WHITE + rl.getAmount() + BLUE + ")" : "") +
                                BLUE + " -- New Light Level: " + WHITE + newVal + RESET
                );
            }
            else if (newVal == null && oldVal != null) {
                System.out.println(
                        BLUE + "Removed Light Level configuration for: " + rl.getResource() +
                                (rl.getAmount() > 1 ? BLUE + " (group of " + WHITE + rl.getAmount() + BLUE + ")" : "") +
                                BLUE + " -- Old Light Level: " + WHITE + oldVal + RESET
                );
            }
            else if (newVal != null) {
                System.out.println(
                        BLUE + "Changed Light Level configuration for: " + rl.getResource() +
                                (rl.getAmount() > 1 ? BLUE + " (group of " + WHITE + rl.getAmount() + BLUE + ")" : "") +
                                BLUE + " -- From Light Level: " + WHITE + oldVal +
                                BLUE + " To Light Level: " + WHITE + newVal + RESET
                );
            }
        });
    }
}