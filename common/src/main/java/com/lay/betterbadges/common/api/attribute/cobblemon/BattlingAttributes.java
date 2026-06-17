package com.lay.betterbadges.common.api.attribute.cobblemon;

import com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class BattlingAttributes {

    public static Holder<Attribute> XP_REWARD = BetterBadgesAttributes.registerRanged(pathBattleRewards("xp"));
    public static Holder<Attribute> LOOT_REWARD = BetterBadgesAttributes.registerRanged(pathBattleRewards("loot"));

    private static String pathBattleRewards(String reward){
        return rootPath("reward." + reward);
    }

    private static String rootPath(String string){
        return "player.cobbleattribbutes.battle." + string;
    }

    public static void registerBattlingAttributes(){}
}
