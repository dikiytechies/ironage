package com.dikiytechies.ironage.data.loot.predicates;

import com.dikiytechies.ironage.init.GlobalLootModifierInit;
import com.dikiytechies.ironage.world.WorldAge;
import com.dikiytechies.ironage.world.WorldAgeState;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.Optional;

public record StageCheck(Optional<WorldAgeState.WorldStage> stage) implements LootItemCondition {
    public static final MapCodec<StageCheck> CODEC = RecordCodecBuilder.mapCodec(inst ->
            inst.group(WorldAgeState.WorldStage.CODEC.optionalFieldOf("stage").forGetter(StageCheck::stage))
            .apply(inst, StageCheck::new));


    @Override
    public LootItemConditionType getType() {
        return GlobalLootModifierInit.STAGE_CHECK.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        return this.stage.isPresent() && this.stage.get().equals(WorldAge.getStage(lootContext.getLevel().getServer()).getStage());
    }

    public static Builder staging() { return new Builder(); }

    public static class Builder implements LootItemCondition.Builder {
        private Optional<WorldAgeState.WorldStage> stage = Optional.empty();

        public Builder setStage(WorldAgeState.WorldStage stage) {
            this.stage = Optional.of(stage);
            return this;
        }

        @Override
        public LootItemCondition build() {
            return new StageCheck(stage);
        }
    }
}
