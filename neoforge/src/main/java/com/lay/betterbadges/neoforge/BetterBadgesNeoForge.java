package com.lay.betterbadges.neoforge;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.attribute.neoforge.BetterBadgesAttributesImpl;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(BetterBadges.MOD_ID)
public final class BetterBadgesNeoForge {

    public BetterBadgesNeoForge(IEventBus bus) {
        BetterBadges.init();

        BetterBadgesAttributesImpl.ATTRIBUTES.register(bus);
    }

}
