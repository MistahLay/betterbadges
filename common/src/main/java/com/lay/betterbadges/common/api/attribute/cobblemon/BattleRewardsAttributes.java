package com.lay.betterbadges.common.api.attribute.cobblemon;

import com.cobblemon.mod.common.api.pokemon.stats.Stat;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.pokemon.EVs;
import com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes;
import com.lay.betterbadges.common.api.attribute.MultiAttributes;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import static com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes.registerRanged;

public class BattleRewardsAttributes {

    public static Holder<Attribute> XP_BOOST = registerRanged(pathBattleRewards("xp_boost"));
    public static Holder<Attribute> POSSIBLE_DROPS = registerRanged(pathBattleRewards("possible_drops"), 1.0, 1.0, 1024.0);

    public static MultiAttributes<Stat> EV_BONUS = new MultiAttributes<>(
            pathBattleRewards("ev."),
            original -> original.getIdentifier().getPath(),
            id -> registerRanged(id, 0.0, 0.0, EVs.MAX_STAT_VALUE),
            Stats.Companion.getPERMANENT()
    );

//    private static Holder<Attribute>

    private static String pathBattleRewards(String reward){
        return rootPath("reward." + reward);
    }

    private static String rootPath(String string){
        return "player.cobbleattribbutes.battle." + string;
    }

    public static void registerAttributes(){ }

    public static void applyToBuilder(AttributeSupplier.Builder builder){
        builder.add(XP_BOOST)
                .add(POSSIBLE_DROPS);
        EV_BONUS.applyToBuilder(builder);
    }
}