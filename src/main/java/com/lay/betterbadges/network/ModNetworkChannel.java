package com.lay.betterbadges.network;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.screen.badgecase.BadgeCaseScreenHandler;
import io.wispforest.owo.network.OwoNetChannel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class ModNetworkChannel {

    public static final OwoNetChannel CHANNEL = OwoNetChannel.create(ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, "main"));

    public static void initialize(){
        CHANNEL.registerServerbound(ChangeLeaguePacket.class, ((message, access) -> {
            Player player = access.player();
            if (player.containerMenu instanceof BadgeCaseScreenHandler badgeCaseMenu && validateMenu(player)) {
                badgeCaseMenu.switchLeague(message.getRegisteredLeague());
            }
        }));
    }

    public static boolean validateMenu(Player player){
        if (!player.containerMenu.stillValid(player)) {
            BetterBadges.LOGGER.debug("Player {} interacted with invalid menu {}", player, player.containerMenu);
            return false;
        }
        return true;
    }
}
