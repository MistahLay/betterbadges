package com.lay.betterbadges;

import com.lay.betterbadges.command.ModCommands;
import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.datapack.ModDatapacks;
import com.lay.betterbadges.emblem.ModEmblems;
import com.lay.betterbadges.event.ModEvents;
import com.lay.betterbadges.item.ModCreativeTab;
import com.lay.betterbadges.item.ModItems;
import com.lay.betterbadges.league.ModLeagues;
import com.lay.betterbadges.network.ModNetworkChannel;
import com.lay.betterbadges.registry.BadgeCaseBase;
import com.lay.betterbadges.registry.BadgeCaseCover;
import com.lay.betterbadges.registry.ModRegistries;
import com.lay.betterbadges.screen.ModScreens;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.platform.Platform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BetterBadges {
	public static final String MOD_ID = "betterbadges";

	public static ResourceLocation of(String path){
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static MinecraftServer SERVER;

	public static void init() {
		LOGGER.info("Initializing Better Badges");

		LOGGER.info("{}", Platform.isModLoaded("architectury"));

		ModRegistries.createRegistries();
		ModDataComponents.registerDataComponents();

		// Items
		ModCreativeTab.registerItemGroups();
		ModItems.registerModItems();

		// Custom Registries
		ModLeagues.registerLeagues();
		ModEmblems.registerEmblems();
		BadgeCaseBase.registerFromConfig();
		BadgeCaseCover.registerFromConfig();

		// Screens, duh
		ModScreens.registerScreenHandlers();

		// Networking
		ModNetworkChannel.initialize();

		// Datapacks
		ModDatapacks.registerReloadListeners();

		ModEvents.initialize();
		ModCommands.registerCommands();

		LifecycleEvent.SERVER_STARTED.register(a -> SERVER = a);
	}

}
