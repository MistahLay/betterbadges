package com.lay.betterbadges.event;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.emblem.Boost;
import com.lay.betterbadges.item.ModItems;
import com.lay.betterbadges.league.BadgeSlot;
import com.lay.betterbadges.league.BadgeAttribute;
import com.lay.betterbadges.league.League;
import com.lay.betterbadges.registry.ModRegistries;
import dev.architectury.event.events.client.ClientTooltipEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
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

public class ModEvents {

    private static Map<Item, BadgeSlot> cache = new HashMap<>();

    public static void initialize(){
//        ClientTooltipEvent.ITEM.register((stack, lines, tooltipContext, tooltipType) -> {
//            if (stack.getItem() == ModItems.BADGE_CASE) return;
//            if (BetterBadges.SERVER != null) {
//                BadgeSlot badgeSlot = findBadge(stack.getItem());
//                if (badgeSlot != null) {
//                    BadgeAttribute adventure = badgeSlot.getAttribute(Boost.ADVENTURE);
//                    BadgeAttribute catching = badgeSlot.getAttribute(Boost.SPAWNING);
//                    BadgeAttribute spawning = badgeSlot.getAttribute(Boost.CATCHING);
//
//                    if (adventure != null) applyBoostTooltip(adventure, lines, ChatFormatting.RED);
//                    if (catching != null) applyBoostTooltip(catching, lines, ChatFormatting.GREEN);
//                    if (spawning != null) applyBoostTooltip(spawning, lines, ChatFormatting.BLUE);
//                }
//            }
//        });
    }

    private static @Nullable BadgeSlot findBadge(Item item){
        if(cache.containsKey(item)) return cache.get(item);
        System.out.println("out of cache");
        for (League league : ModRegistries.LEAGUE) {
            for (BadgeSlot badgeSlot : league.getBadges()) if(badgeSlot.item() == item) {
                cache.put(item, badgeSlot);
                return badgeSlot;
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
