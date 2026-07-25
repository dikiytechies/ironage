package com.dikiytechies.ironage.util;

import com.dikiytechies.ironage.IronAge;
import com.dikiytechies.ironage.world.ClientWorldAge;
import com.dikiytechies.ironage.world.WorldAge;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.Set;

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
                if (item.getItem().is(Items.IRON_INGOT)) {
                    ItemStack newStack = new ItemStack(Items.IRON_NUGGET);
                    newStack.setCount(item.getItem().getCount());
                    item.setItem(newStack);
                }
            });
        }
    }

    @SubscribeEvent
    public static void harvestCheck(PlayerEvent.HarvestCheck event) {
        Player player = event.getEntity();
        if (event.canHarvest() && event.getTargetBlock().requiresCorrectToolForDrops()) {
            WorldAge.WorldStage stage;
            if (!player.level().isClientSide()) {
                stage = WorldAge.get(player.getServer()).get();
            } else {
                stage = ClientWorldAge.getInstance().getStage();
            }
            IronAge.LOGGER.debug(stage.toString());
            if (player.getMainHandItem().getItem() instanceof TieredItem tool) {
                if (checkStoneTool(stage, tool)) {
                    event.setCanHarvest(event.getTargetBlock().is(Tiers.WOOD.getIncorrectBlocksForDrops()));
                } else if (checkLateGameTools(stage, tool)) {
                    event.setCanHarvest(event.getTargetBlock().is(Tiers.STONE.getIncorrectBlocksForDrops()));
                }
            }
        }
    }

    private static boolean checkStoneTool(WorldAge.WorldStage stage, TieredItem tool) {
        return !stage.equals(WorldAge.WorldStage.PRE_STONE) && tool.getTier().equals(Tiers.STONE);
    }

    private static final Set<Tier> lateGameTiers = Set.of(Tiers.IRON, Tiers.DIAMOND, Tiers.NETHERITE);

    private static boolean checkLateGameTools(WorldAge.WorldStage stage, TieredItem tool) {
        return !stage.equals(WorldAge.WorldStage.DEFAULT) && lateGameTiers.contains(tool.getTier());
    }
}
