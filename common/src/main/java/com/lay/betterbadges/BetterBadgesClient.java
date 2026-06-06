package com.lay.betterbadges;

import com.lay.betterbadges.screen.ModScreens;
import com.lay.betterbadges.screen.badgecase.BadgeCaseScreen;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;

public final class BetterBadgesClient {

    public static void init() {
        if(Platform.isFabric()) {
            ClientLifecycleEvent.CLIENT_STARTED.register(client -> {
                MenuScreens.register(ModScreens.BADGE_CASE_SCREEN_HANDLER.get(), BadgeCaseScreen::new);
            });
        }
    }

}
