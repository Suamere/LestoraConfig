package com.lestora;

import net.minecraft.network.FriendlyByteBuf;
import java.util.List;

public record SpecialValuePacket(List<String> lightLevelsMap) {

    public static void encode(SpecialValuePacket packet, FriendlyByteBuf buf) {
        buf.writeCollection(packet.lightLevelsMap, FriendlyByteBuf::writeUtf);
    }

    public static SpecialValuePacket decode(FriendlyByteBuf buf) {
        List<String> list = buf.readList(FriendlyByteBuf::readUtf);
        return new SpecialValuePacket(list);
    }
}