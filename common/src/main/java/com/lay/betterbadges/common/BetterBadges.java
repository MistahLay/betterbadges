package com.lay.betterbadges.common;

import com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes;
import com.lay.betterbadges.common.command.BetterBadgesCommands;
import com.lay.betterbadges.common.component.BetterBadgesDataComponents;
import com.lay.betterbadges.common.config.BetetrBadgesConfigs;
import com.lay.betterbadges.common.datapack.BetterBadgesDatapacks;
import com.lay.betterbadges.common.api.emblem.ModEmblems;
import com.lay.betterbadges.common.event.CobblemonEventsListener;
import com.lay.betterbadges.common.item.BetterBadgesItems;
import com.lay.betterbadges.common.item.BetterBadgesCreativeTab;
import com.lay.betterbadges.common.api.league.ModLeagues;
import com.lay.betterbadges.common.api.league.attributes.BadgeAttributesManager;
import com.lay.betterbadges.common.network.BetterBadgesNetworkChannel;
import com.lay.betterbadges.common.registry.BetterBadgesRegistries;
import com.lay.betterbadges.common.render.screen.BetterBadgesScreens;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
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

		BetterBadgesRegistries.createRegistries();
		BetterBadgesDataComponents.registerDataComponents();

		// Attributes
		BetterBadgesAttributes.registerAttributes();

		// Items
		BetterBadgesCreativeTab.registerItemGroups();
		BetterBadgesItems.registerModItems();

		// Custom Registries
		ModLeagues.registerLeagues();
		ModEmblems.registerEmblems();

		// Screens, duh
		BetterBadgesScreens.registerScreenHandlers();

		// Networking
		BetterBadgesNetworkChannel.initialize();

		// Datapacks
		BetterBadgesDatapacks.registerReloadListeners();

		CobblemonEventsListener.listen();

		BetterBadgesCommands.registerCommands();

		BetetrBadgesConfigs.initializeConfigs();

		LifecycleEvent.SERVER_STARTED.register(minecraftServer -> SERVER = minecraftServer);

		PlayerEvent.PLAYER_JOIN.register(player -> BadgeAttributesManager.updateClients());
	}

}
