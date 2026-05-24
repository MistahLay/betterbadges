package com.lay.betterbadges;

import com.lay.betterbadges.screen.ModScreenHandler;
//import com.lay.betterbadges.screen.badgecase.BadgeCaseGui;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class BetterBadgesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
//        MenuScreens.<BadgeCaseGui, CottonInventoryScreen<BadgeCaseGui>>register(ModScreenHandler.BADGE_CASE_GUI, CottonInventoryScreen::new);
    }
}
