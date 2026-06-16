package com.lay.betterbadges.common.event;

import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.lay.betterbadges.common.util.CatchModifierUtil;

public class CobblemonEventsListener {

    // Shhh a Smart Person is talking, stop talking
    public static void listen(){
        CobblemonEvents.POKEMON_CATCH_RATE.subscribe(event -> {
            event.setCatchRate(CatchModifierUtil.increaseModifierValue(event.getCatchRate(), event.getThrower(), event.getPokemonEntity().getPokemon()));
        });
    }

}
