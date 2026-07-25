package com.dikiytechies.ironage.world;

public interface IWorldAge {
    public WorldAgeState.WorldStage getStage();

    public void setStage(WorldAgeState.WorldStage stage);
}
