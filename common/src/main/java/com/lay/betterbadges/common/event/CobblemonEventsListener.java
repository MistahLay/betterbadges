package com.lay.betterbadges.common.event;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.spawning.spawner.Spawner;
import com.lay.betterbadges.common.util.cobblemon.BattleRewardsModifierHelper;
import com.lay.betterbadges.common.util.cobblemon.CatchModifierHelper;
import com.lay.betterbadges.common.util.cobblemon.SpawningModifierHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

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
            if (event.getPlayer() instanceof ServerPlayer) BattleRewardsModifierHelper.create(event.getPlayer()).modifyLoot(event.getTable(), event.getDrops());
        });

        CobblemonEvents.EXPERIENCE_GAINED_EVENT_PRE.subscribe(event -> {
            if (event.getSource().isBattle()){
                event.setExperience(BattleRewardsModifierHelper.create(event.getPokemon().getOwnerPlayer()).getXpRewardBoost(event.getExperience()));
            }
        });

        CobblemonEvents.EXPERIENCE_CANDY_USE_PRE.subscribe(event ->
            event.setExperienceYield(BattleRewardsModifierHelper.create(event.getPlayer()).getXpCandyBoost(event.getExperienceYield()))
        );

        CobblemonEvents.SPAWN_BUCKET_CHOSEN.subscribe(event -> {
            Entity entity = event.getSpawnCause().getEntity();
            if (entity instanceof ServerPlayer player) {
                Spawner spawner = event.getSpawner();
                SpawningModifierHelper helper = SpawningModifierHelper.create(player);
                spawner.setSpawnPool(helper.createNewSpawnPool(spawner.getSpawnPool()));
                event.setBucket(helper.createNewBucket(spawner.getInfluences()));
            }
        });

        CobblemonEvents.POKEMON_ENTITY_SPAWN.subscribe(event -> {

        });

        CobblemonEvents.POKEMON_CAPTURED.subscribe(event -> {
            SpawningModifierHelper.create(event.getPlayer())
                    .boostIvs(event.getPokemon());
        });

        CobblemonEvents.SHINY_CHANCE_CALCULATION.subscribe(Priority.HIGH, event -> {
            event.addModificationFunction((aFloat, player, pokemon) -> {
                if (player != null) return SpawningModifierHelper.create(player).getBoostedShinyOdds(aFloat);
                return aFloat;
            });
        });
    }

}
