package com.lay.betterbadges.common.util.cobblemon;

import com.cobblemon.mod.common.api.drop.DropEntry;
import com.cobblemon.mod.common.api.drop.DropTable;
import com.cobblemon.mod.common.api.pokemon.stats.Stat;
import com.lay.betterbadges.common.api.attribute.cobblemon.BattleRewardsAttributes;
import com.lay.betterbadges.common.api.attribute.cobblemon.MiscCobblemonAttributes;
import com.lay.betterbadges.common.util.PlayerAttributeHelper;
import kotlin.ranges.IntRange;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

import static com.lay.betterbadges.common.util.PlayerAttributeHelper.attributeValue;

public class BattleRewardsModifierHelper {

    public static int getXpRewardBoost(ServerPlayer player, int original){
        return original * (int) attributeValue(player, BattleRewardsAttributes.XP_BOOST);
    }

    /**
     * Adds a guaranteed amount based on the attribute to the total drops unless it reaches max value
     */
    public static void modifyLoot(ServerPlayer player, DropTable dropTable, List<DropEntry> drops){
        drops.clear();
        drops.addAll(dropTable.getDrops(new IntRange(1, (int) attributeValue(player, BattleRewardsAttributes.POSSIBLE_DROPS)), null));
    }

    public static int getBonusEvYield(ServerPlayer player, int original, Stat ev){
        return original + (int) attributeValue(player, BattleRewardsAttributes.EV_BONUS.get(ev));

    }
}
