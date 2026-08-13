package com.dikiytechies.ironage.data;

import com.dikiytechies.ironage.IronAge;
import com.dikiytechies.ironage.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput out, CompletableFuture<HolderLookup.Provider> provider) {
        super(out, provider);
    }

    @Override
    protected void buildRecipes(RecipeOutput out) {
        buildCrafting(out);
        buildSmithing(out);
    }

    private static void buildCrafting(RecipeOutput out) {
        copyTemplate(out, ModItems.STONE_UPGRADE_SMITHING_TEMPLATE.get(), Items.SMOOTH_STONE, Items.AMETHYST_SHARD);
        copyTemplate(out, ModItems.CHAINMAIL_UPGRADE_SMITHING_TEMPLATE.get(), Items.NETHERRACK, Items.GOLD_INGOT);
        copyTemplate(out, ModItems.IRON_UPGRADE_SMITHING_TEMPLATE.get(), Items.COPPER_BLOCK, Items.LAPIS_LAZULI);
        copyTemplate(out, ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE.get(), Items.PRISMARINE, Items.PRISMARINE_CRYSTALS);
    }

    private static void copyTemplate(RecipeOutput out, Item template, Item base, Item addition) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, template, 2)
                .define('#', template)
                .define('+', addition)
                .define('@', base)
                .pattern("+#+")
                .pattern("+@+")
                .pattern("+++")
                .unlockedBy(getHasName(template), has(template)).save(out);
    }

    private static void buildSmithing(RecipeOutput out) {
        addSmithing(out, ModItems.CHAINMAIL_UPGRADE_SMITHING_TEMPLATE.get(), Items.LEATHER_HELMET, Items.IRON_NUGGET, Items.CHAINMAIL_HELMET, RecipeCategory.COMBAT);
        addSmithing(out, ModItems.CHAINMAIL_UPGRADE_SMITHING_TEMPLATE.get(), Items.LEATHER_CHESTPLATE, Items.IRON_NUGGET, Items.CHAINMAIL_CHESTPLATE, RecipeCategory.COMBAT);
        addSmithing(out, ModItems.CHAINMAIL_UPGRADE_SMITHING_TEMPLATE.get(), Items.LEATHER_LEGGINGS, Items.IRON_NUGGET, Items.CHAINMAIL_LEGGINGS, RecipeCategory.COMBAT);
        addSmithing(out, ModItems.CHAINMAIL_UPGRADE_SMITHING_TEMPLATE.get(), Items.LEATHER_BOOTS, Items.IRON_NUGGET, Items.CHAINMAIL_BOOTS, RecipeCategory.COMBAT);

        addSmithing(out, ModItems.STONE_UPGRADE_SMITHING_TEMPLATE.get(), Items.WOODEN_AXE, ItemTags.STONE_TOOL_MATERIALS, Items.STONE_AXE, RecipeCategory.TOOLS, "stone_tool_materials");
        addSmithing(out, ModItems.STONE_UPGRADE_SMITHING_TEMPLATE.get(), Items.WOODEN_HOE, ItemTags.STONE_TOOL_MATERIALS, Items.STONE_HOE, RecipeCategory.TOOLS,"stone_tool_materials");
        addSmithing(out, ModItems.STONE_UPGRADE_SMITHING_TEMPLATE.get(), Items.WOODEN_PICKAXE, ItemTags.STONE_TOOL_MATERIALS, Items.STONE_PICKAXE, RecipeCategory.TOOLS,"stone_tool_materials");
        addSmithing(out, ModItems.STONE_UPGRADE_SMITHING_TEMPLATE.get(), Items.WOODEN_SHOVEL, ItemTags.STONE_TOOL_MATERIALS, Items.STONE_SHOVEL, RecipeCategory.TOOLS,"stone_tool_materials");
        addSmithing(out, ModItems.STONE_UPGRADE_SMITHING_TEMPLATE.get(), Items.WOODEN_SWORD, ItemTags.STONE_TOOL_MATERIALS, Items.STONE_SWORD, RecipeCategory.COMBAT,"stone_tool_materials");

        addSmithing(out, ModItems.IRON_UPGRADE_SMITHING_TEMPLATE.get(), ModItemTagsProvider.getUpgradeTag(ModItemTagsProvider.Armor.class, ModItemTagsProvider.Armor.HELMET), Items.IRON_INGOT, Items.IRON_HELMET, RecipeCategory.COMBAT);
        addSmithing(out, ModItems.IRON_UPGRADE_SMITHING_TEMPLATE.get(), ModItemTagsProvider.getUpgradeTag(ModItemTagsProvider.Armor.class, ModItemTagsProvider.Armor.CHESTPLATE), Items.IRON_INGOT, Items.IRON_CHESTPLATE, RecipeCategory.COMBAT);
        addSmithing(out, ModItems.IRON_UPGRADE_SMITHING_TEMPLATE.get(), ModItemTagsProvider.getUpgradeTag(ModItemTagsProvider.Armor.class, ModItemTagsProvider.Armor.LEGGINGS), Items.IRON_INGOT, Items.IRON_LEGGINGS, RecipeCategory.COMBAT);
        addSmithing(out, ModItems.IRON_UPGRADE_SMITHING_TEMPLATE.get(), ModItemTagsProvider.getUpgradeTag(ModItemTagsProvider.Armor.class, ModItemTagsProvider.Armor.BOOTS), Items.IRON_INGOT, Items.IRON_BOOTS, RecipeCategory.COMBAT);

        addSmithing(out, ModItems.IRON_UPGRADE_SMITHING_TEMPLATE.get(), ModItemTagsProvider.getUpgradeTag(ModItemTagsProvider.Tool.class, ModItemTagsProvider.Tool.AXE), Items.IRON_INGOT, Items.IRON_AXE, RecipeCategory.TOOLS);
        addSmithing(out, ModItems.IRON_UPGRADE_SMITHING_TEMPLATE.get(), ModItemTagsProvider.getUpgradeTag(ModItemTagsProvider.Tool.class, ModItemTagsProvider.Tool.HOE), Items.IRON_INGOT, Items.IRON_HOE, RecipeCategory.TOOLS);
        addSmithing(out, ModItems.IRON_UPGRADE_SMITHING_TEMPLATE.get(), ModItemTagsProvider.getUpgradeTag(ModItemTagsProvider.Tool.class, ModItemTagsProvider.Tool.PICKAXE), Items.IRON_INGOT, Items.IRON_PICKAXE, RecipeCategory.TOOLS);
        addSmithing(out, ModItems.IRON_UPGRADE_SMITHING_TEMPLATE.get(), ModItemTagsProvider.getUpgradeTag(ModItemTagsProvider.Tool.class, ModItemTagsProvider.Tool.SHOVEL), Items.IRON_INGOT, Items.IRON_SHOVEL, RecipeCategory.TOOLS);
        addSmithing(out, ModItems.IRON_UPGRADE_SMITHING_TEMPLATE.get(), ModItemTagsProvider.getUpgradeTag(ModItemTagsProvider.Tool.class, ModItemTagsProvider.Tool.SWORD), Items.IRON_INGOT, Items.IRON_SWORD, RecipeCategory.COMBAT);

        addSmithing(out, ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_HELMET, Items.DIAMOND, Items.DIAMOND_HELMET, RecipeCategory.COMBAT);
        addSmithing(out, ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_CHESTPLATE, Items.DIAMOND, Items.DIAMOND_CHESTPLATE, RecipeCategory.COMBAT);
        addSmithing(out, ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_LEGGINGS, Items.DIAMOND, Items.DIAMOND_LEGGINGS, RecipeCategory.COMBAT);
        addSmithing(out, ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_BOOTS, Items.DIAMOND, Items.DIAMOND_BOOTS, RecipeCategory.COMBAT);

        addSmithing(out, ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_AXE, Items.DIAMOND, Items.DIAMOND_AXE, RecipeCategory.TOOLS);
        addSmithing(out, ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_HOE, Items.DIAMOND, Items.DIAMOND_HOE, RecipeCategory.TOOLS);
        addSmithing(out, ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_PICKAXE, Items.DIAMOND, Items.DIAMOND_PICKAXE, RecipeCategory.TOOLS);
        addSmithing(out, ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_SHOVEL, Items.DIAMOND, Items.DIAMOND_SHOVEL, RecipeCategory.TOOLS);
        addSmithing(out, ModItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_SWORD, Items.DIAMOND, Items.DIAMOND_SWORD, RecipeCategory.COMBAT);
    }

    private static void addSmithing(RecipeOutput out, ItemLike template, ItemLike base, ItemLike addition, Item result, RecipeCategory category) {
        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(template),
                Ingredient.of(base),
                Ingredient.of(addition),
                category,
                result)
                .unlocks(getHasName(addition), has(addition)).save(out, IronAge.resLoc(getItemName(result) + "_smithing"));
    }

    private static void addSmithingSpec(RecipeOutput out, ItemLike template, ItemLike base, ItemLike addition, Item result, RecipeCategory category) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(template),
                        Ingredient.of(base),
                        Ingredient.of(addition),
                        category,
                        result)
                .unlocks(getHasName(addition), has(addition)).save(out, IronAge.resLoc(getItemName(result) + "_smithing_from_" + getItemName(base)));
    }

    private static void addSmithing(RecipeOutput out, ItemLike template, TagKey<Item> base, ItemLike addition, Item result, RecipeCategory category) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(template),
                        Ingredient.of(base),
                        Ingredient.of(addition),
                        category,
                        result)
                .unlocks(getHasName(addition), has(addition)).save(out, IronAge.resLoc(getItemName(result)));
    }

    private static void addSmithing(RecipeOutput out, ItemLike template, ItemLike base, TagKey<Item> addition, Item result, RecipeCategory category, String id) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(template),
                        Ingredient.of(base),
                        Ingredient.of(addition),
                        category,
                        result)
                .unlocks("has_" + id, has(addition)).save(out, IronAge.resLoc(getItemName(result) + "_smithing"));
    }
}
