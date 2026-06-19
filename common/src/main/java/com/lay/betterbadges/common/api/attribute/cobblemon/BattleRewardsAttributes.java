package com.lay.betterbadges.common.api.attribute.cobblemon;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import static com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes.registerRanged;

public class BattleRewardsAttributes {

    public static Holder<Attribute> XP_BOOST = registerRanged(pathBattleRewards("xp_boost"));
    public static Holder<Attribute> XP_CANDY_BOOST = registerRanged("xp_candy_boost");
    public static Holder<Attribute> POSSIBLE_DROPS = registerRanged(pathBattleRewards("possible_drops"), 1.0, 1.0, 1024.0);

    private static String pathBattleRewards(String reward){
        return rootPath("reward." + reward);
    }

    private static String rootPath(String string){
        return "player.cobbleattribbutes.battle." + string;
    }

    public static void registerAttributes(){ }

    public static void applyToBuilder(AttributeSupplier.Builder builder){
        builder
                .add(XP_BOOST)
                .add(XP_CANDY_BOOST)
                .add(POSSIBLE_DROPS);
    }
}