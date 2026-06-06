package com.lay.betterbadges;

import net.fabricmc.api.ClientModInitializer;

public class BetterBadgesFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BetterBadgesClient.init();
    }
}
