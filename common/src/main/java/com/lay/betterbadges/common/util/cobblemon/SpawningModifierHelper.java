package com.lay.betterbadges.common.util.cobblemon;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.api.pokemon.stats.Stat;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.api.spawning.BestSpawner;
import com.cobblemon.mod.common.api.spawning.SpawnBucket;
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnDetail;
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail;
import com.cobblemon.mod.common.api.spawning.detail.SpawnPool;
import com.cobblemon.mod.common.api.spawning.influence.SpawningInfluence;
import com.cobblemon.mod.common.pokemon.IVs;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.mod.common.util.CollectionUtilsKt;
import com.cobblemon.mod.common.util.ResourceLocationExtensionsKt;
import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes;
import com.lay.betterbadges.common.api.attribute.cobblemon.SpawningAttributes;
import com.lay.betterbadges.common.util.PlayerAttributeHelper;
import kotlin.random.Random;
import net.minecraft.server.level.ServerPlayer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SpawningModifierHelper extends PlayerAttributeHelper {

    public static SpawningModifierHelper create(ServerPlayer player){
        return new SpawningModifierHelper(player);
    }

    private SpawningModifierHelper(ServerPlayer player){
        super(player);
    }

    public SpawnPool createNewSpawnPool(SpawnPool pool){
        SpawnPool newPool = pool.copy(pool.getName() + "updated");

        Map<SpawnDetail, Float> rawPercentages = new LinkedHashMap<>();
        float total = 0f;

        for (SpawnDetail detail : newPool.getDetails()) {
            float percentage = detail.getPercentage();

            if (detail instanceof PokemonSpawnDetail pokemonSpawnDetail) {
                String orgSpeciesName = pokemonSpawnDetail.getPokemon().getSpecies();
                if (orgSpeciesName == null) continue;
                Species species = PokemonSpecies.getByIdentifier(
                        ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace(orgSpeciesName, Cobblemon.MODID)
                );
                if (species == null) continue;
                percentage += this.attributeValue(SpawningAttributes.getByElementalType(species.getPrimaryType()));
                if (species.getSecondaryType() != null) {
                    percentage += this.attributeValue(SpawningAttributes.getByElementalType(species.getSecondaryType())) / 2f;
                }
            }

            rawPercentages.put(detail, percentage);
            total += percentage;
        }

        if (total > 0f) {
            for (Map.Entry<SpawnDetail, Float> entry : rawPercentages.entrySet()) {
                entry.getKey().setPercentage((entry.getValue() / total) * 100f);
            }
        }

        return newPool;
    }

    public float getBoostedShinyOdds(Float original){
        double modifier = this.attributeValue(SpawningAttributes.SHINY_SPAWNING);
        return (float) (original / modifier);
    }

    /**
     * Redo the the choosing bucket probability
     */
    public SpawnBucket createNewBucket(List<SpawningInfluence> influences){
        List<SpawnBucket> buckets = BestSpawner.INSTANCE.getConfig().getBuckets();
        Map<SpawnBucket, Float> spawnBucketsMap = new LinkedHashMap<>();
        for (SpawnBucket bucket : buckets){
            float newWeight = this.attributeValue(SpawningAttributes.getByRarityBucket(bucket));
            spawnBucketsMap.put(bucket, bucket.getWeight() + newWeight);
        }
        influences.forEach(influence -> influence.affectBucketWeights(spawnBucketsMap));
        Map.Entry<SpawnBucket, Float> chosenEntry = CollectionUtilsKt.weightedSelection(
                spawnBucketsMap.entrySet(),
                Random.Default,
                Map.Entry::getValue
        );
        return (chosenEntry != null) ? chosenEntry.getKey() : buckets.getFirst();
    }

    public void boostIvs(Pokemon pokemon){
        IVs ivs = pokemon.getIvs();
        for (Stat stat : Stats.Companion.getPERMANENT()) {
            int ivIncrease = (int) this.attributeValue(SpawningAttributes.getByIvStat(stat));
            pokemon.setIV(stat, Math.min(ivs.getOrDefault(stat) + ivIncrease, IVs.MAX_VALUE));
        }
    }
}
