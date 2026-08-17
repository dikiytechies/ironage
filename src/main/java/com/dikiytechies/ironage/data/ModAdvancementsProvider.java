package com.dikiytechies.ironage.data;

import com.dikiytechies.ironage.IronAge;
import com.dikiytechies.ironage.init.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.dikiytechies.ironage.IronAge.MOD_ID;

public class ModAdvancementsProvider extends AdvancementProvider {
    public ModAdvancementsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new ModAdvancementsGenerator()));
    }

    public static class ModAdvancementsGenerator implements AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(new ItemStack(Items.WOODEN_SWORD),
                            withTitle("root"),
                            withDesc("root"),
                            ResourceLocation.withDefaultNamespace("textures/block/stone.png"),
                            AdvancementType.TASK,
                            false,
                            false,
                            false)
                    .addCriterion("get_cobblestone", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COBBLESTONE))
                    .save(saver, IronAge.resLoc("root"), existingFileHelper);

            AdvancementHolder stoneUpgrade = Advancement.Builder.advancement()
                    .parent(root)
                    .display(new ItemStack(ModItems.STONE_UPGRADE_SMITHING_TEMPLATE.get()),
                            withTitle("stone_upgrade"),
                            withDesc("stone_upgrade"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false)
                    .rewards(AdvancementRewards.Builder.experience(50))
                    .addCriterion("get_stone_upgrade", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.STONE_UPGRADE_SMITHING_TEMPLATE))
                    .save(saver, IronAge.resLoc("stone_upgrade"), existingFileHelper);

            AdvancementHolder chainmailUpgrade = Advancement.Builder.advancement()
                    .parent(stoneUpgrade)
                    .display(new ItemStack(ModItems.CHAINMAIL_UPGRADE_SMITHING_TEMPLATE.get()),
                            withTitle("chain_upgrade"),
                            withDesc("chain_upgrade"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false)
                    .rewards(AdvancementRewards.Builder.experience(50))
                    .addCriterion("get_chain_upgrade", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CHAINMAIL_UPGRADE_SMITHING_TEMPLATE))
                    .save(saver, IronAge.resLoc("chain_upgrade"), existingFileHelper);

            AdvancementHolder ironUpgrade = Advancement.Builder.advancement()
                    .parent(chainmailUpgrade)
                    .display(new ItemStack(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE.get()),
                            withTitle("iron_upgrade"),
                            withDesc("iron_upgrade"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false)
                    .rewards(AdvancementRewards.Builder.experience(70))
                    .addCriterion("get_iron_upgrade", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.IRON_UPGRADE_SMITHING_TEMPLATE))
                    .save(saver, IronAge.resLoc("iron_upgrade"), existingFileHelper);

            Advancement.Builder.advancement()
                    .parent(ironUpgrade)
                    .display(new ItemStack(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE.get()),
                            withTitle("diamond_upgrade"),
                            withDesc("diamond_upgrade"),
                            null,
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            true)
                    .rewards(AdvancementRewards.Builder.experience(150))
                    .addCriterion("get_diamond_upgrade", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
                    .save(saver, IronAge.resLoc("diamond_upgrade"), existingFileHelper);
        }

        private static MutableComponent translatable(String key) {
            return Component.translatable(String.format("advancements.%s.%s", MOD_ID, key));
        }

        private static MutableComponent withTitle(String key) {
            return translatable(key + ".title");
        }

        private static MutableComponent withDesc(String key) {
            return translatable(key + ".description");
        }
    }
}
