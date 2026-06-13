package com.lay.betterbadges.common.league;

import com.lay.betterbadges.common.league.attributes.BadgeAttribute;
import com.lay.betterbadges.common.emblem.Boost;
import com.lay.betterbadges.common.item.badges.BadgeItem;
import com.lay.betterbadges.common.league.attributes.BadgeAttributesManager;
import org.jetbrains.annotations.Nullable;

// Slot Information
public record BadgeSlot(BadgeItem item, int x, int y, int slot) {

    @Nullable
    public BadgeAttribute getAttribute(Boost boost) {
        return BadgeAttributesManager.getAttribute(item, boost);
    }

    public boolean containsBoost(Boost boost) {
        return BadgeAttributesManager.containsAttribute(item, boost);
    }

}