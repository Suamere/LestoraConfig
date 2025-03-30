package com.lestora.config;

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
class SharedConfigHandler {
    public static final ForgeConfigSpec LIGHTING_CONFIG;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> LIGHT_LEVELS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("light_levels");

        List<String> defaultLevels = new ArrayList<>();
        for (var defaultLL : LightConfig.getDefaultLightLevels().entrySet()) {
            defaultLevels.add(defaultLL.getKey().getResource().toString() + "(" + defaultLL.getKey().getAmount() + ")=" + defaultLL.getValue());
        }

        LIGHT_LEVELS = builder.comment("List of item=light level pairs")
                .defineList("items", defaultLevels, o -> o instanceof String && ((String)o).contains("="));
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
                LightConfig.setLightLevelsMap(getLightLevelConfig(), "SERVER");
            } else {
                LightConfig.setLightLevelsMap(getLightLevelConfig(), "CLIENT");
            }
        }
    }

    @SubscribeEvent
    static void onReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == LIGHTING_CONFIG) {
            if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
                LightConfig.setLightLevelsMap(getLightLevelConfig(), "SERVER");
            } else {
                LightConfig.setLightLevelsMap(getLightLevelConfig(), "CLIENT");
            }
        }
    }
}