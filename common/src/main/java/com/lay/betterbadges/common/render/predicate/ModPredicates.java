package com.lay.betterbadges.common.render.predicate;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.player.Player;

public class ModPredicates {

    public static int FRAMES = 20;

    public static void init(){
        ItemProperties.register(ModItems.BOULDER_BADGE.get(), BetterBadges.of("frame"), (itemStack, clientLevel, livingEntity, i) -> {
            if (!(livingEntity instanceof Player player)) return 0.0f;
            if (player.getInventory().contains(itemStack)) return 0.0f;
            return 1.0f;
        });
    }

}
