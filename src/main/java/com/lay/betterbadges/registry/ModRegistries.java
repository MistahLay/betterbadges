package com.lay.betterbadges.registry;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.emblem.Emblem;
import com.lay.betterbadges.league.League;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;

public class ModRegistries {

    public static final Registry<League> LEAGUE = FabricRegistryBuilder.createSimple(RegistryKeys.LEAGUE)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static final Registry<Emblem> EMBLEM = FabricRegistryBuilder.createSimple(RegistryKeys.EMBLEM)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static final Registry<BadgeCaseBase> BADGE_CASE_BASE = FabricRegistryBuilder.createSimple(RegistryKeys.BADGE_CASE_BASE)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static final Registry<BadgeCaseCover> BADGE_CASE_COVER = FabricRegistryBuilder.createSimple(RegistryKeys.BADGE_CASE_COVER)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static void createRegistries(){
        BetterBadges.LOGGER.info("Creating custom registries");
    }

}
