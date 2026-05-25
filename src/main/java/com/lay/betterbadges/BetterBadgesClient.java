package com.lay.betterbadges;

import com.lay.betterbadges.screen.ModScreenHandler;
//import com.lay.betterbadges.screen.badgecase.BadgeCaseGui;
import com.lay.betterbadges.screen.badgecase.BadgeCaseScreen;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import io.wispforest.owo.ui.base.BaseOwoHandledScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class BetterBadgesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModScreenHandler.BADGE_CASE_SCREEN_HANDLER, BadgeCaseScreen::new);
    }
}
