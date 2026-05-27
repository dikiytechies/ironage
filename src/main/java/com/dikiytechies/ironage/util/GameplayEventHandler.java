package com.dikiytechies.ironage.util;

import com.dikiytechies.ironage.world.WorldAge;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

import static com.dikiytechies.ironage.IronAge.MOD_ID;

@EventBusSubscriber(modid = MOD_ID)
public class GameplayEventHandler {
    @SubscribeEvent
    public static void onInteract(LivingEvent.LivingJumpEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof Player) {
            System.out.println(WorldAge.get(event.getEntity().level().getServer()).get().name());
            WorldAge.get(event.getEntity().level().getServer()).set(WorldAge.WorldStage.values()[((WorldAge.get(event.getEntity().level().getServer()).get().ordinal() + 1) % 3)]);
        }
    }
}
