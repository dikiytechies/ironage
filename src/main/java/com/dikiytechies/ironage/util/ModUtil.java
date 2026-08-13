package com.dikiytechies.ironage.util;

import com.dikiytechies.ironage.IronAgeConfig;
import com.dikiytechies.ironage.world.WorldAge;
import net.minecraft.world.item.*;

import java.util.List;

public class ModUtil {
    private static final List<Tier> LATE_GAME_TIERS = List.of(
            Tiers.IRON,
            Tiers.DIAMOND,
            Tiers.NETHERITE
    );

    public static boolean checkStoneTool(WorldAge.WorldStage stage, TieredItem tool) {
        boolean isInConfig = IronAgeConfig.CONFIG.getPreStoneBanned().contains(tool);
        return stage.equals(WorldAge.WorldStage.PRE_STONE) && (tool.getTier().equals(Tiers.STONE) || isInConfig);
    }

    public static boolean checkLateGameTools(WorldAge.WorldStage stage, TieredItem tool) {
        boolean isInConfig = IronAgeConfig.CONFIG.getPreIronBanned().contains(tool);
        return !stage.equals(WorldAge.WorldStage.DEFAULT) && (LATE_GAME_TIERS.contains(tool.getTier()) || isInConfig);
    }

    private static final List<ArmorMaterial> LATE_GAME_MATERIALS = List.of(
            ArmorMaterials.IRON.value(),
            ArmorMaterials.DIAMOND.value(),
            ArmorMaterials.NETHERITE.value()
    );

    public static boolean checkStoneMaterial(WorldAge.WorldStage stage, ArmorItem armor) {
        boolean isInConfig = IronAgeConfig.CONFIG.getPreStoneBanned().contains(armor);
        return stage.equals(WorldAge.WorldStage.PRE_STONE) && isInConfig;
    }

    public static boolean checkLateGameMaterial(WorldAge.WorldStage stage, ArmorItem armor) {
        boolean isInConfig = IronAgeConfig.CONFIG.getPreIronBanned().contains(armor);
        return !stage.equals(WorldAge.WorldStage.DEFAULT) && (LATE_GAME_MATERIALS.contains(armor.getMaterial().value()) || isInConfig);
    }
}
