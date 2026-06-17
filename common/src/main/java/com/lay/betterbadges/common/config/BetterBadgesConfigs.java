package com.lay.betterbadges.common.config;

import com.cobblemon.mod.common.item.PokeBallItem;
import com.lay.betterbadges.common.config.BetterBadgesMainConfig;
import com.lay.betterbadges.common.BetterBadges;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class BetterBadgesConfigs {

    private static List<PokeBallItem> BlacklistedPokeballs = new ArrayList<>();

    public static List<PokeBallItem> getBlacklistedPokeballs(){
        return new ArrayList<>(BlacklistedPokeballs);
    }

    public static BetterBadgesMainConfig config = BetterBadgesMainConfig.createAndLoad();

    public static void initializeConfigs(){
        for (String itemId : config.blacklistedPokaballs()){
            try {
                Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemId));
                if (!(item instanceof PokeBallItem pokeball)) throw new RuntimeException("Blacklisted Pokeball is not a pokeball");
                if (BlacklistedPokeballs.contains(pokeball)) continue;
                BlacklistedPokeballs.add(pokeball);
            } catch (Exception e) {
                BetterBadges.LOGGER.error("Cannot add {} to the Pokeball Blacklist: {}", itemId, e.getMessage());
            }
        }
    }
}
