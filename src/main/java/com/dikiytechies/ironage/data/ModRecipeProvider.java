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
