package com.lay.betterbadges.common.api.league.attributes;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.emblem.Boost;
import com.lay.betterbadges.common.item.badges.BadgeItem;
import com.lay.betterbadges.common.network.BetterBadgesNetworkChannel;
import com.lay.betterbadges.common.network.UpdateBadgeAttributesPacket;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class BadgeAttributesManager {
    private static Map<BadgeItem, Map<Boost, BadgeAttribute>> attributes = new HashMap<>();

    public static void reset(){
        attributes = new HashMap<>();
    }

    public static void register(BadgeItem item, Map<Boost, BadgeAttribute> badgeAttributes){
        attributes.putIfAbsent(item, badgeAttributes);
    }

    public static @Nullable HashMap<Boost, BadgeAttribute> getAttributes(BadgeItem badge){
        if(!attributes.containsKey(badge)) return null;
        return new HashMap<>(attributes.get(badge));
    }

    public static @Nullable BadgeAttribute getAttribute(BadgeItem badge, Boost boost){
        final var attributes = getAttributes(badge);
        if(attributes == null) return null;
        return attributes.getOrDefault(boost, null);
    }

    public static boolean containsAttribute(BadgeItem badge, Boost boost){
        final var attributes = getAttributes(badge);
        if(attributes == null) return false;
        return attributes.containsKey(boost);
    }

    public static void updateClients(){
        for (ServerPlayer player : BetterBadges.SERVER.getPlayerList().getPlayers()){
            BetterBadgesNetworkChannel.CHANNEL.serverHandle(player).send(new UpdateBadgeAttributesPacket(attributes));
        }
    }

    public static void updateClient(ServerPlayer player){
        BetterBadgesNetworkChannel.CHANNEL.serverHandle(player).send(new UpdateBadgeAttributesPacket(attributes));
    }
}
