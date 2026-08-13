package com.dikiytechies.ironage.data;

import com.dikiytechies.ironage.IronAge;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput out, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> blockTags, String modId, ExistingFileHelper existingFileHelper) {
        super(out, provider, blockTags, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        addTags(Armor.class);
        addTags(Tool.class);
    }

    private void addTags(Class<? extends Enum<?>> types) {
        TagKey<Item> typeTag;
        for (var type : types.getEnumConstants()) {
            typeTag = ItemTags.create(IronAge.resLoc(
                    String.format("%s_%s_can_be_upgraded_to_iron", types.getSimpleName().toLowerCase(), type.name().toLowerCase())));
            for (var material : Materials.values()) {
                Item item = BuiltInRegistries.ITEM.get(
                        ResourceLocation.fromNamespaceAndPath(
                                "minecraft",
                                String.format("%s_%s", material.name().toLowerCase(), type.name().toLowerCase())));
                if (item != Items.AIR) this.tag(typeTag).add(item);
            }
        }
    }

    public static TagKey<Item> getUpgradeTag(Class<? extends Enum<?>> types, Enum<?> type) {
        return ItemTags.create(IronAge.resLoc(String.format("%s_%s_can_be_upgraded_to_iron", types.getSimpleName().toLowerCase(), type.name().toLowerCase())));
    }

    public enum Armor {
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS;
    }
    public enum Tool {
        AXE,
        HOE,
        PICKAXE,
        SHOVEL,
        SWORD;
    }
    public enum Materials {
        CHAINMAIL,
        GOLDEN,
        STONE;
    }
}
