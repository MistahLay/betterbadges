package com.lay.betterbadges.event;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.emblem.BoostTypes;
import com.lay.betterbadges.item.ModItems;
import com.lay.betterbadges.item.OwnedItemWrapper;
import com.lay.betterbadges.league.Badge;
import com.lay.betterbadges.league.BadgeAttribute;
import com.lay.betterbadges.league.League;
import com.lay.betterbadges.registry.ModRegistries;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ModEvents {

    private static Map<Item, Badge> cache = new HashMap<>();

    public static void initialize(){
        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> {
            if (stack.getItem() == ModItems.BADGE_CASE) return;
            if (BetterBadges.SERVER != null) {

                Badge badge = findBadge(stack.getItem());
                if (badge != null) {
                    BadgeAttribute adventure = badge.getAttribute(BoostTypes.ADVENTURE);
                    BadgeAttribute catching = badge.getAttribute(BoostTypes.SPAWNING);
                    BadgeAttribute spawning = badge.getAttribute(BoostTypes.CATCHING);

                    if (adventure != null) applyBoostTooltip(adventure, lines, ChatFormatting.RED);
                    if (catching != null) applyBoostTooltip(catching, lines, ChatFormatting.GREEN);
                    if (spawning != null) applyBoostTooltip(spawning, lines, ChatFormatting.BLUE);
                }

                OwnedItemWrapper ownedItem = new OwnedItemWrapper(stack);
                String ownerName = ownedItem.getItemOwnerName(BetterBadges.SERVER);
                if (Objects.equals(ownerName, "unknown")) return;
                lines.add(Component.translatable("tooltip.betterbadges.owner").withStyle(ChatFormatting.LIGHT_PURPLE).append(Component.literal(ownerName).withStyle(ChatFormatting.YELLOW)));

            }
        });
    }

    private static @Nullable Badge findBadge(Item item){
        if(cache.containsKey(item)) return cache.get(item);
        System.out.println("out of cache");
        for (League league : ModRegistries.LEAGUE) {
            for (Badge badge : league.getBadges()) if(badge.getItem() == item) {
                cache.put(item, badge);
                return badge;
            }
        }
        return null;
    }

    private static void applyBoostTooltip(BadgeAttribute attribute, List<Component> lines, ChatFormatting color){
        lines.add(Component.empty());
        lines.add(Component.translatable("tooltip.betterbadges.boost." + attribute.getBoostType().name().toLowerCase()).withStyle(color));
        AttributeModifier attributeModifier = attribute.createModifier();
        Holder<Attribute> holder = attribute.getAttribute().get();
        double d = attributeModifier.amount();

        double e;
        if (attributeModifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                || attributeModifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
            e = d * 100.0;
        } else if (holder.is(Attributes.KNOCKBACK_RESISTANCE)) {
            e = d * 10.0;
        } else {
            e = d;
        }

        if (d > 0.0) {
            lines.add(Component.translatable(
                            "attribute.modifier.plus." + attributeModifier.operation().id(),
                            ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(e),
                            Component.translatable(holder.value().getDescriptionId())
                    )
                    .withStyle(holder.value().getStyle(true)));
        } else if (d < 0.0) {
            lines.add(Component.translatable(
                            "attribute.modifier.take." + attributeModifier.operation().id(),
                            ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(-e),
                            Component.translatable(holder.value().getDescriptionId())
                    )
                    .withStyle(holder.value().getStyle(false)));
        }
        lines.add(Component.empty());
    }

}
