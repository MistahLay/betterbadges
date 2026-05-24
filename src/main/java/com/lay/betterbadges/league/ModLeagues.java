package com.lay.betterbadges.league;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.item.ModItems;

public class ModLeagues {

    public static void registerLeagues(){
        BetterBadges.LOGGER.info("Registering Better Badges leagues");

        League.Builder.fromConfig();
    }


}
