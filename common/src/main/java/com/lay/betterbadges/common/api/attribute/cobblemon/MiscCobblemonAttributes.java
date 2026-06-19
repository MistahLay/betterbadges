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

    private static String rootPath(String string){
        return "player.cobbleattribbutes.misc." + string;
    }

    public static void registerAttributes(){

    }

    public static void applyToBuilder(AttributeSupplier.Builder builder){
        builder
                .add(POKEBALL_USE)
                .add(SCAN_POKEMON, 0.0d);
    }

}
