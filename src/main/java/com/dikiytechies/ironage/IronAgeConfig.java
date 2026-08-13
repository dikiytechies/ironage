package com.dikiytechies.ironage;

import com.dikiytechies.ironage.util.ModUtil;
import com.dikiytechies.ironage.world.WorldAge;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public final ModConfigSpec.ConfigValue<Boolean> ignoreDefaultItems;

    public IronAgeConfig(ModConfigSpec.Builder builder) {
        builder.push("World Options");
        builder.comment("Determines the stage worlds will start on");
        startingStage = builder.defineEnum("starting_stage", WorldAge.WorldStage.PRE_STONE);
        builder.pop();
        builder.push("Item component assignment");
        builder.comment("Ignores default items to ban");
        builder.comment("This includes both armor materials and tool tiers for late-game and STONE for early");
        builder.comment("Tools: (STONE, GOLD ; IRON, DIAMOND and NETHERITE)");
        builder.comment("Armor: (CHAIN, GOLD ; IRON, DIAMOND and NETHERITE)");
        ignoreDefaultItems = builder.define("ignore_default", false);
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
        return list.stream()
            .filter(str -> ResourceLocation.tryParse(str) != null)
            .map(str -> {
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

    private boolean shouldProcess(WorldAge.WorldStage stage, Item item, boolean ignoreDefaults) {
        Set<Item> manuallyBanned = new HashSet<>();
        if (stage.equals(WorldAge.WorldStage.PRE_STONE)) {
            manuallyBanned.addAll(getPreStoneBanned());
            manuallyBanned.addAll(getPreIronBanned());
        } else if (stage.equals(WorldAge.WorldStage.PRE_IRON)) {
            manuallyBanned.addAll(getPreIronBanned());
        }
        boolean isInBanList = manuallyBanned.contains(item);
        boolean shouldBeBannedByDefault = false;

        if (!ignoreDefaults) {
            shouldBeBannedByDefault = ModUtil.checkItem(stage, item);
        }

        return isInBanList || shouldBeBannedByDefault;
    }

    public boolean shouldProcess(WorldAge.WorldStage stage, Item item) {
        return shouldProcess(stage, item, ignoreDefaultItems.get());
    }
}
