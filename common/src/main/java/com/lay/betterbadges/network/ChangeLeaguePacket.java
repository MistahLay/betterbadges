package com.lay.betterbadges.network;

import com.lay.betterbadges.league.League;
import com.lay.betterbadges.registry.ModRegistries;
import net.minecraft.resources.ResourceLocation;

public record ChangeLeaguePacket(String league) {
    public League getRegisteredLeague(){
        return ModRegistries.LEAGUE.get(ResourceLocation.parse(league));
    }
}
