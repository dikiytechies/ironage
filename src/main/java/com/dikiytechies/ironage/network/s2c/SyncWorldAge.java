package com.dikiytechies.ironage.network.s2c;

import com.dikiytechies.ironage.PacketsRegister;
import com.dikiytechies.ironage.world.ClientWorldAge;
import com.dikiytechies.ironage.world.WorldAge;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record SyncWorldAge(WorldAge.WorldStage stage) implements CustomPacketPayload {
    private static CustomPacketPayload.Type<SyncWorldAge> type;

    public static class Handler implements PacketsRegister.PacketCodecHandler<SyncWorldAge> {
        public Handler(ResourceLocation packetId) {
            type = new CustomPacketPayload.Type<>(packetId);
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, SyncWorldAge> reader() {
            return WorldAge.WorldStage.STREAM_CODEC.map(SyncWorldAge::new, SyncWorldAge::stage);
        }

        @Override
        public Type<SyncWorldAge> type() {
            return type;
        }

        @Override
        public void handle(SyncWorldAge payload, IPayloadContext context) {
            ClientWorldAge age = ClientWorldAge.getInstance();
            age.setStage(payload.stage());
        }
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return type;
    }
}
