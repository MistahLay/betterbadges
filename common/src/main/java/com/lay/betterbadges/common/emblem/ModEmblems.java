package com.lay.betterbadges.common.emblem;

import com.lay.betterbadges.common.BetterBadges;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class ModEmblems {

    public static final RegistrySupplier<Emblem> FLOWERING = Emblem.Builder.create(createEmblemPath("flowering"), Items.DIAMOND_SWORD)
            .add(Boost.ADVENTURE, 10, 10)
            .add(Boost.ADVENTURE, 9, 40)
            .add(Boost.ADVENTURE, 10, 70)
            .add(Boost.CATCHING, 40, 71)
            .add(Boost.CATCHING, 40, 40)
            .add(Boost.CATCHING, 40, 9)
            .add(Boost.SPAWNING, 70, 10)
            .add(Boost.SPAWNING, 71, 40)
            .add(Boost.SPAWNING, 70, 70)
            .build();

    public static void registerEmblems(){ }

    public static ResourceLocation createEmblemPath(String path){
        return BetterBadges.of(path);
    }

}
