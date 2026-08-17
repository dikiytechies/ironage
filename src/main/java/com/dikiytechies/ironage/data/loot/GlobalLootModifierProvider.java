package com.dikiytechies.ironage.data.loot;

import com.dikiytechies.ironage.data.loot.predicates.StageCheck;
import com.dikiytechies.ironage.init.ModItems;
import com.dikiytechies.ironage.world.WorldAge;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

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
        this.add("village_stone_upgrade", new ModLootAdder(new LootItemCondition[] {
                AnyOfCondition.anyOf(
                LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("chests/village/village_toolsmith")),
                LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("chests/village/village_weaponsmith"))).build(),
                LootItemRandomChanceCondition.randomChance(0.45f).build()
        }, ModItems.STONE_UPGRADE_SMITHING_TEMPLATE.asItem()));
        this.add("ruined_portal_chainmail_upgrade", new ModLootAdder(new LootItemCondition[] {
                LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("chests/ruined_portal")).build(),
                LootItemRandomChanceCondition.randomChance(0.35f).build()
        }, ModItems.CHAINMAIL_UPGRADE_SMITHING_TEMPLATE.asItem()));
        this.add("nether_bridge_iron_upgrade", new ModLootAdder(new LootItemCondition[] {
                LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("chests/nether_bridge")).build(),
                LootItemRandomChanceCondition.randomChance(0.25f).build()
        }, ModItems.IRON_UPGRADE_SMITHING_TEMPLATE.asItem()));
        this.add("end_city_treasure_diamond_upgrade", new ModLootAdder(new LootItemCondition[] {
                LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("chests/end_city_treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.35f).build()
        }, ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE.asItem()));
    }
}
