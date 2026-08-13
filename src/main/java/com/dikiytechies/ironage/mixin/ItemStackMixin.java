package com.dikiytechies.ironage.mixin;

import com.dikiytechies.ironage.IronAgeConfig;
import com.dikiytechies.ironage.world.WorldAge;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract Item getItem();
    @Shadow public abstract int getMaxDamage();

    @ModifyVariable(method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V",
            at = @At("HEAD"), argsOnly = true, ordinal = 0)
    public int immediatelyBreak(int damage, @Local(argsOnly = true) ServerLevel level) {
        WorldAge.WorldStage stage = WorldAge.get(level.getServer()).get();
        var config = IronAgeConfig.CONFIG;
        if (config.shouldProcess(stage, getItem())) {
            damage = getMaxDamage();
        }
        return damage;
    }
}
