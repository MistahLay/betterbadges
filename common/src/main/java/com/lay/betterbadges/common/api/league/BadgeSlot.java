package com.lay.betterbadges.common.api.league;

import com.lay.betterbadges.common.api.league.attributes.BadgeAttribute;
import com.lay.betterbadges.common.api.emblem.Boost;
import com.lay.betterbadges.common.item.badges.BadgeItem;
import com.lay.betterbadges.common.api.league.attributes.BadgeAttributesManager;
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