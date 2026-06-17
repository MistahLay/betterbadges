package com.lay.betterbadges.common.api.attribute.cobblemon;

import com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class BattleRewardsAttributes {

    public static Holder<Attribute> XP_BOOST = BetterBadgesAttributes.registerRanged(pathBattleRewards("xp_boost"));
    public static Holder<Attribute> POSSIBLE_DROPS = BetterBadgesAttributes.registerRanged(pathBattleRewards("possible_drops"), 1.0, 1.0, 1024.0);
    public static Holder<Attribute> XP_CANDY_BOOST = BetterBadgesAttributes.registerRanged("xp_candy_boost");

    private static String pathBattleRewards(String reward){
        return rootPath("reward." + reward);
    }

    private static String rootPath(String string){
        return "player.cobbleattribbutes.battle." + string;
    }

    public static void registerBattlingAttributes(){}
}