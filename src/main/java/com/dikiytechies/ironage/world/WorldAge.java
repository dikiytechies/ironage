package com.dikiytechies.ironage.world;

import com.dikiytechies.ironage.network.s2c.SyncWorldAge;
import com.dikiytechies.ironage.util.ModUtil;
import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

import java.util.List;

public class WorldAge extends SavedData {
    private WorldStage worldStage;

    public WorldAge() {
        this.worldStage = WorldStage.PRE_STONE;
    }

    public WorldStage get() {
        return worldStage;
    }

    public void set(WorldStage stage) {
        worldStage = stage;
        PacketDistributor.sendToAllPlayers(new SyncWorldAge(stage));
        setDirty();
    }

    public boolean proceedTo(WorldStage stage) {
        if ((worldStage.ordinal() + 1) % WorldStage.values().length == stage.ordinal()) {
            worldStage = stage;
            PacketDistributor.sendToAllPlayers(new SyncWorldAge(stage));
            setDirty();
            return true;
        }
        return false;
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        compoundTag.putString("WorldStage", worldStage.name());
        return compoundTag;
    }

    public static WorldAge load(CompoundTag compoundTag, HolderLookup.Provider provider) {
        WorldAge data = new WorldAge();
        data.worldStage = WorldStage.valueOf(compoundTag.getString("WorldStage"));

        return data;
    }

    public static WorldAge get(MinecraftServer server) {
        DimensionDataStorage storage = server.overworld().getDataStorage();

        return storage.computeIfAbsent(new Factory<>(WorldAge::new, WorldAge::load), "world_age");
    }


    public enum WorldStage {
        DEFAULT(List.of(), List.of()),
        PRE_STONE(ModUtil.STONE_TIERS, ModUtil.STONE_MATERIALS),
        PRE_IRON(ModUtil.IRON_TIERS, ModUtil.IRON_MATERIALS),
        PRE_LATE(ModUtil.LATE_TIERS, ModUtil.LATE_MATERIALS);

        public final List<Tier> toolTier;
        public final List<ArmorMaterial> armorMaterial;

        WorldStage(List<Tier> toolTiers, List<ArmorMaterial> armorMaterials) {
            this.toolTier = toolTiers;
            this.armorMaterial = armorMaterials;
        }

        public static final Codec<WorldStage> CODEC = Codec.STRING.xmap(WorldStage::valueOf, WorldStage::name);

        public static final StreamCodec<FriendlyByteBuf, WorldStage> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(WorldStage.class);
    }
}
