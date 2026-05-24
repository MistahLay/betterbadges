package com.lay.betterbadges.league;

import net.minecraft.world.item.Item;

public class Badge {

    private final Item badgeItem;

    private final int posX;
    private final int posY;

    private final int slot;

    public Badge(Item item, int x, int y, int slot){
        this.badgeItem = item;
        this.posX = x;
        this.posY = y;
        this.slot = slot;
    }

    public Item getItem(){
        return this.badgeItem;
    }

    public int getX(){
        return this.posX;
    }

    public int getY(){
        return this.posY;
    }

    public int getSlot(){
        return this.slot;
    }

}
