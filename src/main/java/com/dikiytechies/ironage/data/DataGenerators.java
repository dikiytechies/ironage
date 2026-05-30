package com.dikiytechies.ironage.data;

import com.dikiytechies.ironage.data.loot.GlobalLootModifierProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOut = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        gen.addProvider(event.includeServer(), new GlobalLootModifierProvider(packOut, lookupProvider));
        gen.addProvider(event.includeServer(), new ModRecipeProvider(packOut, lookupProvider));
    }
}
