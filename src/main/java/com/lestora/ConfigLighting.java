//package com.lestora;
//
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.common.ForgeConfigSpec;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.event.config.ModConfigEvent;
//
//import java.util.*;
//
//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
//public class ConfigLighting {
//    public static final ForgeConfigSpec LIGHTING_CONFIG;
//    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> LIGHT_LEVELS;
//
//    static {
//        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
//        builder.push("light_levels");
//        LIGHT_LEVELS = builder.comment("List of item=light level pairs")
//                .defineList("items",
//                        Arrays.asList("minecraft:torch=14", "minecraft:lava_bucket=10", "minecraft:glowstone=15"),
//                        o -> o instanceof String && ((String)o).contains("="));
//        builder.pop();
//        LIGHTING_CONFIG = builder.build();
//    }
//
//    static List<String> getLightLevelConfig() {
//        return new ArrayList<>(LIGHT_LEVELS.get());
//    }
//
//    @SubscribeEvent
//    static void onLoad(ModConfigEvent.Loading event) {
//        if (event.getConfig().getSpec() == ConfigLighting.LIGHTING_CONFIG) {
//            Config.setLightLevelsMap(getLightLevelConfig(), "CLIENT");
//        }
//    }
//
//    @SubscribeEvent
//    static void onReload(ModConfigEvent.Reloading event) {
//        if (event.getConfig().getSpec() == ConfigLighting.LIGHTING_CONFIG) {
//            Config.setLightLevelsMap(getLightLevelConfig(), "CLIENT");
//        }
//    }
//}