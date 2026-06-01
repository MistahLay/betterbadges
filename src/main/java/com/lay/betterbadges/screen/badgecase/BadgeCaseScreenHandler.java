package com.lay.betterbadges.screen.badgecase;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.emblem.Emblem;
import com.lay.betterbadges.emblem.EmblemTargetItem;
import com.lay.betterbadges.inventory.EmblemBadgesManager;
import com.lay.betterbadges.inventory.LeagueBadgesManager;
import com.lay.betterbadges.item.cases.BadgeCaseWrapper;
import com.lay.betterbadges.league.Badge;
import com.lay.betterbadges.league.League;
import com.lay.betterbadges.mixin.SlotMixin;
import com.lay.betterbadges.screen.AbstractItemContainerMenu;
import com.lay.betterbadges.screen.ModScreenHandler;
import com.lay.betterbadges.screen.Vector2d;
import net.minecraft.core.NonNullList;
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
    private Emblem currentEmblem;
    private final BadgeCaseWrapper badgeCase;
    private final LeagueBadgesManager leagueBadgesManager;
    private final EmblemBadgesManager emblemBadgesManager;

    public final League initializedLeague;

    public static final Vector2d BADGE_CONTAINER_POS = new Vector2d(0, 0);
    public static final Vector2d EMBLEM_CONTAINER_POS = new Vector2d(100, 50);
    public static final Vector2d INVENTORY_CONTAINER_POS = new Vector2d(0, 0);

    public BadgeCaseScreenHandler(int i, Inventory inventory, SlotAccess slot) {
        super(ModScreenHandler.BADGE_CASE_SCREEN_HANDLER, i, inventory, slot);
        this.badgeCase = new BadgeCaseWrapper(slot.get());
        this.leagueBadgesManager = this.badgeCase.getLeagueInventoryManager(inventory.player.registryAccess());
        this.emblemBadgesManager = this.badgeCase.getEmblemInventoryManager();

        this.initializedLeague = this.badgeCase.getCurrentLeague();
        this.currentLeague = initializedLeague;
        this.currentEmblem = this.badgeCase.getCurrentEmblem();

        this.currentContainer = this.leagueBadgesManager.getBadgeContainer(this.badgeCase.getCurrentLeague());
        BetterBadges.LOGGER.info("{} : {}", currentContainer, leagueBadgesManager);

        this.createLeagueBadgesSlots();

        // Well it took me way too long to realize that player inventory is generated last :(
        this.createPlayerInventory(INVENTORY_CONTAINER_POS.x(), INVENTORY_CONTAINER_POS.y(), inventory.player);

        this.createEmblemBadgesSlots();
    }

    public void setLeague(League league){
        if(league == League.EMPTY) return;
        this.badgeCase.setCurrentLeague(league);
        this.currentLeague = league;
    }

    public League getLeague(){
        return this.currentLeague;
    }

    public void setEmblem(Emblem emblem){
        if(emblem == Emblem.EMPTY) return;
        this.badgeCase.setCurrentEmblem(emblem);
        this.currentEmblem = emblem;
    }

    public Emblem getEmblem(){
        return this.currentEmblem;
    }

    public void switchLeague(League league){
        if(this.badgeCase.getCurrentLeague() == league || league == League.EMPTY) return;
        SimpleContainer container = this.leagueBadgesManager.getBadgeContainer(league);
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

    public void switchEmblem(Emblem emblem){
        if(this.badgeCase.getCurrentEmblem() == emblem || emblem == Emblem.EMPTY || !emblemBadgesManager.targetExists(emblem)) return;

        for (int i = 0; i < 8; i++) {
            Slot slot = this.slots.get(i + this.getEmblemIndex());
            Emblem.EmblemSlot emblemSlot = emblem.getSlot(i);
            EmblemTargetItem target = this.emblemBadgesManager.getTarget(emblem, i);
            ((SlotMixin) slot).betterbadges$setX(EMBLEM_CONTAINER_POS.x() + emblemSlot.x());
            ((SlotMixin) slot).betterbadges$setY(EMBLEM_CONTAINER_POS.y() + emblemSlot.y());
            if(target == null){
                ((SlotMixin) slot).betterbadges$setContainer(new SimpleContainer(

                ));
            } else {
                SimpleContainer targetContainer = this.leagueBadgesManager.getBadgeContainer(target.league());
                ((SlotMixin) slot).betterbadges$setContainer(targetContainer);
            }
        }

        this.setEmblem(emblem);
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

    private void createLeagueBadgesSlots() {
        for (Badge badge : this.badgeCase.getCurrentLeague().getBadges()){
            this.addSlot(new BadgeSlot(
                    this.currentContainer,
                    badge.getSlot() ,
                    BADGE_CONTAINER_POS.x() + badge.getX(),
                    BADGE_CONTAINER_POS.y() + badge.getY(),
                    badge.getItem())
            );
        }
    }

    private void createEmblemBadgesSlots() {
        Emblem currentEmblem = this.badgeCase.getCurrentEmblem();
        if(currentEmblem == null || currentEmblem == Emblem.EMPTY) return;
        NonNullList<EmblemTargetItem> targets = this.emblemBadgesManager.getTargets(currentEmblem);
        System.out.println(this.emblemBadgesManager.getTargetItems());
        this.currentEmblem = currentEmblem;
        if(targets == null) return;
        for (int i = 0; i < currentEmblem.getTotalSlots(); i++) {
            Emblem.EmblemSlot emblemSlot = currentEmblem.getSlot(i);
            EmblemTargetItem target = targets.get(i);
            if(target == EmblemTargetItem.EMPTY) {
                this.addSlot(createEmblemBadgeSlot(emblemSlot, i, new SimpleContainer(16)));
                continue;
            }
            SimpleContainer associatedContainer = this.leagueBadgesManager.getBadgeContainer(this.currentLeague);
            this.addSlot(createEmblemBadgeSlot(emblemSlot, i, associatedContainer));
        }
    }

    private LockedSlot createEmblemBadgeSlot(Emblem.EmblemSlot emblemSlot, int slotIndex, SimpleContainer container){
        return new LockedSlot(container, slotIndex, emblemSlot.x() + EMBLEM_CONTAINER_POS.x(), emblemSlot.y() + EMBLEM_CONTAINER_POS.y());
    }

    private int getEmblemIndex(){
        return this.currentContainer.getContainerSize() + 36;
    }

    private int getInventoryIndex(){
        return this.currentContainer.getContainerSize();
    }

    private int getLeagueIndex(){
        return 0;
    }

    /**
     * Updates the BadgeCase's league inventory every slot update
     */
    public class ItemUpdateSlot extends Slot {
        public ItemUpdateSlot(Container container, int i, int j, int k) {
            super(container, i, j, k);
        }

        @Override
        public void setChanged() {
            super.setChanged();
            itemSlot.get().set(ModDataComponents.LEAGUE_INVENTORY_CONTENTS, leagueBadgesManager.serialize(player().registryAccess()));
        }
    }

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

    public class LockedSlot extends Slot {
        public LockedSlot(Container container, int i, int j, int k) {
            super(container, i, j, k);
        }

        @Override
        public boolean mayPlace(ItemStack itemStack) {
            return false;
        }

        @Override
        public boolean mayPickup(Player player) {
            return false;
        }

        @Override
        public boolean allowModification(Player player) {
            return false;
        }
    }

}
