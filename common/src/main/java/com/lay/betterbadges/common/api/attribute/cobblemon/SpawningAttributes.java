package com.lay.betterbadges.common.api.attribute.cobblemon;

import com.cobblemon.mod.common.api.pokemon.stats.Stat;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.api.spawning.BestSpawner;
import com.cobblemon.mod.common.api.spawning.SpawnBucket;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.pokemon.IVs;
import com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import static com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes.registerRanged;

public class SpawningAttributes {

    public static Holder<Attribute> SHINY_SPAWNING = registerRanged(rootPath("shiny"), 1.0, 0.0, 8192.0f);

    public static Holder<Attribute> getByRarityBucket(SpawnBucket bucket){
        return BetterBadgesAttributes.get(pathByBucket(bucket.getName()));
    }

    private static Holder<Attribute> registerByRarityBucket(SpawnBucket bucket){
        return registerRanged(pathByBucket(bucket.getName()), 0.0, 0.0, 100.0);
    }

    private static String pathByBucket(String spawnBucket){
        return rootPath("spawnbucket." + spawnBucket);
    }

    public static Holder<Attribute> getByIvStat(Stat stat){
        return BetterBadgesAttributes.get(pathIvByStat(stat));
    }

    private static Holder<Attribute> registerIvByStat(Stat stat){
        return registerRanged(pathIvByStat(stat), 0.0, 0.0, IVs.MAX_VALUE);
    }

    private static String pathIvByStat(Stat stat){
        return rootPath("iv.stat." + stat.getIdentifier().getPath());
    }

    public static Holder<Attribute> getByElementalType(ElementalType type){
        return BetterBadgesAttributes.get(pathByElementalType(type));
    }

    private static Holder<Attribute> registerByElementalType(ElementalType type){
        return registerRanged(pathByElementalType(type));
    }

    private static String pathByElementalType(ElementalType type){
        return rootPath("type." + type.getName().toLowerCase());
    }

    private static String rootPath(String string){
        return "player.cobbleattribbutes.spawning." + string;
    }

    public static void registerAttributes(){
        for (ElementalType type : ElementalTypes.all()){
            registerByElementalType(type);
        }

        for (SpawnBucket spawnBucket : BestSpawner.INSTANCE.getConfig().getBuckets()){
            registerByRarityBucket(spawnBucket);
        }

        for (Stat stat : Stats.Companion.getPERMANENT()){
            registerIvByStat(stat);
        }
    }

    public static void applyToBuilder(AttributeSupplier.Builder builder){
        for (ElementalType type : ElementalTypes.all()){
            builder.add(getByElementalType(type));
        }

        for (SpawnBucket spawnBucket : BestSpawner.INSTANCE.getConfig().getBuckets()){
            builder.add(getByRarityBucket(spawnBucket));
        }

        for (Stat stat : Stats.Companion.getPERMANENT()){
            builder.add(getByIvStat(stat));
        }

        builder.add(SHINY_SPAWNING);
    }
}
