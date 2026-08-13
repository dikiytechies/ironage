package com.dikiytechies.ironage.util;

import com.dikiytechies.ironage.IronAgeConfig;
import com.dikiytechies.ironage.world.WorldAge;
import net.minecraft.world.item.*;

import java.util.List;

public class ModUtil {
    public static final List<Tier> STONE_TIERS = List.of(
            Tiers.STONE,
            Tiers.GOLD
    );
    public static final List<Tier> IRON_TIERS = List.of(
            Tiers.IRON
    );
    public static final List<Tier> LATE_TIERS = List.of(
            Tiers.DIAMOND,
            Tiers.NETHERITE
    );
    @Deprecated
    public static boolean checkStoneTool(WorldAge.WorldStage stage, TieredItem tool) {
        boolean isInConfig = IronAgeConfig.CONFIG.getPreStoneBanned().contains(tool);
        return stage.equals(WorldAge.WorldStage.PRE_STONE) && (STONE_TIERS.contains(tool.getTier()) || isInConfig);
    }
    @Deprecated
    public static boolean checkIronTools(WorldAge.WorldStage stage, TieredItem tool) {
        boolean isInConfig = IronAgeConfig.CONFIG.getPreIronBanned().contains(tool);
        return !stage.equals(WorldAge.WorldStage.DEFAULT) && (IRON_TIERS.contains(tool.getTier()) || isInConfig);
    }

    public static final List<ArmorMaterial> STONE_MATERIALS = List.of();
    public static final List<ArmorMaterial> IRON_MATERIALS = List.of(
            ArmorMaterials.IRON.value()
    );
    public static final List<ArmorMaterial> LATE_MATERIALS = List.of(
            ArmorMaterials.DIAMOND.value(),
            ArmorMaterials.NETHERITE.value()
    );
    @Deprecated
    public static boolean checkStoneMaterial(WorldAge.WorldStage stage, ArmorItem armor) {
        boolean isInConfig = IronAgeConfig.CONFIG.getPreStoneBanned().contains(armor);
        return stage.equals(WorldAge.WorldStage.PRE_STONE) && (STONE_MATERIALS.contains(armor.getMaterial().value()) || isInConfig);
    }
    @Deprecated
    public static boolean checkIronMaterial(WorldAge.WorldStage stage, ArmorItem armor) {
        boolean isInConfig = IronAgeConfig.CONFIG.getPreIronBanned().contains(armor);
        return !stage.equals(WorldAge.WorldStage.DEFAULT) && (IRON_MATERIALS.contains(armor.getMaterial().value()) || isInConfig);
    }

    public static<T extends Item> boolean checkItem(WorldAge.WorldStage stage, T item) {
        if (stage.equals(WorldAge.WorldStage.DEFAULT)) return false;

        for (int i = stage.ordinal(); i <= WorldAge.WorldStage.values().length; i++) {
            boolean result = false;
            if (item instanceof ArmorItem armor) {
                result = WorldAge.WorldStage.values()[i].armorMaterial.contains(armor.getMaterial().value());
            } else if (item instanceof TieredItem tool) {
                result = WorldAge.WorldStage.values()[i].toolTier.contains(tool.getTier());
            }
            if (result)
                return result;
        }
        return false;
    }
}
