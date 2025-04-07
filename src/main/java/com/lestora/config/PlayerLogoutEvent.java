package com.lestora.config;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value=Dist.CLIENT)
class PlayerLogoutEvent {
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        // Debug output to verify this is running on the client.
        System.err.println("PlayerLoggedOutEvent fired on client.");
        if (Minecraft.getInstance() != null) {
            System.err.println("Minecraft instance detected; we're on the client side.");
        }
        // Reset the config state on the client.
        LightConfig.setServerAuthoritative(false);
        BiomeConfig.setServerAuthoritative(false);
    }
}