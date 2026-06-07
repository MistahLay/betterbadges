package com.lay.betterbadges.fabric;

import com.lay.betterbadges.common.BetterBadgesClient;
import net.fabricmc.api.ClientModInitializer;

public class BetterBadgesFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BetterBadgesClient.init();
    }
}
