package com.lay.betterbadges.common.event;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.spawning.spawner.Spawner;
import com.lay.betterbadges.common.util.cobblemon.BattleRewardsModifierHelper;
import com.lay.betterbadges.common.util.cobblemon.CatchModifierHelper;
import com.lay.betterbadges.common.util.cobblemon.MiscCobblemonHelper;
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
            if (event.getPlayer() instanceof ServerPlayer player) BattleRewardsModifierHelper.create(player).modifyLoot(event.getTable(), event.getDrops());
        });

        CobblemonEvents.EXPERIENCE_GAINED_EVENT_PRE.subscribe(event -> {
            if (event.getSource().isBattle()){
                event.setExperience(BattleRewardsModifierHelper.create(event.getPokemon().getOwnerPlayer()).getXpRewardBoost(event.getExperience()));
            }
        });

        CobblemonEvents.EXPERIENCE_CANDY_USE_PRE.subscribe(event ->
            event.setExperienceYield(MiscCobblemonHelper.getXpCandyBoost(event.getPlayer(), event.getExperienceYield()))
        );

        CobblemonEvents.SPAWN_BUCKET_CHOSEN.subscribe(event -> {
            Entity entity = event.getSpawnCause().getEntity();
            if (entity instanceof ServerPlayer player) {
                Spawner spawner = event.getSpawner();
                spawner.setSpawnPool(SpawningModifierHelper.createNewSpawnPool(player, spawner.getSpawnPool()));
                event.setBucket(SpawningModifierHelper.createNewBucket(player, spawner.getInfluences()));
            }
        });

        CobblemonEvents.POKEMON_CAPTURED.subscribe(event -> {
            SpawningModifierHelper.applyBoostedIvs(event.getPlayer(), event.getPokemon());
        });

        CobblemonEvents.SHINY_CHANCE_CALCULATION.subscribe(Priority.HIGH, event -> {
            event.addModificationFunction((aFloat, player, pokemon) -> {
                if (player != null) return SpawningModifierHelper.getBoostedShinyOdds(player, aFloat);
                return aFloat;
            });
        });

        CobblemonEvents.BERRY_YIELD.subscribe(event -> {
            if (event.getPlacer() instanceof ServerPlayer player) {
                event.setYield(MiscCobblemonHelper.getBonusBerryYield(player, event.getYield()));
            }
        });

        CobblemonEvents.FRIENDSHIP_UPDATED.subscribe(event -> {
            int original = event.getNewFriendship();
            ServerPlayer player = event.getPokemon().getOwnerPlayer();
            if (original - event.getNewFriendshipInitial() > 0 && player != null) event.setNewFriendship(MiscCobblemonHelper.getBonusFriendship(player, original));
        });

        CobblemonEvents.EV_GAINED_EVENT_PRE.subscribe(event -> {
            event.setAmount( event.getAmount());
        });
    }

}
