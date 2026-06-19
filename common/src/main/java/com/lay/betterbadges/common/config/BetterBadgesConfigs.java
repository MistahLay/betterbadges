package com.lay.betterbadges.common.config;

import com.cobblemon.mod.common.item.PokeBallItem;
import com.lay.betterbadges.common.config.BetterBadgesMainConfig;
import com.lay.betterbadges.common.BetterBadges;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BetterBadgesConfigs {

    private static final Set<PokeBallItem> BlacklistedPokeballsUse = new HashSet<>();

    public static boolean canPokeballBeReturned(PokeBallItem pokeball){
        return !BlacklistedPokeballsUse.contains(pokeball);
    }

    public static List<PokeBallItem> getBlacklistedPokeballs(){
        return new ArrayList<>(BlacklistedPokeballsUse);
    }

    public static BetterBadgesMainConfig config = BetterBadgesMainConfig.createAndLoad();

    public static void initializeConfigs(){
        for (String itemId : config.BlacklistPokeballUse()){
            try {
                Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemId));
                if (!(item instanceof PokeBallItem pokeball)) throw new RuntimeException("Blacklisted Pokeball is not a pokeball");
                if (BlacklistedPokeballsUse.contains(pokeball)) continue;
                BlacklistedPokeballsUse.add(pokeball);
            } catch (Exception e) {
                BetterBadges.LOGGER.error("Cannot add {} to the Pokeball Blacklist: {}", itemId, e.getMessage());
            }
        }
    }
}
