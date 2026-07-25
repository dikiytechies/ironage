package com.dikiytechies.ironage.world;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class WorldAge extends SavedData implements IWorldAge {
    private final WorldAgeState worldState;

    public WorldAge() {
        worldState = new WorldAgeState(WorldAgeState.WorldStage.PRE_STONE);
    }

    public WorldAgeState.WorldStage getStage() {
        return worldState.get();
    }

    public void setStage(WorldAgeState.WorldStage stage) {
        worldState.set(stage);
        setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        compoundTag.putString("WorldStage", getStage().name());
        return compoundTag;
    }

    public static WorldAge load(CompoundTag compoundTag, HolderLookup.Provider provider) {
        WorldAge data = new WorldAge();
        data.worldState.set(WorldAgeState.WorldStage.valueOf(compoundTag.getString("WorldStage")));

        return data;
    }

    public static WorldAge getStage(MinecraftServer server) {
        DimensionDataStorage storage = server.overworld().getDataStorage();

        return storage.computeIfAbsent(new Factory<>(WorldAge::new, WorldAge::load), "world_age");
    }
}
