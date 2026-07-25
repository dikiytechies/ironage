package com.dikiytechies.ironage.network.s2c;

import com.dikiytechies.ironage.PacketsRegister;
import com.dikiytechies.ironage.world.ClientAge;
import com.dikiytechies.ironage.world.WorldAgeState;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record SyncWorldStage(WorldAgeState.WorldStage stage) implements CustomPacketPayload {
    private static CustomPacketPayload.Type<SyncWorldStage> type;

    public static class Handler implements PacketsRegister.PacketCodecHandler<SyncWorldStage> {
        public Handler(ResourceLocation packetId) {
            type = new CustomPacketPayload.Type<>(packetId);
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, SyncWorldStage> reader() {
            return STREAM_CODEC;
        }

        @Override
        public Type<SyncWorldStage> type() {
            return type;
        }

        @Override
        public void handle(SyncWorldStage payload, IPayloadContext context) {
            ClientAge age = ClientAge.getInstance();
            age.setStage(payload.stage());
        }

        public static final StreamCodec<RegistryFriendlyByteBuf, SyncWorldStage> STREAM_CODEC = StreamCodec.composite(
                WorldAgeState.WorldStage.STREAM_CODEC,
                SyncWorldStage::stage,
                SyncWorldStage::new
        );
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return type;
    }
}
