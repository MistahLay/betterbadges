package com.lay.betterbadges.common.util.cobblemon;

import com.cobblemon.mod.common.api.drop.DropEntry;
import com.cobblemon.mod.common.api.drop.DropTable;
import com.lay.betterbadges.common.api.attribute.cobblemon.BattleRewardsAttributes;
import com.lay.betterbadges.common.util.PlayerAttributeHelper;
import kotlin.ranges.IntRange;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public class BattleRewardsModifierHelper extends PlayerAttributeHelper {

    public static BattleRewardsModifierHelper create(ServerPlayer player){
        return new BattleRewardsModifierHelper(player);
    }

    public BattleRewardsModifierHelper(ServerPlayer player) {
        super(player);
    }

    public int getXpRewardBoost(int original){
        return original * (int) this.attributeValue(BattleRewardsAttributes.XP_BOOST);
    }

    public int getXpCandyBoost(int original){
        return original * (int) this.attributeValue(BattleRewardsAttributes.XP_CANDY_BOOST);
    }

    /**
     * Adds a guaranteed amount based on the attribute to the total drops unless it reaches max value
     */
    public void modifyLoot(DropTable dropTable, List<DropEntry> drops){
        drops.clear();
        drops.addAll(dropTable.getDrops(new IntRange(1, (int) this.attributeValue(BattleRewardsAttributes.POSSIBLE_DROPS)), null));
    }

}
