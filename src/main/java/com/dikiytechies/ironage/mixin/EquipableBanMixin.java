package com.dikiytechies.ironage.mixin;

import com.dikiytechies.ironage.IronAgeConfig;
import com.dikiytechies.ironage.world.ClientWorldAge;
import com.dikiytechies.ironage.world.WorldAge;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Equipable.class)
public interface EquipableBanMixin {
    // todo connect to tiers
    @Inject(method = "swapWithEquipmentSlot", at = @At("HEAD"), cancellable = true)
    default void banItemOnUse(Item item, Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack stack = player.getItemInHand(hand);
        WorldAge.WorldStage stage = level.isClientSide()? ClientWorldAge.getInstance().getStage(): WorldAge.get(level.getServer()).get();
        var config = IronAgeConfig.CONFIG;
        boolean shouldProcess = stage.equals(WorldAge.WorldStage.PRE_STONE)? config.getPreStoneBanned().contains(item):
                stage.equals(WorldAge.WorldStage.PRE_IRON) && (config.getPreStoneBanned().contains(item) || config.getPreIronBanned().contains(item));
        if (shouldProcess) {
            cir.setReturnValue(InteractionResultHolder.fail(stack));
            cir.cancel();
        }
    }
}
