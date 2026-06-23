package com.lay.betterbadges.neoforge;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.render.atlas.managers.ShineSpritesManager;
import com.lay.betterbadges.common.render.atlas.sources.AnimationOverlayPermutations;
import com.lay.betterbadges.common.render.neoforge.ModelRegistrationHelperImpl;
import com.lay.betterbadges.common.render.screen.BetterBadgesScreens;
import com.lay.betterbadges.common.render.screen.badgecase.BadgeCaseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterSpriteSourceTypesEvent;

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
        event.register(BetterBadgesScreens.BADGE_CASE_SCREEN_HANDLER.get(), BadgeCaseScreen::new);
    }

    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterAdditional event){
        for (ModelResourceLocation resourceLocation : ModelRegistrationHelperImpl.toRegister) event.register(resourceLocation);
    }
}
