package com.lay.betterbadges.common.network;

import com.lay.betterbadges.common.league.League;
import com.lay.betterbadges.common.registry.ModRegistries;
import net.minecraft.resources.ResourceLocation;

public record ChangeLeaguePacket(String league) {
    public League getRegisteredLeague(){
        return ModRegistries.LEAGUE.get(ResourceLocation.parse(league));
    }
}
