package com.lestora.config;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerLoginEvent {
    private static ServerPlayer ownerPlayer = null;

    @SubscribeEvent
    static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer serverPlayer) {
            var server = serverPlayer.getServer();
            // For an integrated server (single-player or LAN), record the owner if not already set.
            if (!server.isDedicatedServer()) {
                if (ownerPlayer == null) {
                    ownerPlayer = serverPlayer;
                }
            }
            // If there is an owner and the joining player is the owner, don't send the config packet.
            if (ownerPlayer != null && ownerPlayer.getUUID().equals(serverPlayer.getUUID())) {
                return;
            }

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