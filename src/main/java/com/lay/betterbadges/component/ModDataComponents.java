package com.lay.betterbadges.component;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.component.custom.LeagueInventoryContents;
import com.lay.betterbadges.league.League;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.function.UnaryOperator;

public class ModDataComponents {

    public static final DataComponentType<String> ITEM_OWNER = register("item_owner", uuidBuilder -> uuidBuilder.persistent(Codec.STRING));
    public static final DataComponentType<League> CURRENT_LEAGUE = register("current_league", league -> league.persistent(League.CODEC));
    public static final DataComponentType<LeagueInventoryContents> LEAGUE_INVENTORY_CONTENTS = register("league_inventory_contents", builder -> builder.persistent(LeagueInventoryContents.CODEC));

    private static <T>DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderUnaryOperator){
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, name), builderUnaryOperator.apply(DataComponentType.builder()).build());
    }

    public static void registerDataComponents(){
        BetterBadges.LOGGER.info("Registering Data Components for " + BetterBadges.MOD_ID);
    }

}
