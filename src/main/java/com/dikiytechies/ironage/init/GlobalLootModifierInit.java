package com.dikiytechies.ironage.init;

import com.dikiytechies.ironage.data.loot.ModLootReplacer;
import com.dikiytechies.ironage.data.loot.predicates.StageCheck;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static com.dikiytechies.ironage.IronAge.MOD_ID;

public class GlobalLootModifierInit {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER = DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, MOD_ID);

    public static final Supplier<MapCodec<ModLootReplacer>> LOOT_MODIFIER = GLOBAL_LOOT_MODIFIER.register("loot_item_replacer",
            () -> ModLootReplacer.CODEC);

    public static final Supplier<LootItemConditionType> STAGE_CHECK = LOOT_CONDITION.register("stage_check",
            () -> new LootItemConditionType(StageCheck.CODEC));
}
