package com.lay.betterbadges.common.item;

import net.minecraft.world.item.ItemStack;

public abstract class ItemStackWrapper {

    protected final ItemStack item;

    public ItemStackWrapper(ItemStack item) {
        this.item = item;
    }

    public ItemStack getStack(){
        return item.copy();
    }

    public ItemStack getSameStack(){
        return item.copy();
    }

}
