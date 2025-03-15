package com.lestora;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// A class to handle config events on the server.
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SharedConfigHandler {
    public static final ForgeConfigSpec LIGHTING_CONFIG;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> LIGHT_LEVELS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("light_levels");
        LIGHT_LEVELS = builder.comment("List of item=light level pairs")
                .defineList("items",
                        Arrays.asList("minecraft:torch=14", "minecraft:lava_bucket=10", "minecraft:glowstone=15"),
                        o -> o instanceof String && ((String)o).contains("="));
        builder.pop();
        LIGHTING_CONFIG = builder.build();
    }

    static List<String> getLightLevelConfig() {
        return new ArrayList<>(LIGHT_LEVELS.get());
    }

    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == LIGHTING_CONFIG) {
            if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
                // We're on the server – use server behavior.
                System.err.println("Load from Server Plz");
                Config.setLightLevelsMap(getLightLevelConfig(), "SERVER");
            } else {
                // We're on the client.
                System.err.println("Load from Client Plz");
                Config.setLightLevelsMap(getLightLevelConfig(), "CLIENT");
                // Optionally, you might want to ignore local changes if a server is authoritative.
            }
        }
    }

    @SubscribeEvent
    static void onReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == LIGHTING_CONFIG) {
            if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
                // We're on the server – use server behavior.
                System.err.println("Reload from Server Plz");
                Config.setLightLevelsMap(getLightLevelConfig(), "SERVER");
            } else {
                // We're on the client.
                System.err.println("Reload from Client Plz");
                Config.setLightLevelsMap(getLightLevelConfig(), "CLIENT");
                // Optionally, you might want to ignore local changes if a server is authoritative.
            }
        }
    }
}