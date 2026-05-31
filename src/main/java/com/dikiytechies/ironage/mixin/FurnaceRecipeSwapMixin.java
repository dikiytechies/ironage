package com.dikiytechies.ironage.mixin;

import com.dikiytechies.ironage.world.WorldAge;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import javax.annotation.Nullable;

@Mixin(AbstractFurnaceBlockEntity.class)
public class FurnaceRecipeSwapMixin {
    // todo fix item flickering on shift + left click
    // todo fix item burn cancel
    @ModifyVariable(method = "burn", at = @At("STORE"), ordinal = 1)
    private static ItemStack swapIngots(ItemStack result, RegistryAccess access, @Nullable RecipeHolder<?> recipe, NonNullList<ItemStack> slots, int maxResultSize, AbstractFurnaceBlockEntity furnace) {
        if (!furnace.getLevel().isClientSide()) {
            if (!WorldAge.get(furnace.getLevel().getServer()).get().equals(WorldAge.WorldStage.DEFAULT) && result.is(Items.IRON_INGOT)) {
                ItemStack newStack = new ItemStack(Items.IRON_NUGGET);
                newStack.setCount(result.getCount());
                return newStack;
            }
        }
        return result;
    }
}
