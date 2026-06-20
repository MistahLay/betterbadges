package com.lay.betterbadges.common.network.packets;

import com.lay.betterbadges.common.api.league.League;
import com.lay.betterbadges.common.registry.BetterBadgesRegistries;
import net.minecraft.resources.ResourceLocation;

public record ChangeLeaguePacket(String league) {
    public League getRegisteredLeague(){
        return BetterBadgesRegistries.LEAGUE.get(ResourceLocation.parse(league));
    }
}