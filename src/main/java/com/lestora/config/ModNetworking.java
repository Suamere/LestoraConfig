package com.lestora.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.SimpleChannel;
import net.minecraftforge.network.ChannelBuilder;

class ModNetworking {
    static int channelID = 0;

    static final SimpleChannel CHANNEL = ChannelBuilder
            .named(ResourceLocation.fromNamespaceAndPath("lestora", "main"))
            .networkProtocolVersion(1)
            .clientAcceptedVersions((remoteVersion, localVersion) -> true)
            .serverAcceptedVersions((remoteVersion, localVersion) -> true)
            .simpleChannel();

    static void registerLightConfig() {
        CHANNEL.messageBuilder(LightValuePacket.class, channelID++)
                .encoder(LightValuePacket::encode)
                .decoder(LightValuePacket::decode)
                .consumer((packet, context) -> {
                    context.enqueueWork(() -> {
                        // This is what runs on the client who joins, how they handle the packet
                        LightConfig.setLightLevelsMap(packet.lightLevelsMap(), "SERVER");
                    });
                    context.setPacketHandled(true);
                })
                .add();
    }

    static void registerBiomeConfig() {
        CHANNEL.messageBuilder(BiomeValuePacket.class, channelID++)
                .encoder(BiomeValuePacket::encode)
                .decoder(BiomeValuePacket::decode)
                .consumer((packet, context) -> {
                    context.enqueueWork(() -> {
                        context.enqueueWork(() -> {
                            // This is what runs on the client who joins, how they handle the packet
                            BiomeConfig.setBiomeTempsMap(packet.biomeTempsMap(), "SERVER");
                        });
                    });
                    context.setPacketHandled(true);
                })
                .add();
    }
}