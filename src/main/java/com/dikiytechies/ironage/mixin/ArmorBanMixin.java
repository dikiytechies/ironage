package com.dikiytechies.ironage.mixin;

import com.dikiytechies.ironage.IronAgeConfig;
import com.dikiytechies.ironage.world.ClientWorldAge;
import com.dikiytechies.ironage.world.WorldAge;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IItemStackExtension.class)
public interface ArmorBanMixin {
    @Shadow
    ItemStack self();

    @Inject(method = "canEquip", at = @At("HEAD"), cancellable = true)
    default void preventEquip(EquipmentSlot armorType, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        WorldAge.WorldStage stage = entity.level().isClientSide()? ClientWorldAge.getInstance().getStage(): WorldAge.get(entity.level().getServer()).get();
        var config = IronAgeConfig.CONFIG;
        if (config.shouldProcess(stage, self().getItem()) && !config.shouldProcessThisOrPrevOnly(stage, self().getItem())) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
