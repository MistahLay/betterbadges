package com.lay.betterbadges.item.badges;

import net.minecraft.world.item.Item;

public class BasicBadge extends Item {

    private int badge_slot;

    public BasicBadge(Properties properties, int badge_slot) {
        super(properties);
        this.badge_slot = badge_slot;
    }

    public int getCaseSlot(){
        return this.badge_slot;
    }
}
