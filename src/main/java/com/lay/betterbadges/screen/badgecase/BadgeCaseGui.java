package com.lay.betterbadges.screen.badgecase;

import com.lay.betterbadges.screen.ModScreenHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;

public class BadgeCaseGui {

    public static final ResourceLocation GUI_TEXTURE = ModScreenHandler.getGuiTexture("badgecase/kanto_badge_case");
    private static final int CENTRE_W = 256;
    private static final int CENTRE_H = 128;

//    private final ItemStack badgeCase;
//    private final LeagueBadgesManager leagueBadgesManager;
//    private League activeLeague;
//    private BadgeContainer currentContainer;
//    private final WBadgeCaseInventory badgeCaseInventory;

    public BadgeCaseGui(int i, Inventory playerInventory, SlotAccess slotAccess) {
//        super(ModScreenHandler.BADGE_CASE_GUI, i, playerInventory, slotAccess);
//
//        // Object Info
//        this.badgeCase = slotAccess.get();
//        this.leagueBadgesManager = LeagueBadgesManager.createFromItem(this.badgeCase, playerInventory.player);
//        if(this.leagueBadgesManager == null){
//            this.removed(this.playerInventory.player);
//        }
//        this.activeLeague = BasicCase.getCurrentLeague(this.badgeCase);
//        this.currentContainer = this.leagueBadgesManager.getBadgeContainer(activeLeague);
//
//        // Root Panel
//        WGridPanel root = new WGridPanel();
//        this.addPainters();
//        this.setTitleVisible(false);
//        root.setInsets(Insets.ROOT_PANEL);
//        this.setRootPanel(root);
//
//        WPlainPanel badgeCasePanel = new WPlainPanel();
//
//        // Top of hierarchy so the slots are in their correct keys aka inventory slots is between 0 - 35 like it usually is
//        WPlayerInvPanel playerInvPanel = new WLockInventory(playerInventory);
//        root.add(playerInvPanel, 0, 6);
//
//        WBadgeCaseInventory badgeCaseInventory = this.badgeCaseInventory = new WBadgeCaseInventory();
//        badgeCaseInventory.setSlotsForContainer(this.currentContainer, this.activeLeague);
//        badgeCasePanel.add(new WTiledSprite(256, 256, GUI_TEXTURE), 4, 11);
//        badgeCasePanel.add(badgeCaseInventory, 4, 11);
//        root.add(badgeCasePanel, -1, -2);
//        root.validate(this);
//    }
//
//    @Override
//    public ItemStack quickMoveStack(Player player, int index) {
//        ItemStack result = ItemStack.EMPTY;
//        Slot slot = slots.get(index);
//
//        if (slot.hasItem()) {
//            ItemStack slotStack = slot.getItem();
//            result = slotStack.copy();
//
//            // Current Container is current badge case container
//            if (this.currentContainer!=null) {
//                if (slot.container==this.currentContainer) {
//                    // TODO: Add quick move implementation for trophy badge system
//                    BetterBadges.LOGGER.info("Within Badge Container");
//                    return ItemStack.EMPTY;
//                } else if(slotStack.is(ModItemTagProvider.BADGES)) {
//                    BetterBadges.LOGGER.info("Is Badge");
//                    // If the item does not have the badge tag and the correct league, it just swaps between the player's storage and their hotbar
//                    int targetBadgeIndex = this.activeLeague.getSlotForBadge(result.getItem());
//                    if(targetBadgeIndex < 0) { // The item does not match the league
//                        BetterBadges.LOGGER.info("Just swaps if it wasn't within league");
//                        if (!swapHotbar(slotStack, index, this.playerInventory, player)) {
//                            return ItemStack.EMPTY;
//                        }
//                    } else if(!this.insertBadge(slotStack, targetBadgeIndex)){
//                        return ItemStack.EMPTY;
//                    }
//                }
//            } else {
//                //There's no badge case container, just swap between the player's storage and their hotbar
//                if (!swapHotbar(slotStack, index, this.playerInventory, player)) {
//                    BetterBadges.LOGGER.info("Not in badgecase");
//                    return ItemStack.EMPTY;
//                }
//            }
//
//            if (slotStack.isEmpty()) {
//                slot.setByPlayer(ItemStack.EMPTY);
//            } else {
//                slot.setChanged();
//            }
//        }
//
//        return result;
//    }
//
//    private boolean swapHotbar(ItemStack toInsert, int slotNumber, Container inventory, Player player) {
//        //Feel out the slots to see what's storage versus hotbar
//        ArrayList<Slot> storageSlots = new ArrayList<>();
//        ArrayList<Slot> hotbarSlots = new ArrayList<>();
//        boolean swapToStorage = true;
//        boolean inserted = false;
//
//        for(Slot slot : slots) {
//            if (slot.container == inventory && slot instanceof ValidatedSlot validated) {
//                int index = validated.getInventoryIndex();
//                if (Inventory.isHotbarSlot(index)) {
//                    hotbarSlots.add(slot);
//                } else {
//                    storageSlots.add(slot);
//                    if (slot.index==slotNumber) swapToStorage = false;
//                }
//            }
//        }
//        if (storageSlots.isEmpty() || hotbarSlots.isEmpty()) return false;
//
//        if (swapToStorage) {
//            //swap from hotbar to storage
//            for(int i=0; i<storageSlots.size(); i++) {
//                Slot curSlot = storageSlots.get(i);
//                if (insertIntoExisting(toInsert, curSlot)) inserted = true;
//                if (toInsert.isEmpty()) break;
//            }
//            if (!toInsert.isEmpty()) {
//                for(int i=0; i<storageSlots.size(); i++) {
//                    Slot curSlot = storageSlots.get(i);
//                    if (insertIntoEmpty(toInsert, curSlot)) inserted = true;
//                    if (toInsert.isEmpty()) break;
//                }
//            }
//        } else {
//            //swap from storage to hotbar
//            for(int i=0; i<hotbarSlots.size(); i++) {
//                Slot curSlot = hotbarSlots.get(i);
//                if (insertIntoExisting(toInsert, curSlot)) inserted = true;
//                if (toInsert.isEmpty()) break;
//            }
//            if (!toInsert.isEmpty()) {
//                for(int i=0; i<hotbarSlots.size(); i++) {
//                    Slot curSlot = hotbarSlots.get(i);
//                    if (insertIntoEmpty(toInsert, curSlot)) inserted = true;
//                    if (toInsert.isEmpty()) break;
//                }
//            }
//        }
//
//        return inserted;
//    }
//
//    private boolean insertBadge(ItemStack toInsert, int targetSlot) {
//        Slot slot = this.slots.get(targetSlot + 36);
//        if(slot == null || slot.hasItem()) {
//            return false;
//        }
//        insertIntoEmpty(toInsert, slot);
//        return true;
//    }
//
//    private boolean insertIntoEmpty(ItemStack toInsert, Slot slot) {
//        ItemStack curSlotStack = slot.getItem();
//        if (curSlotStack.isEmpty() && slot.mayPlace(toInsert)) {
//            if (toInsert.getCount() > slot.getMaxStackSize(toInsert)) {
//                slot.setByPlayer(toInsert.split(slot.getMaxStackSize(toInsert)));
//            } else {
//                slot.setByPlayer(toInsert.split(toInsert.getCount()));
//            }
//
//            slot.setChanged();
//            return true;
//        }
//
//        return false;
//    }
//
//    private boolean insertIntoExisting(ItemStack toInsert, Slot slot) {
//        ItemStack curSlotStack = slot.getItem();
//        if (!curSlotStack.isEmpty() && ItemStack.isSameItemSameComponents(toInsert, curSlotStack) && slot.mayPlace(toInsert)) {
//            int combinedAmount = curSlotStack.getCount() + toInsert.getCount();
//            int maxAmount = Math.min(toInsert.getMaxStackSize(), slot.getMaxStackSize(toInsert));
//            if (combinedAmount <= maxAmount) {
//                toInsert.setCount(0);
//                curSlotStack.setCount(combinedAmount);
//                slot.setChanged();
//                return true;
//            } else if (curSlotStack.getCount() < maxAmount) {
//                toInsert.shrink(maxAmount - curSlotStack.getCount());
//                curSlotStack.setCount(maxAmount);
//                slot.setChanged();
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private ArrayList<Slot> getSlotsOfContainer(Container container){
//        ArrayList<Slot> associatedSlots = new ArrayList<>();
//        for (Slot slot : slots){
//            if(slot.container == container) associatedSlots.add(slot);
//        }
//        return associatedSlots;
//    }
//
//    private void getSlotsOfContainers(ArrayList<Slot> fromOutput, ArrayList<Slot> toOutput, Container fromContainer, Container toContainer){
//        for (Slot slot : slots){
//            if(slot.container == fromContainer) fromOutput.add(slot);
//            if(slot.container == toContainer) toOutput.add(slot);
//        }
//    }
//
//    @Override
//    public boolean stillValid(Player entity) {
//        return ItemStack.isSameItem(ownerStack, owner.get());
//    }
//
//    @Override
//    public void addPainters() {}
//
//    public void changeBadgeInventory(BadgeContainer container){
//        this.currentContainer = container;
//        for (int i = 36; i < 44; i++){
//            Slot previousSlot = this.slots.get(i);
//            this.slots.set(i, new Slot(this.currentContainer, previousSlot.index, previousSlot.x, previousSlot.y));
//        }
//        this.broadcastChanges();
//    }
//
//    private class WBadgeCaseInventory extends WPlainPanel {
//
//        public void setSlotsForContainer(BadgeContainer container, League league){
//            children.clear();
//            setSlot(container, 0, 46, 74, league);
//            setSlot(container, 1, 74, 74, league);
//            setSlot(container, 2, 98, 74, league);
//            setSlot(container, 3, 126, 74, league);
//            setSlot(container, 4, 38, 97, league);
//            setSlot(container, 5, 70, 97, league);
//            setSlot(container, 6, 102, 97, league);
//            setSlot(container, 7, 134, 97, league);
//        }
//
//        private void setSlot(BadgeContainer container, int index, int x, int y, League league){
//            setSlot(container, index, x, y, league.getRequiredBadgeAt(index));
//        }
//
//        private void setSlot(BadgeContainer container, int index, int x, int y, Item filter){
//            WItemSlot itemSlot = new WItemSlot(container, index, 1, 1, false){
//                @Override
//                public void addPainters() {
//                    setBackgroundPainter(null);
//                }
//            };
//            itemSlot.addChangeListener(new WItemSlot.ChangeListener() {
//                @Override
//                public void onStackChanged(WItemSlot slot, Container inventory, int index, ItemStack stack) {
//
//                    owner.get().set(ModDataComponents.LEAGUE_INVENTORY_CONTENTS, leagueBadgesManager.getLeagueInventoryContents(playerInventory.player));
//                }
//            });
//            itemSlot.setInputFilter(stack -> stack.is(filter));
//            itemSlot.setBackgroundPainter(null);
//            this.add(itemSlot, x, y);
//        }
//
//    }
//
//    private class WLockInventory extends WPlayerInvPanel {
//
//        private final WItemSlot newHotbar;
//        private int originalIndex;
//
//        public WLockInventory(Inventory playerInventory) {
//            super(playerInventory, false);
//            newHotbar = new WItemSlot(playerInventory, 0, 9, 1, false) {
//                @Override
//                protected Component getNarrationName() {
//                    return NarrationMessages.Vanilla.HOTBAR;
//                }
//            };
//            this.children.remove(1);
//            this.add(newHotbar, 0, y + 58);
//        }
//
//        @Override
//        public void validate(GuiDescription c) {
//            super.validate(c);
//            int index = playerInventory.findSlotMatchingItem(badgeCase);
//            if(index < 0) index = this.originalIndex;
//            List<ValidatedSlot> peers = ((WItemSlotMixin) this.newHotbar).modId$peers();
//            BetterBadges.LOGGER.info("{}", index);
//            ValidatedSlot targetSlot = peers.get(index);
//            targetSlot.setTakingAllowed(false);
//            targetSlot.setInsertingAllowed(false);
//        }
//
//        @Override
//        public void addPainters() {
//            super.addPainters();
//            this.setBackgroundPainter(null);
//        }
//
//        @Environment(EnvType.CLIENT)
//        @Override
//        public WPanel setBackgroundPainter(BackgroundPainter painter) {
//            super.setBackgroundPainter(null);
//            inventory.setBackgroundPainter(painter);
//            newHotbar.setBackgroundPainter(painter);
//            return this;
//        }
//    }

}}
