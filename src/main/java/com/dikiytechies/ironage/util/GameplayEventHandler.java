package com.dikiytechies.ironage.util;

import com.dikiytechies.ironage.IronAgeConfig;
import com.dikiytechies.ironage.network.s2c.SyncWorldAge;
import com.dikiytechies.ironage.world.ClientWorldAge;
import com.dikiytechies.ironage.world.WorldAge;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Arrays;

import static com.dikiytechies.ironage.IronAge.MOD_ID;

@EventBusSubscriber(modid = MOD_ID)
public class GameplayEventHandler {
    @SubscribeEvent
    public static void onInteract(LivingEvent.LivingJumpEvent event) {
        // todo remove debug
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof Player) {
            WorldAge.get(event.getEntity().level().getServer()).set(WorldAge.WorldStage.values()[((WorldAge.get(event.getEntity().level().getServer()).get().ordinal() + 1) % WorldAge.WorldStage.values().length)]);
            System.out.println(WorldAge.get(event.getEntity().level().getServer()).get().name());
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
            if (player.getMainHandItem().getItem() instanceof TieredItem tool) {
                var config = IronAgeConfig.CONFIG;
                if (ModUtil.checkItem(stage, tool) && !config.ignoreDefaultItems.get()) {
                    int toolOrdinal = Arrays.stream(Tiers.values())
                            .filter(t -> t.equals(tool.getTier()))
                            .findAny()
                            .map(Enum::ordinal)
                            .orElse(0);
                    event.setCanHarvest(event.getTargetBlock().is(Tiers.values()[Math.max(0, toolOrdinal - 1)].getIncorrectBlocksForDrops()));
                }
            }
        }
    }

    @SubscribeEvent // this event is server-side
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        WorldAge.WorldStage stage = WorldAge.get(event.getEntity().getServer()).get();
        PacketDistributor.sendToPlayer((ServerPlayer) event.getEntity(), new SyncWorldAge(stage));
    }

    @SubscribeEvent
    public static void addToolTips(ItemTooltipEvent event) {
        warnUsingBannedItem(event);
    }

    private static void warnUsingBannedItem(ItemTooltipEvent event) {
        if (event.getContext().level() != null && event.getContext().level().isClientSide()) {
            var config = IronAgeConfig.CONFIG;
            ItemStack stack = event.getItemStack();
            WorldAge.WorldStage stage = ClientWorldAge.getInstance().getStage();
            if (config.shouldProcess(stage, stack.getItem())) {
                event.getToolTip().add(Component.translatable(String.format("tooltip.%s.item_warn", MOD_ID)).withStyle(ChatFormatting.DARK_RED));
            }
        }
    }
}
