package com.lay.betterbadges.neoforge;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.BetterBadgesClient;
import com.lay.betterbadges.common.render.atlas.managers.ModAtlasManagers;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@EventBusSubscriber(modid = BetterBadges.MOD_ID, value = Dist.CLIENT)
public class BetterBadgesNeoForgeClient {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        BetterBadgesClient.init();
    }

    @SubscribeEvent
    public static void onClientReload(RegisterClientReloadListenersEvent event){
        event.registerReloadListener((
                ModAtlasManagers.SHINE_SPRITES
        ));
    }
}
