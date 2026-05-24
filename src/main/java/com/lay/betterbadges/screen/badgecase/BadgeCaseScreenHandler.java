package com.lay.betterbadges.screen.badgecase;

import com.lay.betterbadges.screen.AbstractItemContainerMenu;
import com.lay.betterbadges.screen.ModScreenHandler;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BadgeCaseScreenHandler extends AbstractItemContainerMenu {
    public BadgeCaseScreenHandler(int i, Inventory inventory, SlotAccess slot) {
        super(ModScreenHandler.BADGE_CASE_GUI, i, inventory, slot);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }
}
