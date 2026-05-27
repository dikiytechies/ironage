package com.dikiytechies.ironage;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(IronAge.MOD_ID)
public class IronAge {
    public static final String MOD_ID = "iron_age";

    public static final Logger LOGGER = LogUtils.getLogger();

    public IronAge(IEventBus bus, ModContainer modContainer) {

    }
}
