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
        LIGHT_LEVELS = builder.comment("List of item=light level pairs")
                .defineList("items",
                        Arrays.asList(
                                // Special Blocks
                                "minecraft:light(15)=15",     // Light Block
                                "minecraft:light(14)=14",     // Light Block
                                "minecraft:light(13)=13",     // Light Block
                                "minecraft:light(12)=12",     // Light Block
                                "minecraft:light(11)=11",     // Light Block
                                "minecraft:light(10)=10",     // Light Block
                                "minecraft:light(9)=9",       // Light Block
                                "minecraft:light(7)=7",       // Light Block
                                "minecraft:light(6)=6",       // Light Block
                                "minecraft:light(5)=5",       // Light Block
                                "minecraft:light(4)=4",       // Light Block
                                "minecraft:light(3)=3",       // Light Block
                                "minecraft:light(2)=2",       // Light Block
                                "minecraft:light(1)=1",       // Light Block
                                "minecraft:sea_pickle(4)=15",      // Sea Pickles (4), ensure Lit
                                "minecraft:sea_pickle(3)=12",      // Sea Pickles (3), ensure Lit
                                "minecraft:sea_pickle(2)=9",       // Sea Pickles (2), ensure Lit
                                "minecraft:sea_pickle(1)=6",       // Sea Pickles (1), ensure Lit
                                "minecraft:respawn_anchor(4)=15",   // Respawn Anchor (4)
                                "minecraft:respawn_anchor(3)=11",   // Respawn Anchor (3)
                                "minecraft:respawn_anchor(2)=7",    // Respawn Anchor (2)
                                "minecraft:respawn_anchor(1)=3",    // Respawn Anchor (1)
                                "minecraft:candle(4)=12",           // Candles (4), ensure Lit
                                "minecraft:candle(3)=9",            // Candles (3), ensure Lit
                                "minecraft:candle(2)=6",            // Candles (2), ensure Lit
                                "minecraft:candle(1)=3",            // Candles (1), ensure Lit
                                "minecraft:campfire=15",       // Campfire, ensure Lit
                                "minecraft:redstone_lamp=15",  // Redstone Lamp, ensure Lit
                                "minecraft:soul_campfire=10",  // Soul Campfire, ensure Lit
                                "minecraft:blast_furnace=13",  // Blast Furnace, ensure Lit
                                "minecraft:furnace=13",        // Furnace, ensure Lit
                                "minecraft:smoker=13",         // Smoker, ensure Lit
                                "minecraft:deepslate_redstone_ore=9",   // Deepslate Redstone Ore, ensure Lit
                                "minecraft:redstone_ore=9",    // Redstone Ore, ensure Lit
                                "minecraft:redstone_torch=7",  // Redstone Torch, ensure Lit

                                // Light level 15
                                "minecraft:beacon=15",
                                "minecraft:conduit=15",
                                "minecraft:end_gateway=15",
                                "minecraft:end_portal=15",
                                "minecraft:fire=15",
                                "minecraft:ochre_froglight=15",
                                "minecraft:pearlescent_froglight=15",
                                "minecraft:verdant_froglight=15",
                                "minecraft:glowstone=15",
                                "minecraft:jack_o_lantern=15",
                                "minecraft:lantern=15",
                                "minecraft:lava=15",
                                "minecraft:lava_cauldron=15",
                                "minecraft:sea_lantern=15",
                                "minecraft:shroomlight=15",

                                // Light level 14
                                "minecraft:cave_vines=14", // Ensure it has metadata of "hasBerries"
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
                                "minecraft:magma_block=3",                // or "magma_block" depending on your version

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