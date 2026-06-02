package com.lay.betterbadges.event;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.item.ModItems;
import com.lay.betterbadges.item.OwnedItemWrapper;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.Objects;

public class ModEvents {

    public static void initialize(){
        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> {
            if(stack.getItem() == ModItems.BADGE_CASE) return;
            if(BetterBadges.SERVER != null) {
                OwnedItemWrapper badge = new OwnedItemWrapper(stack);
                String ownerName = badge.getItemOwnerName(BetterBadges.SERVER);
                if(Objects.equals(ownerName, "unknown")) return;
                lines.add(Component.translatable("tooltip.betterbadges.owner").withStyle(ChatFormatting.RED).append(Component.literal(ownerName).withStyle(ChatFormatting.AQUA)));
            }
        });
    }

}
