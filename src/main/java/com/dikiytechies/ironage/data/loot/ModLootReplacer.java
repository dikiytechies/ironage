package com.dikiytechies.ironage.data.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.stream.Collectors;

public class ModLootReplacer extends LootModifier {
    public static final MapCodec<ModLootReplacer> CODEC = RecordCodecBuilder.mapCodec(inst ->
        LootModifier.codecStart(inst).and(inst.group(
                        BuiltInRegistries.ITEM.byNameCodec().fieldOf("to_replace").forGetter(e -> e.toReplace),
                        BuiltInRegistries.ITEM.byNameCodec().fieldOf("replace_with").forGetter(e -> e.replaceWith)))
                .apply(inst, ModLootReplacer::new));

    private final Item toReplace;
    private final Item replaceWith;

    public ModLootReplacer(LootItemCondition[] conditionsIn, Item toReplace, Item replaceWith) {
        super(conditionsIn);
        this.toReplace = toReplace;
        this.replaceWith = replaceWith;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> loot, LootContext lootContext) {
        if (!toReplace.equals(replaceWith)) {
            loot = loot.stream().map(item -> {
                if (item.is(toReplace)) {
                    ItemStack replace = new ItemStack(replaceWith, item.getCount());
                    replace.applyComponents(item.getComponents());
                    return replace;
                } else return item;
            }).collect(Collectors.toCollection(ObjectArrayList::new));
        }
        return loot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
