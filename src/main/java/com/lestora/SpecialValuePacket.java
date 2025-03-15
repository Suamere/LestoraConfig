package com.lestora;

import net.minecraft.network.FriendlyByteBuf;
import java.util.List;

record SpecialValuePacket(List<String> lightLevelsMap) {

    static void encode(SpecialValuePacket packet, FriendlyByteBuf buf) {
        buf.writeCollection(packet.lightLevelsMap, FriendlyByteBuf::writeUtf);
    }

    static SpecialValuePacket decode(FriendlyByteBuf buf) {
        List<String> list = buf.readList(FriendlyByteBuf::readUtf);
        return new SpecialValuePacket(list);
    }
}