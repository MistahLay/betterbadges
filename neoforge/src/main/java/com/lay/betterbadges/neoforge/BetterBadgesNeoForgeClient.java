package com.lay.betterbadges.neoforge;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.BetterBadgesClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = BetterBadges.MOD_ID, value = Dist.CLIENT)
public class BetterBadgesNeoForgeClient {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        BetterBadgesClient.init();
    }

}
