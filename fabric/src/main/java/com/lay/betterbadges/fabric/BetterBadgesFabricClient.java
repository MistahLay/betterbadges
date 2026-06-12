package com.lay.betterbadges.fabric;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.BetterBadgesClient;
import com.lay.betterbadges.common.render.atlas.managers.ModAtlasManagers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class BetterBadgesFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BetterBadgesClient.init();

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return BetterBadges.of("atlases");
            }

            @Override
            public CompletableFuture<Void> reload(
                    PreparationBarrier preparationBarrier,
                    ResourceManager resourceManager,
                    ProfilerFiller profilerFiller,
                    ProfilerFiller profilerFiller2,
                    Executor executor,
                    Executor executor2
            ) {
                return CompletableFuture.allOf(ModAtlasManagers.SHINE_SPRITES.reload(preparationBarrier, resourceManager, profilerFiller, profilerFiller2, executor, executor2));
            }
        });
    }
}
