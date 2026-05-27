package com.lay.betterbadges.screen.badgecase;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.inventory.LeagueBadgesManager;
import com.lay.betterbadges.item.cases.BadgeCaseWrapper;
import com.lay.betterbadges.league.Badge;
import com.lay.betterbadges.league.League;
import com.lay.betterbadges.mixin.SlotMixin;
import com.lay.betterbadges.screen.AbstractItemContainerMenu;
import com.lay.betterbadges.screen.ModScreenHandler;
import com.lay.betterbadges.screen.Vector2d;
import io.wispforest.owo.client.screens.ScreenUtils;
import io.wispforest.owo.mixin.ScreenHandlerInvoker;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BadgeCaseScreenHandler extends AbstractItemContainerMenu {

    private SimpleContainer currentContainer;
    private League currentLeague;
    private final BadgeCaseWrapper badgeCase;
    private final LeagueBadgesManager manager;
    public final League initializedLeague;

    public static final Vector2d BADGE_CONTAINER_POS = new Vector2d(0, 0);
    public static final Vector2d INVENTORY_CONTAINER_POS = new Vector2d(0, 0);

    public BadgeCaseScreenHandler(int i, Inventory inventory, SlotAccess slot) {
        super(ModScreenHandler.BADGE_CASE_SCREEN_HANDLER, i, inventory, slot);
        this.badgeCase = new BadgeCaseWrapper(slot.get());
        this.manager = this.badgeCase.getInventoryManager(inventory.player.registryAccess());

        this.initializedLeague = this.badgeCase.getCurrentLeague();
        this.currentLeague = initializedLeague;

        this.currentContainer = this.manager.getBadgeContainer(this.badgeCase.getCurrentLeague());
        BetterBadges.LOGGER.info("{} : {}", currentContainer, manager);

        this.createLeagueBadgesSlots();

        // Well it took me way too long to realize that player inventory is generated last :(
        this.createPlayerInventory(INVENTORY_CONTAINER_POS.x(), INVENTORY_CONTAINER_POS.y(), inventory.player);

    }

    public void setLeague(League league){
        if(league == League.EMPTY) return;
        this.badgeCase.setCurrentLeague(league);
        this.currentLeague = league;
    }

    public League getLeague(){
        return this.currentLeague;
    }

    public void switchLeague(League league){
        if(this.badgeCase.getCurrentLeague() == league || league == League.EMPTY) return;
        SimpleContainer container = this.manager.getBadgeContainer(league);
        List<Badge> badges = league.getBadges();

        for (Slot slot : this.slots){
            if(slot.container == this.currentContainer) {
                Badge badge = badges.removeFirst();
                ((SlotMixin) slot).betterbadges$setX(BADGE_CONTAINER_POS.x() + badge.getX());
                ((SlotMixin) slot).betterbadges$setY(BADGE_CONTAINER_POS.y() + badge.getY());
                ((SlotMixin) slot).betterbadges$setContainer(container);
                ((BadgeSlot) slot).setWhitelist(badge.getItem());
            }
        }

        this.currentContainer = container;

        this.setLeague(league);
        this.broadcastChanges();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {

        Slot slot = this.slots.get(index);

        if(!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack itemStack2 = slot.getItem();
        ItemStack itemStack = itemStack2.copy();

        int containerSize = this.currentContainer.getContainerSize();
        int target = this.currentLeague.getSlotForBadge(itemStack2.getItem());

        if (target >= 0 && this.currentContainer.getItem(target).isEmpty()){
            if(!this.moveItemStackTo(itemStack2, target, target + 1, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index >= containerSize && index < containerSize + 27) {
            if (!this.moveItemStackTo(itemStack2, containerSize + 27, containerSize + 36, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index >= containerSize + 27 && index < containerSize + 36) {
            if (!this.moveItemStackTo(itemStack2, containerSize, containerSize + 27, false)) {
                return ItemStack.EMPTY;
            }
        } else if (!this.moveItemStackTo(itemStack2, containerSize, containerSize + 36, false)) {
            return ItemStack.EMPTY;
        }

        if (itemStack2.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return itemStack;
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
    public class ItemUpdateSlot extends Slot {
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
    public class BadgeSlot extends ItemUpdateSlot{

        private Item badge;

        public BadgeSlot(Container container, int i, int j, int k, Item badge) {
            super(container, i, j, k);
            this.badge = badge;
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack itemStack) {
            return !(this.hasItem() && this.getItem().is(badge)) && itemStack.is(badge);
        }

        @Override
        public boolean mayPickup(@NotNull Player player) {
            return false;
        }

        public void setWhitelist(Item badge){
            this.badge = badge;
        }

    }

}
