package com.lay.betterbadges.item.bounded;

import com.lay.betterbadges.BetterBadges;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Objects;

public abstract class BoundItem extends Item {

    public BoundItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        BoundItemWrapper ownedItem = new BoundItemWrapper(stack);
        String ownerName = ownedItem.getItemOwnerName(BetterBadges.SERVER);
        if (Objects.equals(ownerName, "unknown")) return;
        tooltipComponents.add(Component.translatable("tooltip.betterbadges.owner").withStyle(ChatFormatting.LIGHT_PURPLE).append(Component.literal(ownerName).withStyle(ChatFormatting.YELLOW)));
    }


}
