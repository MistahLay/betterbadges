package com.lay.betterbadges.item;

import net.minecraft.world.item.ItemStack;

public abstract class ItemStackWrapper {

    protected final ItemStack item;

    public ItemStackWrapper(ItemStack item) {
        this.item = item;
    }

}
