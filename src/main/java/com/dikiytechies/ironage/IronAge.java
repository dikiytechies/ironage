package com.dikiytechies.ironage;

import com.dikiytechies.ironage.data.DataGenerators;
import com.dikiytechies.ironage.init.GlobalLootModifierInit;
import com.dikiytechies.ironage.init.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(IronAge.MOD_ID)
public class IronAge {
    public static final String MOD_ID = "iron_age";

    public static final Logger LOGGER = LogUtils.getLogger();

    public IronAge(IEventBus bus, ModContainer modContainer) {
        GlobalLootModifierInit.GLOBAL_LOOT_MODIFIER.register(bus);
        GlobalLootModifierInit.LOOT_CONDITION.register(bus); // Я ишак бляя
        ModItems.ITEMS.register(bus);

        bus.addListener(DataGenerators::gatherData);
    }

    public static ResourceLocation resLoc(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
