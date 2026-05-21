package com.lay.betterbadges.inventory;

import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.datagen.ModItemTagProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class BadgeContainer extends SimpleContainer {
    public static final int DEFAULT_SIZE = 8;

    private final String owner;

    BadgeContainer(String owner, int i){
        super(i <= 0 ? BadgeContainer.DEFAULT_SIZE : i);
        this.owner = owner;
    }

    BadgeContainer(String owner, ItemStack... items){
        super(items);
        this.owner = owner;
    }

    // Checks if it's a badge and contains the owner data
    @Override
    public boolean canAddItem(ItemStack itemStack) {
        if(!itemStack.is(ModItemTagProvider.BADGES)) return false;
        if(!Objects.equals(itemStack.getComponents().get(ModDataComponents.ITEM_OWNER), owner)) return false;
        return super.canAddItem(itemStack);
    }

}
