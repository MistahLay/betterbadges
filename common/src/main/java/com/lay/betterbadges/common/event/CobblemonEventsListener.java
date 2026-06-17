package com.lay.betterbadges.common.event;

import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.lay.betterbadges.common.util.BattleRewardsHelper;
import com.lay.betterbadges.common.util.CatchModifierHelper;
import net.minecraft.server.level.ServerPlayer;

public class CobblemonEventsListener {

    // Shhh a Smart Person is talking, stop talking
    public static void listen(){
        CobblemonEvents.POKEMON_CATCH_RATE.subscribe(event -> {
            // TODO: Add catch modifier
            if (event.getThrower() instanceof ServerPlayer) {
                event.setCatchRate(CatchModifierHelper.modify(event));
            }
        });

        CobblemonEvents.LOOT_DROPPED.subscribe(event -> {
            BattleRewardsHelper.create(event.getPlayer()).modifyLoot(event.getTable(), event.getDrops());
        });

        CobblemonEvents.EXPERIENCE_GAINED_EVENT_PRE.subscribe(event -> {
            if (event.getSource().isBattle()){
                event.setExperience(BattleRewardsHelper.create(event.getPokemon().getOwnerPlayer()).getXpRewardBoost(event.getExperience()));
            }
        });

        CobblemonEvents.EXPERIENCE_CANDY_USE_PRE.subscribe(event ->
                event.setExperienceYield(BattleRewardsHelper.create(event.getPlayer()).getXpCandyBoost(event.getExperienceYield()))
        );
    }

}
