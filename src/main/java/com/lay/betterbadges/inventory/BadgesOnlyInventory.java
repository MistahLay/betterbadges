package com.lay.betterbadges.inventory;

import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.datagen.ModItemTagProvider;
import com.lay.betterbadges.item.cases.BasicCase;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class BadgesOnlyInventory extends SimpleContainer {
    private static final int DEFAULT_SIZE = 8;

    private BasicCase badgeCase;

    // Checks if it's a badge and contains the owner data
    @Override
    public boolean canAddItem(ItemStack itemStack) {
        if(!itemStack.is(ModItemTagProvider.BADGES)) return false;
        if(!Objects.equals(itemStack.getComponents().get(ModDataComponents.ITEM_OWNER), badgeCase.getOriginalOwner().toString())) return false;
        return super.canAddItem(itemStack);
    }

}
