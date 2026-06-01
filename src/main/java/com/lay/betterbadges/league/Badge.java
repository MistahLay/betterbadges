package com.lay.betterbadges.league;

import com.lay.betterbadges.emblem.BoostTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

// Probably a bad idea making this hard coded, oh well
public class Badge {
    private final Item item;
    private final int x;
    private final int y;
    private final int slot;
    private final Map<BoostTypes, BadgeAttribute> boosts;

    public static final Badge EMPTY = new Badge(Items.AIR, 0, 0, 0, new HashMap<>());

    public Badge(Item item, int x, int y, int slot, Map<BoostTypes, BadgeAttribute> boosts) {
        this.item = item;
        this.x = x;
        this.y = y;
        this.slot = slot;
        this.boosts = boosts;
    }

    @Nullable
    public BadgeAttribute getAttribute(BoostTypes type){
        return this.boosts.get(type);
    }

    public boolean containsBoost(BoostTypes type){
        return this.boosts.containsKey(type);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSlot() {
        return slot;
    }

    public Item getItem() {
        return item;
    }
}