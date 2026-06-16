package com.lay.betterbadges.common.registry;

import com.google.common.base.Suppliers;
import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.emblem.Emblem;
import com.lay.betterbadges.common.api.league.League;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class ModRegistries {

    public static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(BetterBadges.MOD_ID));

    public static final Registrar<League> LEAGUE = createRegistrar(ModRegistryKeys.LEAGUE);

    public static final Registrar<Emblem> EMBLEM = createRegistrar(ModRegistryKeys.EMBLEM);

    public static final Registrar<BadgeCaseBase> BADGE_CASE_BASE = createRegistrar(ModRegistryKeys.BADGE_CASE_BASE);

    public static final Registrar<BadgeCaseCover> BADGE_CASE_COVER = createRegistrar(ModRegistryKeys.BADGE_CASE_COVER);

    public static void createRegistries(){
        BetterBadges.LOGGER.info("Creating custom registries");
    }

    @SuppressWarnings("unchecked")
    public static <T> Registrar<T> createRegistrar(ResourceKey<Registry<T>> key){
        return (Registrar<T>) MANAGER.get().builder(key.location()).syncToClients().build();
    }

}
