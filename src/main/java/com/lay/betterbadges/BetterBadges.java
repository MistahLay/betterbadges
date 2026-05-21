package com.lay.betterbadges;

import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.item.ModCreativeTab;
import com.lay.betterbadges.item.ModItems;
import com.lay.betterbadges.league.LeagueRegistry;
import com.lay.betterbadges.screen.ModScreenHandler;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterBadges implements ModInitializer {
	public static final String MOD_ID = "betterbadges";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Better Badges");
		ModDataComponents.registerDataComponents();
		ModCreativeTab.registerItemGroups();
		ModItems.registerModItems();
		LeagueRegistry.registerLeagues();
		ModScreenHandler.registerScreenHandlers();
	}
}