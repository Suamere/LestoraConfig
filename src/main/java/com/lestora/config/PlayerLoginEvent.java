package com.lestora.config;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
class PlayerLoginEvent {
    @SubscribeEvent
    static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer serverPlayer) {
            ModNetworking.CHANNEL.send(
                    new LightValuePacket(LightConfigHandler.getLightLevelConfig()),
                    PacketDistributor.PLAYER.with(serverPlayer)
            );
            ModNetworking.CHANNEL.send(
                    new BiomeValuePacket(BiomeConfigHandler.getBiomeTempConfig()),
                    PacketDistributor.PLAYER.with(serverPlayer)
            );
        }
    }
}