package com.lay.betterbadges.datapack;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.datapack.attributes.BadgeAttributesReloadListener;
import dev.architectury.registry.ReloadListenerRegistry;
import net.minecraft.server.packs.PackType;

public class ModDatapacks {

    public static void registerReloadListeners(){
        ReloadListenerRegistry.register(PackType.SERVER_DATA, new BadgeAttributesReloadListener(), BetterBadges.of("badge_attributes_listener"));
    }

}
