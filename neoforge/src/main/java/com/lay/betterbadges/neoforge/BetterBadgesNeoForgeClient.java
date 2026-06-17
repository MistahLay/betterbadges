package com.lay.betterbadges.neoforge;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.BetterBadgesClient;
import com.lay.betterbadges.common.attribute.ModAttributes;
import com.lay.betterbadges.common.render.atlas.managers.ShineSpritesManager;
import com.lay.betterbadges.common.render.atlas.sources.AnimationOverlayPermutations;
import com.lay.betterbadges.common.render.screen.ModScreens;
import com.lay.betterbadges.common.render.screen.badgecase.BadgeCaseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterSpriteSourceTypesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@EventBusSubscriber(modid = BetterBadges.MOD_ID, value = Dist.CLIENT)
public class BetterBadgesNeoForgeClient {

    @SubscribeEvent
    public static void onClientReload(RegisterClientReloadListenersEvent event){
        if (AnimationOverlayPermutations.SOURCE_TYPE == null) AnimationOverlayPermutations.register();
        if (ShineSpritesManager.MANAGER == null) ShineSpritesManager.register(Minecraft.getInstance().getTextureManager());
        event.registerReloadListener(ShineSpritesManager.MANAGER);
    }

    @SubscribeEvent
    public static void onSpriteSourcesRegister(RegisterSpriteSourceTypesEvent event){
        event.register(BetterBadges.of(AnimationOverlayPermutations.ID), AnimationOverlayPermutations.SOURCE_TYPE);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModScreens.BADGE_CASE_SCREEN_HANDLER.get(), BadgeCaseScreen::new);
    }
}
