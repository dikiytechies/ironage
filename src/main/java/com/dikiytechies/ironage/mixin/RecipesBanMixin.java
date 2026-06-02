package com.dikiytechies.ironage.mixin;

import com.dikiytechies.ironage.world.WorldAge;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Mixin(RecipeManager.class)
public abstract class RecipesBanMixin {
    @Shadow
    private <I extends RecipeInput, T extends Recipe<I>> Collection<RecipeHolder<T>> byType(RecipeType<T> p_44055_) {
        return null;
    }

    @Inject(method = "getRecipeFor(Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/item/crafting/RecipeInput;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/crafting/RecipeHolder;)Ljava/util/Optional;",
            at = @At("HEAD"), cancellable = true)
    public <I extends RecipeInput, T extends Recipe<I>> void banRecipes(
            RecipeType<T> recipe, I input, Level level, @Nullable RecipeHolder<T> lastRecipe, CallbackInfoReturnable<Optional<RecipeHolder<T>>> cir) {
        if (!level.isClientSide()) {
            if (isCookingRecipe(recipe) && checkIron(recipe, input, level)) {
                cir.setReturnValue(getNuggetsRecipe(recipe, level));
                cir.cancel();
            } else if (checkIron(recipe, input, level) || checkStone(recipe, input, level)) {
                cir.setReturnValue(Optional.empty());
                cir.cancel();
            }
        }
    }

    @Unique
    private <I extends RecipeInput, T extends Recipe<I>> boolean checkIron(RecipeType<T> recipe, I input, Level level) {
        return this.byType(recipe).stream().anyMatch(r -> r.value().matches(input, level)
                && r.value().getResultItem(level.registryAccess()).is(Items.IRON_INGOT)) &&
                !WorldAge.get(level.getServer()).get().equals(WorldAge.WorldStage.DEFAULT);
    }

    @Unique
    private <I extends RecipeInput, T extends Recipe<I>> boolean checkStone(RecipeType<T> recipe, I input, Level level) {
        return this.byType(recipe).stream().anyMatch(r -> r.value().matches(input, level)
                && stoneTools.contains(r.value().getResultItem(level.registryAccess()).getItem())) &&
                WorldAge.get(level.getServer()).get().equals(WorldAge.WorldStage.PRE_STONE);
    }

    @Unique
    private <I extends RecipeInput, T extends Recipe<I>> Optional<RecipeHolder<T>> getNuggetsRecipe(RecipeType<T> recipe, Level level) {
        return this.byType(recipe).stream().filter(r -> r.value().getResultItem(level.registryAccess()).is(Items.IRON_NUGGET)).findFirst();
    }

    @Unique
    private static <I extends RecipeInput, T extends Recipe<I>> boolean isCookingRecipe(RecipeType<T> type) {
        return type == RecipeType.SMELTING || type == RecipeType.BLASTING;
    }

    @Unique
    private List<Item> stoneTools = List.of(
            Items.STONE_AXE,
            Items.STONE_HOE,
            Items.STONE_PICKAXE,
            Items.STONE_SHOVEL,
            Items.STONE_SWORD
    );
}
