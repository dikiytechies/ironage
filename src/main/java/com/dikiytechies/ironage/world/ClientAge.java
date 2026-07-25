package com.dikiytechies.ironage.world;

public class ClientAge implements IWorldAge {
    private static ClientAge clientAge;

    private final WorldAgeState worldState;

    private ClientAge() {
        worldState = new WorldAgeState(WorldAgeState.WorldStage.DEFAULT);
    }

    public static ClientAge getInstance() {
        if (clientAge != null)
            return clientAge;
        return new ClientAge();
    }

    @Override
    public WorldAgeState.WorldStage getStage() {
        return worldState.get();
    }

    @Override
    public void setStage(WorldAgeState.WorldStage stage) {
        worldState.set(stage);
    }
}
