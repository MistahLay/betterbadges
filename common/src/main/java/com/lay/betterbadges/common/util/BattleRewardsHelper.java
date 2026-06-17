package com.lay.betterbadges.common.util;

import com.cobblemon.mod.common.api.drop.DropEntry;
import com.cobblemon.mod.common.api.drop.DropTable;
import com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes;
import com.lay.betterbadges.common.api.attribute.cobblemon.BattleRewardsAttributes;
import kotlin.ranges.IntRange;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.List;

public class BattleRewardsHelper {

    private final ServerPlayer player;

    public static BattleRewardsHelper create(ServerPlayer player){
        return new BattleRewardsHelper(player);
    }

    public BattleRewardsHelper(ServerPlayer player){
        this.player = player;
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

    private float attributeValue(Holder<Attribute> attribute){
        return (float) this.player.getAttributeValue(BetterBadgesAttributes.actual(attribute));
    }

}
