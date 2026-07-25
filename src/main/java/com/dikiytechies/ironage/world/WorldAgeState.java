package com.dikiytechies.ironage.world;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class WorldAgeState {
    private WorldStage worldStage;

    public WorldAgeState(WorldStage stage) {
        worldStage = stage;
    }

    public WorldStage get() { return worldStage; }
    public void set(WorldStage type) { worldStage = type; }

    public enum WorldStage {
        DEFAULT,
        PRE_STONE,
        PRE_IRON;

        public static final Codec<WorldStage> CODEC = Codec.STRING.xmap(WorldStage::valueOf, WorldStage::name);

        public static final StreamCodec<RegistryFriendlyByteBuf, WorldStage> STREAM_CODEC =
                ByteBufCodecs.idMapper(
                        id -> values()[id],
                        WorldAgeState.WorldStage::ordinal
                ).cast();
    }
}
