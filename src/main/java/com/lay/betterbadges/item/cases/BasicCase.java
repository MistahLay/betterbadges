package com.lay.betterbadges.item.cases;

import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.league.League;
import com.lay.betterbadges.screen.badgecase.BadgeCaseGui;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BasicCase extends Item {

    public BasicCase(Properties properties) {
        super(properties);
    }

    public static League getCurrentLeague(ItemStack itemStack) {
        return itemStack.get(ModDataComponents.CURRENT_LEAGUE);
    }

    public static String getItemOwner(ItemStack itemStack){
        return itemStack.get(ModDataComponents.ITEM_OWNER);
    }

    private static boolean isItemStackValid(ItemStack itemStack) {
        return (getItemOwner(itemStack) != null) && (getCurrentLeague(itemStack) != null);
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack itemStack) {
        super.verifyComponentsAfterLoad(itemStack);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        EquipmentSlot slot = switch (interactionHand) {
            case MAIN_HAND -> EquipmentSlot.MAINHAND;
            case OFF_HAND -> EquipmentSlot.OFFHAND;
        };
        ItemStack stack = player.getItemInHand(interactionHand);
        if(player instanceof ServerPlayer && isItemStackValid(stack)){
            player.openMenu(new ExtendedScreenHandlerFactory<EquipmentSlot>() {
                @Override
                public EquipmentSlot getScreenOpeningData(ServerPlayer player) {
                    return slot;
                }

                @Override
                public @NotNull Component getDisplayName() {
                    return stack.getDisplayName();
                }

                @Override
                public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                    return new BadgeCaseGui(i, player.getInventory(), SlotAccess.forEquipmentSlot(player, slot));
                }
            });
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

}
