package com.dikiytechies.ironage;

import com.dikiytechies.ironage.world.WorldAge;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class IronAgeConfig {
    public static final IronAgeConfig CONFIG;
    public static final ModConfigSpec SPEC;

    static {
        Pair<IronAgeConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(IronAgeConfig::new);

        CONFIG = pair.getLeft();
        SPEC = pair.getRight();
    }

    public final ModConfigSpec.EnumValue<WorldAge.WorldStage> startingStage;
    public final ModConfigSpec.ConfigValue<List<? extends String>> preStoneItems;
    public final ModConfigSpec.ConfigValue<List<? extends String>> preIronItems;

    public IronAgeConfig(ModConfigSpec.Builder builder) {
        builder.push("World Options");
        builder.comment("Determines the stage worlds will start on");
        startingStage = builder.defineEnum("starting_stage", WorldAge.WorldStage.PRE_STONE);
        builder.pop();
        builder.push("Item component assignment");
        builder.comment("Assigns items banned on PRE_STONE");
        preStoneItems = builder.defineList("pre_stone_items", List.of(),
                () -> "minecraft:air",
                loc -> loc instanceof String str && ResourceLocation.tryParse(str) != null);
        builder.comment("Assigns items banned on PRE_IRON");
        preIronItems = builder.defineListAllowEmpty("pre_iron_items", List.of(),
                () -> "minecraft:air",
                loc -> loc instanceof String str && ResourceLocation.tryParse(str) != null);
        builder.pop();
    }

    private List<Item> getBannedFromList(List<? extends String> list) {
        return list.stream().map(str -> {
            var loc = ResourceLocation.tryParse(str);
            if (loc != null) {
                return BuiltInRegistries.ITEM.get(loc);
            }
            return null;
        }).toList();
    }

    public List<Item> getPreStoneBanned() {
        return getBannedFromList(preStoneItems.get());
    }

    public List<Item> getPreIronBanned() {
        return getBannedFromList(preIronItems.get());
    }
}
