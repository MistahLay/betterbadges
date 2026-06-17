package com.lay.betterbadges.common;

import com.lay.betterbadges.common.api.attribute.ModAttributes;
import com.lay.betterbadges.common.command.ModCommands;
import com.lay.betterbadges.common.component.ModDataComponents;
import com.lay.betterbadges.common.config.ModConfigs;
import com.lay.betterbadges.common.datapack.ModDatapacks;
import com.lay.betterbadges.common.api.emblem.ModEmblems;
import com.lay.betterbadges.common.event.CobblemonEventsListener;
import com.lay.betterbadges.common.item.ModCreativeTab;
import com.lay.betterbadges.common.item.ModItems;
import com.lay.betterbadges.common.api.league.ModLeagues;
import com.lay.betterbadges.common.api.league.attributes.BadgeAttributesManager;
import com.lay.betterbadges.common.network.ModNetworkChannel;
import com.lay.betterbadges.common.registry.ModRegistries;
import com.lay.betterbadges.common.render.screen.ModScreens;

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

		ModRegistries.createRegistries();
		ModDataComponents.registerDataComponents();

		// Attributes
		ModAttributes.registerAttributes();

		// Items
		ModCreativeTab.registerItemGroups();
		ModItems.registerModItems();

		// Custom Registries
		ModLeagues.registerLeagues();
		ModEmblems.registerEmblems();

		// Screens, duh
		ModScreens.registerScreenHandlers();

		// Networking
		ModNetworkChannel.initialize();

		// Datapacks
		ModDatapacks.registerReloadListeners();

		CobblemonEventsListener.listen();

		ModCommands.registerCommands();

		ModConfigs.initializeConfigs();

		LifecycleEvent.SERVER_STARTED.register(minecraftServer -> SERVER = minecraftServer);

		PlayerEvent.PLAYER_JOIN.register(player -> BadgeAttributesManager.updateClients());
	}

}
