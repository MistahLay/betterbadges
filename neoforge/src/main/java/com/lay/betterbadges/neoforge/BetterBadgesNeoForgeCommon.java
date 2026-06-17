package com.lay.betterbadges.neoforge;

import com.lay.betterbadges.common.BetterBadges;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

@EventBusSubscriber(modid = BetterBadges.MOD_ID)
public class BetterBadgesNeoForgeCommon {

    @SubscribeEvent
    public static void onEntityAttributeSetup(EntityAttributeModificationEvent event){ }

}
