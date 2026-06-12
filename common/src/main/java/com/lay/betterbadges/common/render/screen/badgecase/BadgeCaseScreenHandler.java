package com.lay.betterbadges.common.render.screen.badgecase;

import com.lay.betterbadges.common.component.ModDataComponents;
import com.lay.betterbadges.common.emblem.Emblem;
import com.lay.betterbadges.common.emblem.EmblemSlot;
import com.lay.betterbadges.common.emblem.EmblemTargetItem;
import com.lay.betterbadges.common.inventory.EmblemBadgesManager;
import com.lay.betterbadges.common.inventory.LeagueBadgesManager;
import com.lay.betterbadges.common.item.bounded.BoundItemWrapper;
import com.lay.betterbadges.common.item.cases.BadgeCaseWrapper;
import com.lay.betterbadges.common.league.BadgeSlot;
import com.lay.betterbadges.common.league.League;
import com.lay.betterbadges.common.mixin.SlotMixin;
import com.lay.betterbadges.common.render.screen.AbstractItemContainerMenu;
import com.lay.betterbadges.common.render.screen.ModScreens;
import com.lay.betterbadges.common.render.screen.Vector2d;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class BadgeCaseScreenHandler extends AbstractItemContainerMenu {

    private League currentLeague;
    private Emblem currentEmblem;

    @Nullable
    private Slot highlightedSlot = null;

    private final BadgeCaseWrapper badgeCase;
    private final LeagueBadgesManager leagueBadgesManager;
    private final EmblemBadgesManager emblemBadgesManager;

    // 8 League Badges 36 Player Inventory 20 Emblem Badges
    private final SimpleContainer dummyContainer = new SimpleContainer(32);

    public static final Vector2d BADGE_CONTAINER_POS = new Vector2d(0, 0);
    public static final Vector2d EMBLEM_CONTAINER_POS = new Vector2d(-103, 83);
    public static final Vector2d INVENTORY_CONTAINER_POS = new Vector2d(9, 103);

    public BadgeCaseScreenHandler(int i, Inventory inventory, SlotAccess slot) {
        super(ModScreens.BADGE_CASE_SCREEN_HANDLER.get(), i, inventory, slot);
        this.badgeCase = new BadgeCaseWrapper(slot.get());
        this.leagueBadgesManager = this.badgeCase.getLeagueInventoryManager(inventory.player.registryAccess());
        this.emblemBadgesManager = this.badgeCase.getEmblemInventoryManager();

        this.currentLeague = this.badgeCase.getCurrentLeague();

        this.currentEmblem = this.badgeCase.getCurrentEmblem();

        if(!this.emblemBadgesManager.containsEmblem(currentEmblem)) this.currentEmblem = null;

        this.createLeagueBadgesSlots();

        // Well it took me way too long to realize that player inventory is generated last :(
        this.createPlayerInventory(INVENTORY_CONTAINER_POS.x(), INVENTORY_CONTAINER_POS.y(), inventory.player);

        this.createEmblemBadgesSlots();
    }

    @Override
    public void clicked(int i, int j, ClickType clickType, Player player) {
        super.clicked(i, j, clickType, player);
        if(clickType == ClickType.QUICK_MOVE){
            if (i < 0 || i > this.getInventoryIndex() - 1) { // Slots from 0-7
                return;
            }
            Slot slot = this.slots.get(i);
            if(!(slot instanceof ScreenBadgeSlot) || this.highlightedSlot == null) return;

            ItemStack stack = slot.getItem();
            int target = this.currentLeague.getSlotForBadge(stack.getItem());

            if(target < 0) return;
            if(this.changeHighlightedSlot(stack, i)) this.highlightedSlot = null;
        } else if(clickType == ClickType.PICKUP){
            if (i < 0) {
                return;
            }
            Slot slot = this.slots.get(i);
            if (!(slot instanceof ScreenEmblemSlot emblemSlot)) return;
            if (emblemSlot.emblem == Emblem.EMPTY) return;
            if (this.highlightedSlot != null && this.highlightedSlot.index == slot.index) {
                this.highlightedSlot = null;
                return;
            }
            this.highlightedSlot = slot;
        }
    }

    public void setEmblem(Emblem emblem){
        if(emblem == Emblem.EMPTY) return;
        this.badgeCase.setCurrentEmblem(emblem);
        this.currentEmblem = emblem;
    }

    public Emblem getEmblem(){
        return this.currentEmblem;
    }

    private boolean isEmblemValid(Emblem emblem){
        return emblem != null && emblem != Emblem.EMPTY && this.emblemBadgesManager != null && this.emblemBadgesManager.containsEmblem(emblem) && emblem != this.currentEmblem;
    }

    public void switchEmblem(Emblem emblem){
        if(!isEmblemValid(emblem)) return;

        highlightedSlot = null;
        this.setEmblem(emblem);

        if(this.slots.size() >= this.getEmblemIndex() + 7) {
            for (int i = 0; i < Emblem.MAX_SLOTS; i++) {
                Slot slot = this.slots.get(i + this.getEmblemIndex());
                EmblemSlot emblemSlot = emblem.getSlot(i);

                if (!(slot instanceof ScreenEmblemSlot screenEmblemSlot)) continue;

                if (emblemSlot == null){
                    if (screenEmblemSlot.emblem == Emblem.EMPTY) break;
                    screenEmblemSlot.setEmblem(emblem);
                    ((SlotMixin) slot).betterbadges$setContainer(this.dummyContainer);
                    ((SlotMixin) slot).betterbadges$setSlot(i);
                    ((SlotMixin) slot).betterbadges$setX(1000000);
                    ((SlotMixin) slot).betterbadges$setY(1000000);
                    continue;
                }

                EmblemTargetItem target = this.emblemBadgesManager.getTarget(emblem, i);
                ((SlotMixin) slot).betterbadges$setX(EMBLEM_CONTAINER_POS.x() + emblemSlot.x());
                ((SlotMixin) slot).betterbadges$setY(EMBLEM_CONTAINER_POS.y() + emblemSlot.y());
                if (target == null || target == EmblemTargetItem.EMPTY) {
                    ((SlotMixin) slot).betterbadges$setContainer(this.dummyContainer);
                } else {
                    ((SlotMixin) slot).betterbadges$setSlot(target.targetSlot());
                    SimpleContainer targetContainer = this.leagueBadgesManager.getBadgeContainer(target.league());
                    ((SlotMixin) slot).betterbadges$setContainer(targetContainer);
                }
            }
        }
        else {
            this.createEmblemBadgesSlots();
        }

        this.broadcastChanges();
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
        if(this.badgeCase.getCurrentLeague() == league || league == League.EMPTY || league == null) return;
        SimpleContainer container = this.leagueBadgesManager.getBadgeContainer(league);
        List<BadgeSlot> badgeSlots = league.getBadges();

        SimpleContainer currentContainer = this.getCurrentLeagueContainer();

        this.setLeague(league);

        for (int i = 0; i < 8; i++){
            Slot slot = this.slots.get(i);
            if(slot.container == currentContainer) {
                BadgeSlot badgeSlot = badgeSlots.removeFirst();
                ((SlotMixin) slot).betterbadges$setX(BADGE_CONTAINER_POS.x() + badgeSlot.x());
                ((SlotMixin) slot).betterbadges$setY(BADGE_CONTAINER_POS.y() + badgeSlot.y());
                ((SlotMixin) slot).betterbadges$setContainer(container);
                ((ScreenBadgeSlot) slot).setWhitelist(badgeSlot.item());
            }
        }

        this.broadcastChanges();
    }

    public EmblemBadgesManager getEmblemBadgesManager(){
        return this.emblemBadgesManager;
    }

    public LeagueBadgesManager getLeagueBadgesManager(){
        return this.leagueBadgesManager;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {

        Slot slot = this.slots.get(index);

        if(!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack itemStack2 = slot.getItem();
        ItemStack itemStack = itemStack2.copy();

        int containerSize = this.getCurrentLeagueContainer().getContainerSize();
        int target = this.currentLeague.getSlotForBadge(itemStack2.getItem());

        if (target >= 0 && this.getCurrentLeagueContainer().getItem(target).isEmpty() && Objects.equals(new BoundItemWrapper(itemStack2).getItemOwner(), this.badgeCase.getItemOwner())){
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

    private boolean changeHighlightedSlot(ItemStack stack, int index){
        Slot slot = this.highlightedSlot;
        if(slot == null) return false;
        int actualSlotIndex = slot.index - this.getEmblemIndex();
        EmblemSlot emblemSlot = this.currentEmblem.getSlot(actualSlotIndex);

        if(!this.currentLeague.getRequiredBadgeAt(index).containsBoost(emblemSlot.category())) return false;
        if(ItemStack.isSameItemSameComponents(slot.getItem(), stack) && this.getCurrentLeagueContainer() == slot.container) return false;

        EmblemTargetItem targetItem = this.emblemBadgesManager.findTarget(this.currentLeague, index, this.currentEmblem, actualSlotIndex);

        if(targetItem != null) {
            Slot previousSlot = this.slots.get(this.getEmblemIndex() + targetItem.currentSlot());
            this.emblemBadgesManager.removeTarget(this.currentEmblem, targetItem.currentSlot());
            ((SlotMixin) previousSlot).betterbadges$setContainer(dummyContainer);
        }

        this.emblemBadgesManager.setTarget(this.currentEmblem, this.currentLeague, index, actualSlotIndex, true);
        ((SlotMixin) slot).betterbadges$setContainer(this.getCurrentLeagueContainer());
        ((SlotMixin) slot).betterbadges$setSlot(index);
        this.broadcastChanges();
        this.badgeCase.setEmblemInventoryManager(this.emblemBadgesManager);
        return true;
    }

    private void createLeagueBadgesSlots() {
        for (BadgeSlot badgeSlot : this.badgeCase.getCurrentLeague().getBadges()){
            this.addSlot(new ScreenBadgeSlot(
                    this.getCurrentLeagueContainer(),
                    badgeSlot.slot() ,
                    BADGE_CONTAINER_POS.x() + badgeSlot.x(),
                    BADGE_CONTAINER_POS.y() + badgeSlot.y(),
                    badgeSlot.item())
            );
        }
    }

    private void createEmblemBadgesSlots() {
        Emblem currentEmblem = this.badgeCase.getCurrentEmblem();
        if(currentEmblem == null || currentEmblem == Emblem.EMPTY) return;
        NonNullList<EmblemTargetItem> targets = this.emblemBadgesManager.getTargets(currentEmblem);
        this.currentEmblem = currentEmblem;
        if(targets == null) return;
        int totalSlots = currentEmblem.getTotalSlots();
        for (int i = 0; i < Emblem.MAX_SLOTS; i++) {
            if (i >= totalSlots) {
                this.addSlot(new ScreenEmblemSlot(this.dummyContainer, Emblem.EMPTY.get(), i, 1000000, 1000000));
                continue;
            }
            EmblemSlot emblemSlot = currentEmblem.getSlot(i);
            EmblemTargetItem target = targets.get(i);

            if(target == EmblemTargetItem.EMPTY) {
                this.addSlot(createEmblemBadgeSlot(emblemSlot, currentEmblem, i, this.dummyContainer));
                continue;
            }

            SimpleContainer associatedContainer = this.leagueBadgesManager.getBadgeContainer(target.league());

            this.addSlot(createEmblemBadgeSlot(emblemSlot, currentEmblem, target.targetSlot(), associatedContainer));
        }
    }

    public @Nullable Slot getHighlightedSlot(){
        return this.highlightedSlot;
    }

    private ScreenEmblemSlot createEmblemBadgeSlot(EmblemSlot emblemSlot, Emblem emblem, int slotIndex, SimpleContainer container){
        return new ScreenEmblemSlot(container, emblem, slotIndex, emblemSlot.x() + EMBLEM_CONTAINER_POS.x(), emblemSlot.y() + EMBLEM_CONTAINER_POS.y());
    }

    public int getEmblemIndex(){
        return this.getCurrentLeagueContainer().getContainerSize() + 36;
    }

    public int getInventoryIndex(){
        return this.getCurrentLeagueContainer().getContainerSize();
    }

    public SimpleContainer getCurrentLeagueContainer(){
        return this.leagueBadgesManager.getBadgeContainer(this.badgeCase.getCurrentLeague());
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

    public class ScreenBadgeSlot extends ItemUpdateSlot{

        private Item badge;

        public ScreenBadgeSlot(Container container, int i, int j, int k, Item badge) {
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

    public class ScreenEmblemSlot extends Slot {
        private Emblem emblem;
        public ScreenEmblemSlot(Container container, Emblem emblem, int i, int j, int k) {
            super(container, i, j, k);
            this.emblem = emblem;
        }

        @Override
        public void setChanged() {
            super.setChanged();
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

        @Override
        public boolean isActive() {
            return super.isActive();
        }

        public void setEmblem(Emblem emblem){
            this.emblem = emblem;
        }

        public Emblem getEmblem(){
            return this.emblem;
        }
    }

}
