package com.lestora.config;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.ArrayList;
import java.util.List;

// A class to handle config events on the server.
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
class BiomeConfigHandler {
    public static final ForgeConfigSpec BIOME_CONFIG;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> BIOME_TEMPS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("biome_temps");

        List<String> defaultTemps = new ArrayList<>();
        for (var defaultTemp : BiomeConfig.getDefaultBiomeTemps().entrySet()) {
            defaultTemps.add(defaultTemp.getKey().toString() + "=" + defaultTemp.getValue());
        }

        BIOME_TEMPS = builder.comment("List of biome=temperature pairs. Temperature must be in range -1.0 to 2.0")
                .defineList("items", defaultTemps, o -> o instanceof String && ((String)o).contains("="));
        builder.pop();
        BIOME_CONFIG = builder.build();
    }

    static List<String> getBiomeTempConfig() {
        return new ArrayList<>(BIOME_TEMPS.get());
    }

    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == BIOME_CONFIG) {
            if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
                BiomeConfig.setBiomeTempsMap(getBiomeTempConfig(), "SERVER");
            } else {
                BiomeConfig.setBiomeTempsMap(getBiomeTempConfig(), "CLIENT");
            }
        }
    }

    @SubscribeEvent
    static void onReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == BIOME_CONFIG) {
            if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
                BiomeConfig.setBiomeTempsMap(getBiomeTempConfig(), "SERVER");
            } else {
                BiomeConfig.setBiomeTempsMap(getBiomeTempConfig(), "CLIENT");
            }
        }
    }
}