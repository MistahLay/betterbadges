package com.lay.betterbadges.fabric;

import com.lay.betterbadges.common.BetterBadges;
import net.fabricmc.api.ModInitializer;

public final class BetterBadgesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        BetterBadges.init();
    }

}
