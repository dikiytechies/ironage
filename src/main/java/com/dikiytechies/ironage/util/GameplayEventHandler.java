package com.dikiytechies.ironage.util;

import com.dikiytechies.ironage.world.WorldAge;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

import java.util.Collection;
import java.util.stream.Collectors;

import static com.dikiytechies.ironage.IronAge.MOD_ID;

@EventBusSubscriber(modid = MOD_ID)
public class GameplayEventHandler {
    @SubscribeEvent
    public static void onInteract(LivingEvent.LivingJumpEvent event) {
        // todo remove debug
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof Player) {
            System.out.println(WorldAge.get(event.getEntity().level().getServer()).get().name());
            WorldAge.get(event.getEntity().level().getServer()).set(WorldAge.WorldStage.values()[((WorldAge.get(event.getEntity().level().getServer()).get().ordinal() + 1) % 3)]);
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        transformIronIngots(event);
    }

    private static void transformIronIngots(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof ServerPlayer) && !WorldAge.get(entity.getServer()).get().equals(WorldAge.WorldStage.DEFAULT)) {
            event.getDrops().forEach(item -> {
                if (item.getItem().getItem().equals(Items.IRON_INGOT)) {
                    ItemStack newStack = new ItemStack(Items.IRON_NUGGET);
                    newStack.setCount(item.getItem().getCount());
                    item.setItem(newStack);
                }
            });
        }
    }
}
