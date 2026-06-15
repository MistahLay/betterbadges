package com.lay.betterbadges.common.attribute.cobblemon;

import com.lay.betterbadges.common.attribute.ModAttributes;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class BattlingAttributes {

    public static Holder<Attribute> XP_REWARD = ModAttributes.registerRanged(pathBattleRewards("xp"));
    public static Holder<Attribute> LOOT_REWARD = ModAttributes.registerRanged(pathBattleRewards("loot"));

    private static String pathBattleRewards(String reward){
        return rootPath("reward." + reward);
    }

    private static String rootPath(String string){
        return "player.cobbleattribbutes.battle." + string;
    }

    public static void registerBattlingAttributes(){ }
}
