package com.lay.betterbadges.common.render.screen;

import io.wispforest.owo.client.screens.SlotGenerator;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

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
