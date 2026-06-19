package com.lay.betterbadges.fabric;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.attribute.vanilla.MiscVanillaAttributes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;

public final class BetterBadgesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        BetterBadges.init();

        this.listenEvents();
    }

    public void listenEvents(){
        EntityElytraEvents.CUSTOM.register((entity, tickElytra) -> MiscVanillaAttributes.hasNaturalElytra(entity));
    }

}
