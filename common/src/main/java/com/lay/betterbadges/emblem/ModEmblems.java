package com.lay.betterbadges.emblem;

import com.lay.betterbadges.BetterBadges;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class ModEmblems {

    public static final RegistrySupplier<Emblem> KANTO = Emblem.Builder.create(createEmblemPath("kanto"), Items.DIAMOND_SWORD)
            .add(Boost.ADVENTURE, 0, 0)
            .add(Boost.ADVENTURE, 18, 0)
            .add(Boost.ADVENTURE, 36, 0)
            .add(Boost.CATCHING, 0, 18)
            .add(Boost.CATCHING, 18, 18)
            .add(Boost.CATCHING, 36, 18)
            .add(Boost.SPAWNING, 0, 36)
            .add(Boost.SPAWNING, 18, 36)
            .build();

    public static final RegistrySupplier<Emblem> JOHTO = Emblem.Builder.create(createEmblemPath("johto"), Items.STONE_SWORD)
            .add(Boost.ADVENTURE, 0, 36)
            .add(Boost.ADVENTURE, 18, 36)
            .add(Boost.CATCHING, 0, 0)
            .add(Boost.CATCHING, 18, 0)
            .add(Boost.CATCHING, 36, 0)
            .add(Boost.SPAWNING, 0, 18)
            .add(Boost.SPAWNING, 18, 18)
            .add(Boost.SPAWNING, 36, 18)
            .build();

    public static void registerEmblems(){ }

    public static ResourceLocation createEmblemPath(String path){
        return BetterBadges.of(path).withSuffix("_emblem");
    }

}
