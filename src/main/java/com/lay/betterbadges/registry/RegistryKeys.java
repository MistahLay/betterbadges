package com.lay.betterbadges.registry;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.emblem.Emblem;
import com.lay.betterbadges.league.League;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class RegistryKeys {
    public static final ResourceKey<Registry<Emblem>> EMBLEM = createRegistryKey("emblem");
    public static final ResourceKey<Registry<League>> LEAGUE = createRegistryKey("league");

    public static final ResourceKey<Registry<BadgeCaseBase>> BADGE_CASE_BASE = createRegistryKey("badge_case_base");
    public static final ResourceKey<Registry<BadgeCaseCover>> BADGE_CASE_COVER = createRegistryKey("badge_case_cover");

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String key){
        return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, key));
    }
}
