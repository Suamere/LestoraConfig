package com.lestora.config;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("lestora_config")
public class LestoraConfigMod {
    public LestoraConfigMod(FMLJavaModLoadingContext constructContext) {
        ModNetworking.register();
        constructContext.registerConfig(ModConfig.Type.COMMON, SharedConfigHandler.LIGHTING_CONFIG, "lestora-lighting.toml");
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
    }
}