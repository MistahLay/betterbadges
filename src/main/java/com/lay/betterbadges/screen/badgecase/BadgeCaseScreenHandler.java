package com.lay.betterbadges.screen.badgecase;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.inventory.LeagueBadgesManager;
import com.lay.betterbadges.item.cases.BadgeCaseWrapper;
import com.lay.betterbadges.league.Badge;
import com.lay.betterbadges.screen.AbstractItemContainerMenu;
import com.lay.betterbadges.screen.ModScreenHandler;
import com.lay.betterbadges.screen.Vector2d;
import io.github.cottonmc.cotton.gui.widget.data.Vec2i;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BadgeCaseScreenHandler extends AbstractItemContainerMenu {

    private SimpleContainer currentContainer;
    private final BadgeCaseWrapper badgeCase;
    private final LeagueBadgesManager manager;

    public static final Vector2d BADGE_CONTAINER_POS = new Vector2d(0, 0);
    public static final Vector2d INVENTORY_CONTAINER_POS = new Vector2d(0, 0);

    public BadgeCaseScreenHandler(int i, Inventory inventory, SlotAccess slot) {
        super(ModScreenHandler.BADGE_CASE_SCREEN_HANDLER, i, inventory, slot);
        this.badgeCase = new BadgeCaseWrapper(slot.get());
        this.manager = this.badgeCase.getInventoryManager(inventory.player.registryAccess());

        this.currentContainer = this.manager.getBadgeContainer(this.badgeCase.getCurrentLeague());
        BetterBadges.LOGGER.info("{} : {}", currentContainer, manager);

        // This goes first ofc, so the slots are from 0-35
        this.createPlayerInventory(INVENTORY_CONTAINER_POS.x(), INVENTORY_CONTAINER_POS.y(), inventory.player);

        this.createLeagueBadgesSlots();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            result = slotStack.copy();

            // Current Container is current badge case container
            if (this.currentContainer!=null) {
                if (slot.container==this.currentContainer) {
                    // TODO: Add quick move implementation for trophy badge system
                    BetterBadges.LOGGER.info("Within Badge Container");
                    return ItemStack.EMPTY;
                } else {
                    // If the item does not have the badge tag and the correct league, it just swaps between the player's storage and their hotbar
                    int targetBadgeIndex = this.badgeCase.getCurrentLeague().getSlotForBadge(result.getItem());
                    if(targetBadgeIndex < 0) { // The item does not match the league
                        BetterBadges.LOGGER.info("Just swaps if it wasn't within league");
                        if (!swapHotbar(slotStack, index, this.playerInventory)) {
                            return ItemStack.EMPTY;
                        }
                    } else if(!this.insertBadge(slotStack, targetBadgeIndex)){
                        return ItemStack.EMPTY;
                    }
                }
            } else {
                //There's no badge case container, just swap between the player's storage and their hotbar
                if (!swapHotbar(slotStack, index, this.playerInventory)) {
                    BetterBadges.LOGGER.info("Not in badgecase");
                    return ItemStack.EMPTY;
                }
            }

            if (slotStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return result;
    }

    private boolean insertBadge(ItemStack toInsert, int targetSlot) {
        Slot slot = this.slots.get(targetSlot + 36);
        if(slot.hasItem()) {
            return false;
        }
        insertIntoEmpty(toInsert, slot);
        return true;
    }

    private void createLeagueBadgesSlots(){
        for (Badge badge : this.badgeCase.getCurrentLeague().getBadges()){
            this.addSlot(new BadgeSlot(
                    this.currentContainer,
                    badge.getSlot() ,
                    BADGE_CONTAINER_POS.x() + badge.getX(),
                    BADGE_CONTAINER_POS.y() +badge.getY(),
                    badge.getItem())
            );
        }
    }

    /**
     * Updates the BadgeCase inventory every update
     */
    private class ItemUpdateSlot extends Slot {
        public ItemUpdateSlot(Container container, int i, int j, int k) {
            super(container, i, j, k);
        }

        @Override
        public void setChanged() {
            super.setChanged();
            itemSlot.get().set(ModDataComponents.LEAGUE_INVENTORY_CONTENTS, manager.getLeagueInventoryContents(player().registryAccess()));
        }
    }

    /**
     * Ironically, it's not actually a list
     */
    private class BadgeSlot extends ItemUpdateSlot{

        private final Item whitelist;

        public BadgeSlot(Container container, int i, int j, int k, Item whitelist) {
            super(container, i, j, k);
            this.whitelist = whitelist;
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack itemStack) {
            return !(this.hasItem() && this.getItem().is(whitelist)) && itemStack.is(whitelist);
        }

        @Override
        public boolean mayPickup(@NotNull Player player) {
            return !this.getItem().is(whitelist);
        }

    }

}
