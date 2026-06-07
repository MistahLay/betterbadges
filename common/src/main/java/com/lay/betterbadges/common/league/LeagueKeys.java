package com.lay.betterbadges.common.league;

import com.lay.betterbadges.common.BetterBadges;
import net.minecraft.resources.ResourceLocation;

public class LeagueKeys {

    public static final ResourceLocation KANTO = createLeaguePath("kanto");
    public static final ResourceLocation JOHTO = createLeaguePath("johto");

    public static ResourceLocation createLeaguePath(String path){
        return BetterBadges.of(path).withSuffix("_league");
    }

}
