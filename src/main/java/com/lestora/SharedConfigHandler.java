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
class SharedConfigHandler {
    public static final ForgeConfigSpec LIGHTING_CONFIG;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> LIGHT_LEVELS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("light_levels");
        LIGHT_LEVELS = builder.comment("List of item=light level pairs")
                .defineList("items",
                        Arrays.asList(
                                // Special Blocks
                                "minecraft:light_block(15)=15",     // Light Block
                                "minecraft:light_block(14)=14",     // Light Block
                                "minecraft:light_block(13)=13",     // Light Block
                                "minecraft:light_block(12)=12",     // Light Block
                                "minecraft:light_block(11)=11",     // Light Block
                                "minecraft:light_block(10)=10",     // Light Block
                                "minecraft:light_block(9)=9",       // Light Block
                                "minecraft:light_block(7)=7",       // Light Block
                                "minecraft:light_block(6)=6",       // Light Block
                                "minecraft:light_block(5)=5",       // Light Block
                                "minecraft:light_block(4)=4",       // Light Block
                                "minecraft:light_block(3)=3",       // Light Block
                                "minecraft:light_block(2)=2",       // Light Block
                                "minecraft:light_block(1)=1",       // Light Block
                                "minecraft:sea_pickles(4)=15",      // Waterlogged Sea Pickles (4)
                                "minecraft:sea_pickles(3)=12",      // Waterlogged Sea Pickles (3)
                                "minecraft:sea_pickles(2)=9",       // Waterlogged Sea Pickles (2)
                                "minecraft:sea_pickles(1)=6",       // Waterlogged Sea Pickles (1)
                                "minecraft:respawn_anchor(4)=15",   // Respawn Anchor (4)
                                "minecraft:respawn_anchor(3)=11",   // Respawn Anchor (3)
                                "minecraft:respawn_anchor(2)=7",    // Respawn Anchor (2)
                                "minecraft:respawn_anchor(1)=3",    // Respawn Anchor (1)
                                "minecraft:candle(4)=12",           // Lit Candles (4)
                                "minecraft:candle(3)=9",            // Lit Candles (3)
                                "minecraft:candle(2)=6",            // Lit Candles (2)
                                "minecraft:candle(1)=3",            // Lit Candles (1)
                                "minecraft:campfire=15",       // Lit Campfire
                                "minecraft:redstone_lamp=15",  // Lit Redstone Lamp
                                "minecraft:soul_campfire=10",  // Lit Soul Campfire
                                "minecraft:blast_furnace=13",  // Lit Blast Furnace
                                "minecraft:furnace=13",        // Lit Furnace
                                "minecraft:smoker=13",         // Lit Smoker
                                "minecraft:deepslate_redstone_ore=9",   // Lit Deepslate Redstone Ore
                                "minecraft:redstone_ore=9",    // Lit Redstone Ore
                                "minecraft:redstone_torch=7",  // Lit Redstone Torch

                                // Light level 15
                                "minecraft:beacon=15",
                                "minecraft:conduit=15",
                                "minecraft:end_gateway=15",
                                "minecraft:end_portal=15",
                                "minecraft:fire=15",
                                "minecraft:froglight=15",
                                "minecraft:glowstone=15",
                                "minecraft:jack_o_lantern=15",
                                "minecraft:lantern=15",
                                "minecraft:lava=15",
                                "minecraft:lava_cauldron=15",
                                "minecraft:sea_lantern=15",
                                "minecraft:shroomlight=15",

                                // Light level 14
                                "minecraft:cave_vines_with_berries=14",
                                "minecraft:end_rod=14",
                                "minecraft:torch=14",

                                // Light level 13

                                // Light level 12

                                // Light level 11
                                "minecraft:nether_portal=11",

                                // Light level 10
                                "minecraft:crying_obsidian=10",
                                "minecraft:soul_fire=10",
                                "minecraft:soul_lantern=10",
                                "minecraft:soul_torch=10",

                                // Light level 9

                                // Light level 7
                                "minecraft:enchanting_table=7",
                                "minecraft:ender_chest=7",
                                "minecraft:glow_lichen=7",

                                // Light level 6
                                "minecraft:sculk_catalyst=6",

                                // Light level 5
                                "minecraft:amethyst_cluster=5",

                                // Light level 4
                                "minecraft:large_amethyst_bud=4",

                                // Light level 3
                                "minecraft:magma=3",                // or "magma_block" depending on your version

                                // Light level 2
                                "minecraft:medium_amethyst_bud=2",

                                // Light level 1
                                "minecraft:brewing_stand=1",
                                "minecraft:brown_mushroom=1",
                                "minecraft:dragon_egg=1",
                                "minecraft:end_portal_frame=1",
                                "minecraft:sculk_sensor=1",
                                "minecraft:small_amethyst_bud=1"
                        ),
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
                LestoraConfig.setLightLevelsMap(getLightLevelConfig(), "SERVER");
            } else {
                LestoraConfig.setLightLevelsMap(getLightLevelConfig(), "CLIENT");
            }
        }
    }

    @SubscribeEvent
    static void onReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == LIGHTING_CONFIG) {
            if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
                LestoraConfig.setLightLevelsMap(getLightLevelConfig(), "SERVER");
            } else {
                LestoraConfig.setLightLevelsMap(getLightLevelConfig(), "CLIENT");
            }
        }
    }
}