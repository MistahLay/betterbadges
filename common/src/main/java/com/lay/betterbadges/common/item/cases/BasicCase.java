package com.lay.betterbadges.common.item.cases;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.emblem.Emblem;
import com.lay.betterbadges.common.inventory.EmblemBadgesManager;
import com.lay.betterbadges.common.inventory.LeagueBadgesManager;
import com.lay.betterbadges.common.item.bounded.BoundItem;
import com.lay.betterbadges.common.item.bounded.BoundItemWrapper;
import com.lay.betterbadges.common.league.ModLeagues;
import com.lay.betterbadges.common.registry.ModRegistries;
import com.lay.betterbadges.common.render.screen.badgecase.BadgeCaseScreenHandler;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
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
        boolean modified = false;

        if (!badgeCase.canUse(player, true)) {
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(
                    Component.translatable("actionbar.custom.not-owner", badgeCase.getItemOwnerName(serverPlayer.server)))
            );
            modified = true;
        }
        if (slot == EquipmentSlot.MAINHAND) {
            ItemStack offHand = player.getOffhandItem();
            if (!offHand.isEmpty() && consumeEmblem(badgeCase, offHand, player)) {
                offHand.shrink(1);
                modified = true;
            }
        }
        EmblemBadgesManager emblemBadgesManager = badgeCase.getEmblemInventoryManager();
        if (badgeCase.getCurrentEmblem() == null && emblemBadgesManager != null && emblemBadgesManager.getEmblems() != null) {
            badgeCase.setCurrentEmblem(emblemBadgesManager.getEmblems().getFirst(), false);
            modified = true;
        }
        if (badgeCase.getLeagueInventoryManager(serverPlayer.registryAccess()) == null) {
            badgeCase.setInventoryManager(LeagueBadgesManager.createEmpty(), serverPlayer.registryAccess());
            badgeCase.setCurrentLeague(ModLeagues.KANTO.get());
            modified = true;
        }
        if (badgeCase.getCurrentLeague() == null) {
            badgeCase.setCurrentLeague(ModLeagues.KANTO.get());
            modified = true;
        }
        if (modified) return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
        MenuRegistry.openExtendedMenu(serverPlayer, new ExtendedMenuProvider() {
            @Override
            public void saveExtraData(FriendlyByteBuf buf) {
                buf.writeJsonWithCodec(EquipmentSlot.CODEC, slot);
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
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    private boolean consumeEmblem(BadgeCaseWrapper badgeCase, ItemStack emblemItem, Player player){
        Emblem emblem = Emblem.getEmblemFromItem(emblemItem.getItem());
        EmblemBadgesManager emblemBadgesManager = badgeCase.getEmblemInventoryManager();
        BoundItemWrapper boundItem = new BoundItemWrapper(emblemItem);
        if (emblemBadgesManager == null) {
            emblemBadgesManager = new EmblemBadgesManager(new HashMap<>());
        }
        if (!boundItem.canUse(player)) return false;
        if (emblemBadgesManager.containsEmblem(emblem)) return false;
        emblemBadgesManager.addEmblem(emblem);
        badgeCase.setEmblemInventoryManager(emblemBadgesManager, false);
        badgeCase.setCurrentEmblem(emblem, false);
        return true;
    }

}
