package com.lay.betterbadges.common;

import com.lay.betterbadges.common.command.ModCommands;
import com.lay.betterbadges.common.component.ModDataComponents;
import com.lay.betterbadges.common.datapack.ModDatapacks;
import com.lay.betterbadges.common.emblem.ModEmblems;
import com.lay.betterbadges.common.event.ModEvents;
import com.lay.betterbadges.common.item.ModCreativeTab;
import com.lay.betterbadges.common.item.ModItems;
import com.lay.betterbadges.common.league.ModLeagues;
import com.lay.betterbadges.common.network.ModNetworkChannel;
import com.lay.betterbadges.common.registry.ModRegistries;
import com.lay.betterbadges.common.render.screen.ModScreens;

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

		// Registries
		ModLeagues.registerLeagues();
		ModEmblems.registerEmblems();

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
