package com.dikiytechies.ironage.data.loot;

import com.dikiytechies.ironage.data.loot.predicates.StageCheck;
import com.dikiytechies.ironage.world.WorldAge;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.concurrent.CompletableFuture;

import static com.dikiytechies.ironage.IronAge.MOD_ID;

public class GlobalLootModifierProvider extends net.neoforged.neoforge.common.data.GlobalLootModifierProvider {

    public GlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MOD_ID);
    }

    @Override
    protected void start() {
        this.add("loot_item_replacer", new ModLootReplacer(new LootItemCondition[] {
                StageCheck.staging().setStage(WorldAge.WorldStage.DEFAULT).invert().build()
        }, Items.IRON_INGOT, Items.IRON_NUGGET));
    }
}
