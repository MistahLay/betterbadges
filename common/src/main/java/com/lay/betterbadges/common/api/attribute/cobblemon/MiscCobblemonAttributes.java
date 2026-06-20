package com.lay.betterbadges.common.api.attribute.cobblemon;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import static com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes.registerPercentage;
import static com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes.registerRanged;

/**
 * Cobblemon Attributes that does not have a specific Group
 */
public class MiscCobblemonAttributes {

    public static Holder<Attribute> POKEBALL_USE = registerPercentage(rootPath("pokeball_use"), 1.0);
    public static Holder<Attribute> SCAN_POKEMON = registerRanged("scan_pokemon");

    public static Holder<Attribute> BERRY_YIELD_BONUS = registerRanged(rootPath("berry_yield"), 0.0);
    public static Holder<Attribute> FRIENDSHIP_POINTS_BONUS = registerRanged(rootPath("friendship_points_bonus"), 0.0);
    public static Holder<Attribute> XP_CANDY_BOOST = registerRanged(rootPath("xp_candy_boost"));

    private static String rootPath(String string){
        return "player.cobbleattribbutes.misc." + string;
    }

    public static void registerAttributes(){

    }

    public static void applyToBuilder(AttributeSupplier.Builder builder){
        builder
                .add(POKEBALL_USE)
                .add(BERRY_YIELD_BONUS)
                .add(XP_CANDY_BOOST)
                .add(FRIENDSHIP_POINTS_BONUS)
                .add(SCAN_POKEMON, 0.0d);
    }

}
