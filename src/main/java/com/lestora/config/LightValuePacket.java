package com.lestora.config;

import net.minecraft.network.FriendlyByteBuf;
import java.util.List;

record LightValuePacket(List<String> lightLevelsMap) {

    static void encode(LightValuePacket packet, FriendlyByteBuf buf) {
        buf.writeCollection(packet.lightLevelsMap, FriendlyByteBuf::writeUtf);
    }

    static LightValuePacket decode(FriendlyByteBuf buf) {
        List<String> list = buf.readList(FriendlyByteBuf::readUtf);
        return new LightValuePacket(list);
    }
}