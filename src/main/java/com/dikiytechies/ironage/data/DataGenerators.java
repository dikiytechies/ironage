package com.dikiytechies.ironage.data;

import com.dikiytechies.ironage.IronAge;
import com.dikiytechies.ironage.data.loot.GlobalLootModifierProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOut = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(event.includeServer(), new GlobalLootModifierProvider(packOut, lookupProvider));
        gen.addProvider(event.includeServer(), new ModRecipeProvider(packOut, lookupProvider));

        BlockTagsProvider blockTagsProvider = new BlockTagsProvider(packOut, lookupProvider, IronAge.MOD_ID, existingFileHelper) {
            @Override
            protected void addTags(HolderLookup.@NotNull Provider provider) {

            }
        };
        gen.addProvider(event.includeServer(), blockTagsProvider);
        gen.addProvider(event.includeServer(), new ModItemTagsProvider(packOut, lookupProvider, blockTagsProvider.contentsGetter(), IronAge.MOD_ID, existingFileHelper));
    }
}
