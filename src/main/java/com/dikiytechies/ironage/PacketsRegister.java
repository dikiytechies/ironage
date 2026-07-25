package com.dikiytechies.ironage;

import com.dikiytechies.ironage.network.s2c.SyncWorldAge;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PacketsRegister {
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registerPacket(registrar, PayloadRegistrar::playToClient, new SyncWorldAge.Handler(IronAge.resLoc("can_harvest")));
    }

    public static interface PacketHandler<T extends CustomPacketPayload> {
        CustomPacketPayload.Type<T> type();
        void handle(T payload, IPayloadContext context);
    }

    public static interface PacketCodecHandler<T extends CustomPacketPayload> extends PacketHandler<T> {
        StreamCodec<? super RegistryFriendlyByteBuf, T> reader();
    }

    public static <T extends CustomPacketPayload> void registerPacket(PayloadRegistrar registrar, PacketType packetType, PacketCodecHandler<T> handler) {
        packetType.register(registrar, handler.type(), handler.reader(), handler::handle);
    }

    @FunctionalInterface
    public static interface PacketType {
        <T extends CustomPacketPayload> void register(PayloadRegistrar registrar,
                                                      CustomPacketPayload.Type<T> type,
                                                      StreamCodec<? super RegistryFriendlyByteBuf, T> reader,
                                                      IPayloadHandler<T> handler);
    }
}
