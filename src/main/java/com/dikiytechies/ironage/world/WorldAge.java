package com.dikiytechies.ironage.world;

import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

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
        setDirty();
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
        DEFAULT,
        PRE_STONE,
        PRE_IRON;

        public static final Codec<WorldStage> CODEC = Codec.STRING.xmap(WorldStage::valueOf, WorldStage::name);
    }
}
