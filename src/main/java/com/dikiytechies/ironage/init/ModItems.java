package com.dikiytechies.ironage.init;

import com.dikiytechies.ironage.IronAge;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

import static com.dikiytechies.ironage.IronAge.MOD_ID;
import static net.minecraft.world.item.SmithingTemplateItem.*;

@EventBusSubscriber(modid = MOD_ID)
public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredItem<SmithingTemplateItem> STONE_UPGRADE_SMITHING_TEMPLATE = ITEMS.registerItem("stone_upgrade_smithing_template",
            (p) -> createUpgrade("stone_upgrade", createToolsIconList(), List.of(EMPTY_SLOT_INGOT)), new Item.Properties());

    public static final DeferredItem<SmithingTemplateItem> CHAINMAIL_UPGRADE_SMITHING_TEMPLATE = ITEMS.registerItem("chainmail_upgrade_smithing_template",
            (p) -> createUpgrade("chainmail_upgrade", createTrimmableArmorIconList(), List.of(EMPTY_SLOT_INGOT)), new Item.Properties());


    @SubscribeEvent
    public static void addItemsToCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.INGREDIENTS)) {
            event.insertAfter(new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                    new ItemStack(STONE_UPGRADE_SMITHING_TEMPLATE.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STONE_UPGRADE_SMITHING_TEMPLATE.get()),
                    new ItemStack(CHAINMAIL_UPGRADE_SMITHING_TEMPLATE.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    private static List<ResourceLocation> createToolsIconList() {
        return List.of(
                EMPTY_SLOT_HOE,
                EMPTY_SLOT_AXE,
                EMPTY_SLOT_SWORD,
                EMPTY_SLOT_SHOVEL,
                EMPTY_SLOT_PICKAXE
        );
    }

    private static SmithingTemplateItem createUpgrade(String name, List<ResourceLocation> emptyBase, List<ResourceLocation> emptyAdditions) {
        return new SmithingTemplateItem(
                Component.translatable(Util.makeDescriptionId("item", IronAge.resLoc("smithing_template." + name + ".applies_to"))),
                Component.translatable(Util.makeDescriptionId("item", IronAge.resLoc("smithing_template." + name + ".ingridients"))),
                Component.translatable(Util.makeDescriptionId("upgrade", IronAge.resLoc(name))),
                Component.translatable(Util.makeDescriptionId("item", IronAge.resLoc("smithing_template." + name + ".base_slot_description"))),
                Component.translatable(Util.makeDescriptionId("item", IronAge.resLoc("smithing_template." + name + ".additions_slot_description"))),
                emptyBase, emptyAdditions, FeatureFlags.VANILLA);
    }
}
