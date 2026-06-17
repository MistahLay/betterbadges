package com.lay.betterbadges.common.api.league;

import com.lay.betterbadges.common.api.league.attributes.BadgeAttribute;
import com.lay.betterbadges.common.api.emblem.Boost;
import com.lay.betterbadges.common.item.badges.BadgeItem;
import com.lay.betterbadges.common.api.league.attributes.BadgeAttributesManager;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

// Slot Information
public final class BadgeSlot {
    private final DeferredSupplier<BadgeItem> itemSupplier;
    private final int x;
    private final int y;
    private final int slot;

    public BadgeSlot(DeferredSupplier<BadgeItem> itemSupplier, int x, int y, int slot) {
        this.itemSupplier = itemSupplier;
        this.x = x;
        this.y = y;
        this.slot = slot;
    }

    public BadgeItem item() {
        return this.itemSupplier.get();
    }

    @Nullable
    public BadgeAttribute getAttribute(Boost boost) {
        return BadgeAttributesManager.getAttribute(this.item(), boost);
    }

    public boolean containsBoost(Boost boost) {
        return BadgeAttributesManager.containsAttribute(this.item(), boost);
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public int slot() {
        return this.slot;
    }


}