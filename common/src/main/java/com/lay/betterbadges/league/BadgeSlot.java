package com.lay.betterbadges.league;

import com.lay.betterbadges.datapack.attributes.BadgeAttributesReloadListener;
import com.lay.betterbadges.emblem.Boost;
import com.lay.betterbadges.item.badges.BadgeItem;
import org.jetbrains.annotations.Nullable;

// Slot Information
public record BadgeSlot(BadgeItem item, int x, int y, int slot) {

    @Nullable
    public BadgeAttribute getAttribute(Boost boost) {
        return BadgeAttributesReloadListener.getAttribute(item, boost);
    }

    public boolean containsBoost(Boost boost) {
        return BadgeAttributesReloadListener.containsAttribute(item, boost);
    }

}