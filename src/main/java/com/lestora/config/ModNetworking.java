package com.lestora.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.SimpleChannel;
import net.minecraftforge.network.ChannelBuilder;

class ModNetworking {
    static final SimpleChannel CHANNEL = ChannelBuilder
            .named(ResourceLocation.fromNamespaceAndPath("lestora", "main"))
            .networkProtocolVersion(1)
            .clientAcceptedVersions((remoteVersion, localVersion) -> true)
            .serverAcceptedVersions((remoteVersion, localVersion) -> true)
            .simpleChannel();

    static void register() {
        int id = 0;
        CHANNEL.messageBuilder(SpecialValuePacket.class, id++)
                .encoder(SpecialValuePacket::encode)
                .decoder(SpecialValuePacket::decode)
                .consumer((packet, context) -> {
                    context.enqueueWork(() -> {
                        LightConfig.setLightLevelsMap(packet.lightLevelsMap(), "SERVER");
                    });
                    context.setPacketHandled(true);
                })
                .add();
    }
}