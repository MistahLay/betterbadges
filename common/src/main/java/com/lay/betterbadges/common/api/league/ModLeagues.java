package com.lay.betterbadges.common.api.league;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.item.BetterBadgesItems;
import dev.architectury.registry.registries.RegistrySupplier;

public class ModLeagues {

    public static final RegistrySupplier<League> KANTO = League.Builder.create(LeagueKeys.KANTO)
            .add(BetterBadgesItems.BOULDER_BADGE, 46, 18)
            .add(BetterBadgesItems.CASCADE_BADGE, 74, 18)
            .add(BetterBadgesItems.THUNDER_BADGE, 98, 18)
            .add(BetterBadgesItems.RAINBOW_BADGE, 126, 18)
            .add(BetterBadgesItems.SOUL_BADGE, 38, 36)
            .add(BetterBadgesItems.MARSH_BADGE, 70, 36)
            .add(BetterBadgesItems.VOLCANO_BADGE, 102, 36)
            .add(BetterBadgesItems.EARTH_BADGE, 134, 36)
            .build();

    public static final RegistrySupplier<League> JOHTO = League.Builder.create(LeagueKeys.JOHTO)
            .add(BetterBadgesItems.ZEPHYR_BADGE, 0, 97)
            .add(BetterBadgesItems.HIVE_BADGE, 18, 97)
            .add(BetterBadgesItems.PLAIN_BADGE, 36, 97)
            .add(BetterBadgesItems.FOG_BADGE, 54, 97)
            .add(BetterBadgesItems.STORM_BADGE, 72, 97)
            .add(BetterBadgesItems.MINERAL_BADGE, 90, 97)
            .add(BetterBadgesItems.GLACIER_BADGE, 108, 97)
            .add(BetterBadgesItems.RISING_BADGE, 126, 97)
            .build();

    public static void registerLeagues(){
        BetterBadges.LOGGER.info("Registering Better Badges leagues");
    }

}
