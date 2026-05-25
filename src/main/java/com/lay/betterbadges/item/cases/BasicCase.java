package com.lay.betterbadges.item.cases;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.league.League;
import com.lay.betterbadges.screen.badgecase.BadgeCaseScreenHandler;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class BasicCase extends Item {

    public BasicCase(Properties properties) {
        super(properties);
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack itemStack) {
        super.verifyComponentsAfterLoad(itemStack);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, InteractionHand interactionHand) {
        if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResultHolder.pass(player.getItemInHand(interactionHand));

        EquipmentSlot slot = switch (interactionHand) {
            case MAIN_HAND -> EquipmentSlot.MAINHAND;
            case OFF_HAND -> EquipmentSlot.OFFHAND;
        };

        ItemStack stack = player.getItemInHand(interactionHand);

        BadgeCaseWrapper badgeCase = new BadgeCaseWrapper(stack);

        if (!badgeCase.canPlayerUse(player)) {
            GameProfileCache cache = serverPlayer.server.getProfileCache();
            if (cache != null) {
                String owner = badgeCase.getItemOwner();
                if (owner == null || owner.length() != 33) {
                    serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(
                            Component.translatable("actionbar.custom.not-owner", "unknown")
                    ));
                } else {
                    Optional<GameProfile> profile = cache.get(UUID.fromString(owner));
                    serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(
                            Component.translatable("actionbar.custom.not-owner", profile.isPresent() ? profile.get().getName() : "unknown"))
                    );
                }
            }
        } else if (!badgeCase.hasActiveLeague()) {
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("actionbar.custom.invalid-case")));
        } else {
            serverPlayer.openMenu(new ExtendedScreenHandlerFactory<EquipmentSlot>() {
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
                    return new BadgeCaseScreenHandler(i, player.getInventory(), SlotAccess.forEquipmentSlot(player, slot));
                }
            });
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

}
