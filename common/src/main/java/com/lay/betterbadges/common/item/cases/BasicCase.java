package com.lay.betterbadges.common.item.cases;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.component.ModDataComponents;
import com.lay.betterbadges.common.emblem.Emblem;
import com.lay.betterbadges.common.inventory.EmblemBadgesManager;
import com.lay.betterbadges.common.item.bounded.BoundItem;
import com.lay.betterbadges.common.registry.ModRegistries;
import com.lay.betterbadges.common.screen.badgecase.BadgeCaseScreenHandler;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BasicCase extends BoundItem {

    public BasicCase(Properties properties) {
        super(properties);
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack itemStack) {
        super.verifyComponentsAfterLoad(itemStack);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, InteractionHand interactionHand) {
        if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResultHolder.fail(player.getItemInHand(interactionHand));
        if(ModRegistries.LEAGUE.getIds().size() == 1) return InteractionResultHolder.fail(player.getItemInHand(interactionHand));

        EquipmentSlot slot = switch (interactionHand) {
            case MAIN_HAND -> EquipmentSlot.MAINHAND;
            case OFF_HAND -> EquipmentSlot.OFFHAND;
        };

        ItemStack stack = player.getItemInHand(interactionHand);

        BadgeCaseWrapper badgeCase = new BadgeCaseWrapper(stack);

        if (!badgeCase.canPlayerUse(player)) {
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(
                Component.translatable("actionbar.custom.not-owner", badgeCase.getItemOwnerName(serverPlayer.server)))
            );
        } else {
            if (slot == EquipmentSlot.MAINHAND) {
                ItemStack offHand = player.getOffhandItem();
                BetterBadges.LOGGER.info("in main hand");
                if (offHand != ItemStack.EMPTY) {
                    Emblem emblem = this.canConsumeEmblem(badgeCase, offHand, player);
                    BetterBadges.LOGGER.info("trying to consume");
                    if (emblem != null) {
                        BetterBadges.LOGGER.info("got to consume");
                        offHand.shrink(1);
                        EmblemBadgesManager emblemManager = badgeCase.getEmblemInventoryManager();
                        emblemManager.addEmblem(emblem);
                        badgeCase.setEmblemInventoryManager(emblemManager);
                        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
                    }
                }
            }
            if (!badgeCase.hasActiveLeague()) {
                serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("actionbar.custom.invalid-case")));
            } else {
                MenuRegistry.openExtendedMenu(serverPlayer, new ExtendedMenuProvider() {
                    @Override
                    public void saveExtraData(FriendlyByteBuf buf) {

                    }

                    @Override
                    public Component getDisplayName() {
                        return stack.getDisplayName();
                    }

                    @Override
                    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                        return new BadgeCaseScreenHandler(i, player.getInventory(), SlotAccess.forEquipmentSlot(player, slot));
                    }
                });
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    private @Nullable Emblem canConsumeEmblem(BadgeCaseWrapper badgeCase, ItemStack emblemItem, Player player){
        Emblem emblem = Emblem.getEmblemFromItem(emblemItem.getItem());
        EmblemBadgesManager emblemBadgesManager = badgeCase.getEmblemInventoryManager();
        if (emblemBadgesManager == null) {
            emblemBadgesManager = new EmblemBadgesManager(new HashMap<>());
            badgeCase.setEmblemInventoryManager(emblemBadgesManager);
        }
        if (Objects.equals(player.getUUID().toString(), emblemItem.get(ModDataComponents.ITEM_OWNER))
                && emblemBadgesManager.getTargets(emblem) == null) return emblem;
        return null;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        if(BetterBadges.SERVER != null) {
            BadgeCaseWrapper badgeCase = new BadgeCaseWrapper(itemStack);
            String ownerName = badgeCase.getItemOwnerName(BetterBadges.SERVER);
            list.add(Component.translatable("tooltip.betterbadges.owner").withStyle(ChatFormatting.RED).append(Component.literal(ownerName).withStyle(ChatFormatting.AQUA)));
        }
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
    }

}
