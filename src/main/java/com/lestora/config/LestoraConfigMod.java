package com.lestora.config;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("lestora_config")
public class LestoraConfigMod {
    private static final Logger LOGGER = LogManager.getLogger();

    final String BLUE = "\033[34m";
    final String WHITE = "\033[37m";
    final String RESET = "\033[0m";

    public LestoraConfigMod(FMLJavaModLoadingContext constructContext) {
        ModNetworking.registerLightConfig();
        constructContext.registerConfig(ModConfig.Type.COMMON, LightConfigHandler.LIGHTING_CONFIG, "lestora-lighting.toml");
        LightConfig.subscribe((rl, oldVal, newVal) -> {
            if (!LightConfig.configFirstLoaded()) return;

            if (oldVal == null && newVal != null) {
                LOGGER.info(
                        BLUE + "Added Light Level configuration for: " + rl.getResource() +
                                (rl.getAmount() > 1 ? BLUE + " (group of " + WHITE + rl.getAmount() + BLUE + ")" : "") +
                                BLUE + " -- New Light Level: " + WHITE + newVal + RESET
                );
            }
            else if (newVal == null && oldVal != null) {
                LOGGER.info(
                        BLUE + "Removed Light Level configuration for: " + rl.getResource() +
                                (rl.getAmount() > 1 ? BLUE + " (group of " + WHITE + rl.getAmount() + BLUE + ")" : "") +
                                BLUE + " -- Old Light Level: " + WHITE + oldVal + RESET
                );
            }
            else if (newVal != null) {
                LOGGER.info(
                        BLUE + "Changed Light Level configuration for: " + rl.getResource() +
                                (rl.getAmount() > 1 ? BLUE + " (group of " + WHITE + rl.getAmount() + BLUE + ")" : "") +
                                BLUE + " -- From Light Level: " + WHITE + oldVal +
                                BLUE + " To Light Level: " + WHITE + newVal + RESET
                );
            }
        });


        ModNetworking.registerBiomeConfig();
        constructContext.registerConfig(ModConfig.Type.COMMON, BiomeConfigHandler.BIOME_CONFIG, "lestora-biome.toml");
        BiomeConfig.subscribe((rl, oldVal, newVal) -> {
            if (!BiomeConfig.configFirstLoaded()) return;

            if (oldVal == null && newVal != null) {
                System.out.println(
                        BLUE + "Added Biome configuration for: " + rl + BLUE + " -- New Biome Temp: " + WHITE + newVal + RESET
                );
            }
            else if (newVal == null && oldVal != null) {
                System.out.println(
                        BLUE + "Removed Biome configuration for: " + rl + BLUE + " -- Old Biome Temp: " + WHITE + oldVal + RESET
                );
            }
            else if (newVal != null) {
                System.out.println(
                        BLUE + "Changed Biome configuration for: " + rl + BLUE + " -- From Biome Temp: " + WHITE + oldVal + BLUE + " To Biome Temp: " + WHITE + newVal + RESET
                );
            }
        });
    }
}