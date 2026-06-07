package com.lay.betterbadges.common.league;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.item.ModItems;
import dev.architectury.registry.registries.RegistrySupplier;

public class ModLeagues {

    public static final RegistrySupplier<League> KANTO = League.Builder.create(LeagueKeys.KANTO)
            .add(ModItems.BOULDER_BADGE.get(), 46, 74)
            .add(ModItems.CASCADE_BADGE.get(), 74, 74)
            .add(ModItems.THUNDER_BADGE.get(), 98, 74)
            .add(ModItems.RAINBOW_BADGE.get(), 126, 74)
            .add(ModItems.SOUL_BADGE.get(), 38, 97)
            .add(ModItems.MARSH_BADGE.get(), 70, 97)
            .add(ModItems.VOLCANO_BADGE.get(), 102, 97)
            .add(ModItems.EARTH_BADGE.get(), 134, 97)
            .build();

    public static final RegistrySupplier<League> JOHTO = League.Builder.create(LeagueKeys.JOHTO)
            .add(ModItems.ZEPHYR_BADGE.get(), 0, 97)
            .add(ModItems.HIVE_BADGE.get(), 18, 97)
            .add(ModItems.PLAIN_BADGE.get(), 36, 97)
            .add(ModItems.FOG_BADGE.get(), 54, 97)
            .add(ModItems.STORM_BADGE.get(), 72, 97)
            .add(ModItems.MINERAL_BADGE.get(), 90, 97)
            .add(ModItems.GLACIER_BADGE.get(), 108, 97)
            .add(ModItems.RISING_BADGE.get(), 126, 97)
            .build();

    public static void registerLeagues(){
        BetterBadges.LOGGER.info("Registering Better Badges leagues");
    }

}
