package com.lay.betterbadges.screen;

import io.github.cottonmc.cotton.gui.ValidatedSlot;
import io.wispforest.owo.client.screens.SlotGenerator;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
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

    protected boolean swapHotbar(ItemStack toInsert, int slotNumber, Container inventory) {
        //Feel out the slots to see what's storage versus hotbar
        ArrayList<Slot> storageSlots = new ArrayList<>();
        ArrayList<Slot> hotbarSlots = new ArrayList<>();
        boolean swapToStorage = true;
        boolean inserted = false;

        for(Slot slot : slots) {
            if (slot.container == inventory) {
                int index = slot.getContainerSlot();
                if (Inventory.isHotbarSlot(index)) {
                    hotbarSlots.add(slot);
                } else {
                    storageSlots.add(slot);
                    if (slot.index==slotNumber) swapToStorage = false;
                }
            }
        }
        if (storageSlots.isEmpty() || hotbarSlots.isEmpty()) return false;

        if (swapToStorage) {
            //swap from hotbar to storage
            for (Slot curSlot : storageSlots) {
                if (insertIntoExisting(toInsert, curSlot)) inserted = true;
                if (toInsert.isEmpty()) break;
            }
            if (!toInsert.isEmpty()) {
                for (Slot curSlot : storageSlots) {
                    if (insertIntoEmpty(toInsert, curSlot)) inserted = true;
                    if (toInsert.isEmpty()) break;
                }
            }
        } else {
            //swap from storage to hotbar
            for (Slot curSlot : hotbarSlots) {
                if (insertIntoExisting(toInsert, curSlot)) inserted = true;
                if (toInsert.isEmpty()) break;
            }
            if (!toInsert.isEmpty()) {
                for (Slot curSlot : hotbarSlots) {
                    if (insertIntoEmpty(toInsert, curSlot)) inserted = true;
                    if (toInsert.isEmpty()) break;
                }
            }
        }

        return inserted;
    }

    protected boolean insertIntoEmpty(ItemStack toInsert, Slot slot) {
        ItemStack curSlotStack = slot.getItem();
        if (curSlotStack.isEmpty() && slot.mayPlace(toInsert)) {
            if (toInsert.getCount() > slot.getMaxStackSize(toInsert)) {
                slot.setByPlayer(toInsert.split(slot.getMaxStackSize(toInsert)));
            } else {
                slot.setByPlayer(toInsert.split(toInsert.getCount()));
            }

            slot.setChanged();
            return true;
        }

        return false;
    }

    protected boolean insertIntoExisting(ItemStack toInsert, Slot slot) {
        ItemStack curSlotStack = slot.getItem();
        if (!curSlotStack.isEmpty() && ItemStack.isSameItemSameComponents(toInsert, curSlotStack) && slot.mayPlace(toInsert)) {
            int combinedAmount = curSlotStack.getCount() + toInsert.getCount();
            int maxAmount = Math.min(toInsert.getMaxStackSize(), slot.getMaxStackSize(toInsert));
            if (combinedAmount <= maxAmount) {
                toInsert.setCount(0);
                curSlotStack.setCount(combinedAmount);
                slot.setChanged();
                return true;
            } else if (curSlotStack.getCount() < maxAmount) {
                toInsert.shrink(maxAmount - curSlotStack.getCount());
                curSlotStack.setCount(maxAmount);
                slot.setChanged();
                return true;
            }
        }
        return false;
    }


    /**
     * Closes only if the item instance changes
     * TODO: Secure this further if possible
     */
    @Override
    public boolean stillValid(Player entity) {
        return ItemStack.isSameItem(itemStack, itemSlot.get());
    }

    protected void createPlayerInventory(int x, int y, Player player){
        SlotGenerator
                .begin(this::addSlot, x, y)
                .grid(playerInventory, 9, 9, 3);

        int slotIndex = playerInventory.selected;
        boolean canLock = ItemStack.isSameItem(itemStack, player.getItemInHand(InteractionHand.MAIN_HAND));

        for (int column = 0; column < 9; column++) {
            // Overrides the item's slot to not be moveable on the hotbar
            // Does not work if the item slot came from the off-hand
            if(canLock && slotIndex == column) this.addSlot(new Slot(playerInventory, column, x + column * 18, y + 58){
                @Override
                public boolean allowModification(Player player) {
                    return false;
                }

                @Override
                public boolean mayPickup(Player player) {
                    return false;
                }

                @Override
                public boolean mayPlace(ItemStack itemStack) {
                    return false;
                }
            });
            else this.addSlot(new Slot(playerInventory, column, x + column * 18, y + 58));
        }

    }

    public ItemStack getOriginalStack(){
        return this.itemStack.copy();
    }
}
