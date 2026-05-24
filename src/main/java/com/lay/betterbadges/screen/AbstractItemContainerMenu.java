package com.lay.betterbadges.screen;

import io.github.cottonmc.cotton.gui.ValidatedSlot;
import io.wispforest.owo.client.screens.SlotGenerator;
import net.minecraft.world.Container;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Opens an inventory menu that references an ItemStack
 */
public abstract class AbstractItemContainerMenu extends AbstractContainerMenu {

    protected final SlotAccess itemSlot;
    protected final Inventory playerInventory;
    protected final ItemStack itemStack;

    protected AbstractItemContainerMenu(@Nullable MenuType<?> menuType, int i, Inventory inventory, SlotAccess slot) {
        super(menuType, i);
        this.playerInventory = inventory;
        this.itemSlot = slot;
        this.itemStack = slot.get().copy();
    }

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

    /**
     * Closes only if the item instance changes
     * TODO: Secure this further if possible... Can't do
     */
    @Override
    public boolean stillValid(Player entity) {
        return ItemStack.isSameItem(itemStack, itemSlot.get());
    }

    protected void createPlayerInventory(int x, int y){
        SlotGenerator
                .begin(this::addSlot, x, y)
                .playerInventory(this.playerInventory);
    }

    public ItemStack getOriginalStack(){
        return this.itemStack.copy();
    }

}
