package com.lay.betterbadges.common.util.cobblemon;

import com.lay.betterbadges.common.api.attribute.cobblemon.MiscCobblemonAttributes;
import net.minecraft.server.level.ServerPlayer;

import static com.lay.betterbadges.common.util.PlayerAttributeHelper.attributeValue;

public class MiscCobblemonHelper {

    public static int getXpCandyBoost(ServerPlayer player, int original){
        return original * (int) attributeValue(player, MiscCobblemonAttributes.XP_CANDY_BOOST);
    }

    public static int getBonusBerryYield(ServerPlayer player, int original){
        return original + (int) attributeValue(player, MiscCobblemonAttributes.BERRY_YIELD_BONUS);
    }

    public static int getBonusFriendship(ServerPlayer player, int original){
        return original + (int) attributeValue(player, MiscCobblemonAttributes.FRIENDSHIP_POINTS_BONUS);
    }

}
