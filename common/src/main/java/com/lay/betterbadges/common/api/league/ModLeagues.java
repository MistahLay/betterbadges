package com.lay.betterbadges.common.api.league;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.item.ModItems;
import com.lay.betterbadges.common.registry.ModRegistries;
import dev.architectury.registry.registries.RegistrySupplier;

public class ModLeagues {

    public static final RegistrySupplier<League> KANTO = League.Builder.create(LeagueKeys.KANTO)
            .add(ModItems.BOULDER_BADGE, 46, 18)
            .add(ModItems.CASCADE_BADGE, 74, 18)
            .add(ModItems.THUNDER_BADGE, 98, 18)
            .add(ModItems.RAINBOW_BADGE, 126, 18)
            .add(ModItems.SOUL_BADGE, 38, 36)
            .add(ModItems.MARSH_BADGE, 70, 36)
            .add(ModItems.VOLCANO_BADGE, 102, 36)
            .add(ModItems.EARTH_BADGE, 134, 36)
            .build();

    public static final RegistrySupplier<League> JOHTO = League.Builder.create(LeagueKeys.JOHTO)
            .add(ModItems.ZEPHYR_BADGE, 0, 97)
            .add(ModItems.HIVE_BADGE, 18, 97)
            .add(ModItems.PLAIN_BADGE, 36, 97)
            .add(ModItems.FOG_BADGE, 54, 97)
            .add(ModItems.STORM_BADGE, 72, 97)
            .add(ModItems.MINERAL_BADGE, 90, 97)
            .add(ModItems.GLACIER_BADGE, 108, 97)
            .add(ModItems.RISING_BADGE, 126, 97)
            .build();

    public static void registerLeagues(){
        BetterBadges.LOGGER.info("Registering Better Badges leagues");
    }

}
