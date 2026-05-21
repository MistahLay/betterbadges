package com.lay.betterbadges.league;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.item.ModItems;

public class LeagueRegistry {

    public static final League KANTO = League.Builder.create("kanto")
            .addBadge(ModItems.BOULDER_BADGE)
            .addBadge(ModItems.CASCADE_BADGE)
            .addBadge(ModItems.THUNDER_BADGE)
            .addBadge(ModItems.RAINBOW_BADGE)
            .addBadge(ModItems.SOUL_BADGE)
            .addBadge(ModItems.MARSH_BADGE)
            .addBadge(ModItems.VOLCANO_BADGE)
            .addBadge(ModItems.EARTH_BADGE)
            .build();

    public static void registerLeagues(){
        BetterBadges.LOGGER.info("Registering Better Badges leagues");
    }

}
