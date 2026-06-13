package com.lay.betterbadges.common.event;

import com.lay.betterbadges.common.league.attributes.BadgeAttribute;
import com.lay.betterbadges.common.league.BadgeSlot;
import com.lay.betterbadges.common.league.League;
import com.lay.betterbadges.common.registry.ModRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
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
        for (League league : ModRegistries.LEAGUE) {
            for (BadgeSlot badgeSlot : league.getBadges()) if(badgeSlot.item() == item) {
                cache.put(item, badgeSlot);
                return badgeSlot;
            }
        }
        return null;
    }

    private static void applyBoostTooltip(BadgeAttribute attribute, List<Component> lines, ChatFormatting color){
    }

}
