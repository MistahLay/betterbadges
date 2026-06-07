package com.lay.betterbadges.common.emblem;

import com.lay.betterbadges.common.item.badges.BadgeItem;
import com.lay.betterbadges.common.league.BadgeSlot;

public record EmblemSlot(Boost category, int x, int y, int index) {
    public boolean isValid(BadgeItem item){
        BadgeSlot badgeSlot = item.getBadgeSlot();
        return badgeSlot.containsBoost(this.category);
    }
}
