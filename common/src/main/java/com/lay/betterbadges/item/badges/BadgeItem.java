package com.lay.betterbadges.item.badges;

import com.lay.betterbadges.item.bounded.BoundItem;
import com.lay.betterbadges.league.BadgeSlot;
import com.lay.betterbadges.league.League;
import com.lay.betterbadges.registry.ModRegistries;
import net.minecraft.resources.ResourceLocation;

public class BadgeItem extends BoundItem {

    private final ResourceLocation leagueLocation;

    public BadgeItem(Properties properties, ResourceLocation leagueLocation) {
        super(properties);
        this.leagueLocation = leagueLocation;
    }

    public League getLeague(){
        League league = ModRegistries.LEAGUE.get(this.leagueLocation);
        if(league == null || league == League.EMPTY) throw new RuntimeException("League: " + this.leagueLocation.toString() + " does not exists");
        return league;
    }

    public BadgeSlot getBadgeSlot(){
        return this.getLeague().getBadgeFromItem(this);
    }

}
