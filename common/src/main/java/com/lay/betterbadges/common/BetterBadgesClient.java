package com.lay.betterbadges.common;

import com.lay.betterbadges.common.render.atlas.sources.ModAtlasSources;
import com.lay.betterbadges.common.render.screen.ModScreens;
import com.lay.betterbadges.common.render.screen.badgecase.BadgeCaseScreen;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.platform.Platform;
import net.minecraft.client.gui.screens.MenuScreens;

public final class BetterBadgesClient {

    public static void init() {
        ModAtlasSources.init();
        if(Platform.isFabric()) {
            ClientLifecycleEvent.CLIENT_STARTED.register(client -> {
                MenuScreens.register(ModScreens.BADGE_CASE_SCREEN_HANDLER.get(), BadgeCaseScreen::new);
            });
        }
    }

}
