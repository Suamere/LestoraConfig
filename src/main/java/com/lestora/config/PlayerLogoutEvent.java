package com.lestora.config;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value=Dist.CLIENT)
class PlayerLogoutEvent {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Level lastLevel = null;
    private static boolean onMainMenu = true;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Level currentLevel = Minecraft.getInstance().level;

        if (currentLevel != lastLevel) {
            if (currentLevel == null && lastLevel != null) {
                onMainMenu = true;
                LOGGER.info("Player returned to main menu");
                LightConfig.setServerAuthoritative(false);
                BiomeConfig.setServerAuthoritative(false);
            } else if (currentLevel != null && onMainMenu) {
                onMainMenu = false;
                LOGGER.info("Player logged in to some game");
            }
            lastLevel = currentLevel;
        }
    }
}