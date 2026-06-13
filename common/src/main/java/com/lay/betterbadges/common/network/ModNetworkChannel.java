package com.lay.betterbadges.common.network;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.emblem.Boost;
import com.lay.betterbadges.common.item.badges.BadgeItem;
import com.lay.betterbadges.common.league.attributes.BadgeAttribute;
import com.lay.betterbadges.common.league.attributes.BadgeAttributeModifierSetting;
import com.lay.betterbadges.common.render.screen.badgecase.BadgeCaseScreenHandler;
import io.wispforest.owo.network.OwoNetChannel;
import io.wispforest.owo.serialization.CodecUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class ModNetworkChannel {

    public static final OwoNetChannel CHANNEL = OwoNetChannel.create(ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, "main"));

    public static void initialize(){
        CHANNEL.addEndecs(builder -> {
            builder.register(CodecUtils.toEndec(Boost.CODEC), Boost.class);
            builder.register(CodecUtils.toEndec(BadgeItem.CODEC), BadgeItem.class);
            builder.register(CodecUtils.toEndec(BadgeAttribute.CODEC.codec()), BadgeAttribute.class);
            builder.register(CodecUtils.toEndec(BadgeAttributeModifierSetting.CODEC.codec()), BadgeAttributeModifierSetting.class);
        });

        CHANNEL.registerServerbound(ChangeLeaguePacket.class,(message, access) -> {
            Player player = access.player();
            if (player.containerMenu instanceof BadgeCaseScreenHandler badgeCaseMenu && validateMenu(player)) {
                badgeCaseMenu.switchLeague(message.getRegisteredLeague());
            }
        });

        CHANNEL.registerServerbound(ChangeEmblemPacket.class, (message, access) -> {
            Player player = access.player();
            if (player.containerMenu instanceof BadgeCaseScreenHandler badgeCaseMenu && validateMenu(player)) {
                badgeCaseMenu.switchEmblem(message.getRegisteredEmblem());
            }
        });

        CHANNEL.registerClientbound(UpdateBadgeAttributesPacket.class, ((message, access) -> message.update()));

    }

    public static boolean validateMenu(Player player){
        if (!player.containerMenu.stillValid(player)) {
            BetterBadges.LOGGER.debug("Player {} interacted with invalid menu {}", player, player.containerMenu);
            return false;
        }
        return true;
    }
}
