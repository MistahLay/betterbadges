package com.lay.betterbadges.common.datapack;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.datapack.attributes.BadgeAttributesReloadListener;
import dev.architectury.registry.ReloadListenerRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.PackType;

public class ModDatapacks {

    public static void registerReloadListeners(){
        ReloadListenerRegistry.register(PackType.SERVER_DATA, new BadgeAttributesReloadListener(), BetterBadges.of("badge_attributes_listener"));
    }

}
