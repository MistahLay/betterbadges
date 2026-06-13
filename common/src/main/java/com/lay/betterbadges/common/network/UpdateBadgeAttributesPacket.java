package com.lay.betterbadges.common.network;

import com.lay.betterbadges.common.emblem.Boost;
import com.lay.betterbadges.common.item.badges.BadgeItem;
import com.lay.betterbadges.common.league.attributes.BadgeAttribute;
import com.lay.betterbadges.common.league.attributes.BadgeAttributesManager;

import java.util.Map;

public record UpdateBadgeAttributesPacket(Map<BadgeItem, Map<Boost, BadgeAttribute>> attributes) {
    public void update(){
        BadgeAttributesManager.reset();
        for (Map.Entry<BadgeItem, Map<Boost, BadgeAttribute>> badgeItemAttribute : attributes.entrySet()){
            BadgeAttributesManager.register(badgeItemAttribute.getKey(), badgeItemAttribute.getValue());
        }
    }
}
