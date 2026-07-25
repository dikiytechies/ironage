package com.dikiytechies.ironage.world;

public class ClientWorldAge {
    private static ClientWorldAge instance;

    WorldAge.WorldStage stage;

    private ClientWorldAge() {
        this.stage = WorldAge.WorldStage.DEFAULT;
    }

    public static ClientWorldAge getInstance() {
        if (instance != null)
            return instance;
        instance = new ClientWorldAge();
        return instance;
    }

    public void setStage(WorldAge.WorldStage stage) {
        this.stage = stage;
    }

    public WorldAge.WorldStage getStage() {
        return stage;
    }
}
