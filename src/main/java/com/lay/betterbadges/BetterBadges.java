package com.lay.betterbadges;

import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.config.ConfigEndecs;
import com.lay.betterbadges.config.ModConfigs;
import com.lay.betterbadges.item.ModCreativeTab;
import com.lay.betterbadges.item.ModItems;
import com.lay.betterbadges.league.Badge;
import com.lay.betterbadges.league.ModLeagues;
import com.lay.betterbadges.registry.BadgeCaseBase;
import com.lay.betterbadges.registry.BadgeCaseCover;
import com.lay.betterbadges.registry.ModRegistries;
import com.lay.betterbadges.screen.ModScreenHandler;
import net.fabricmc.api.ModInitializer;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterBadges implements ModInitializer {
	public static final String MOD_ID = "betterbadges";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Better Badges");

		ConfigEndecs.registerEndecs();

		ModConfigs.initializeConfigs();

		ModRegistries.createRegistries();

		ModDataComponents.registerDataComponents();

		// Items
		ModCreativeTab.registerItemGroups();
		ModItems.registerModItems();

		// Custom Registries
		ModLeagues.registerLeagues();
		BadgeCaseBase.registerFromConfig();
		BadgeCaseCover.registerFromConfig();

		// Screens, duh
		ModScreenHandler.registerScreenHandlers();
	}

}