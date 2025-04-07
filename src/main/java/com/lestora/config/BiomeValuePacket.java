package com.lestora.config;

import net.minecraft.network.FriendlyByteBuf;

import java.util.List;

record BiomeValuePacket(List<String> biomeTempsMap) {

    static void encode(BiomeValuePacket packet, FriendlyByteBuf buf) {
        buf.writeCollection(packet.biomeTempsMap, FriendlyByteBuf::writeUtf);
    }

    static BiomeValuePacket decode(FriendlyByteBuf buf) {
        List<String> list = buf.readList(FriendlyByteBuf::readUtf);
        return new BiomeValuePacket(list);
    }
}