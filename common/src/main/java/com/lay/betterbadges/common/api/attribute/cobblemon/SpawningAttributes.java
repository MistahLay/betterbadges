package com.lay.betterbadges.common.api.attribute.cobblemon;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.api.spawning.BestSpawner;
import com.cobblemon.mod.common.api.spawning.SpawnBucket;
import com.cobblemon.mod.common.api.spawning.preset.BestSpawnerConfig;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.pokemon.IVs;
import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpawningAttributes {

    public static Holder<Attribute> SHINY_SPAWNING = BetterBadgesAttributes.registerRanged(rootPath("shiny"), 1.0, 0.0, 8192.0f);

    public static Map<Stats, Holder<Attribute>> IVS = new HashMap<>();

    public static Holder<Attribute> HP_IV = registerIvByStat(Stats.HP);
    public static Holder<Attribute> SPEED_IV = registerIvByStat(Stats.SPEED);
    public static Holder<Attribute> SPECIAL_ATTACK_IV = registerIvByStat(Stats.SPECIAL_ATTACK);
    public static Holder<Attribute> SPECIAL_DEFENCE_IV = registerIvByStat(Stats.SPECIAL_DEFENCE);
    public static Holder<Attribute> ATTACK_IV = registerIvByStat(Stats.ATTACK);
    public static Holder<Attribute> DEFENCE_IV = registerIvByStat(Stats.DEFENCE);

    public static Holder<Attribute> getByRarityBucket(SpawnBucket bucket){
        return BetterBadgesAttributes.actual(pathByBucket(bucket.getName()));
    }

    private static Holder<Attribute> registerByRarityBucket(SpawnBucket bucket){
        return BetterBadgesAttributes.registerRanged(pathByBucket(bucket.getName()), 0.0, 0.0, 100.0);
    }

    private static String pathByBucket(String spawnBucket){
        return rootPath("spawnbucket." + spawnBucket);
    }

    public static Holder<Attribute> getByIvStat(Stats stat){
        return BetterBadgesAttributes.actual(pathIvByStat(stat));
    }

    private static Holder<Attribute> registerIvByStat(Stats stat){
        Holder<Attribute> ivAttribute = BetterBadgesAttributes.registerRanged(pathIvByStat(stat), 0.0, 0.0, IVs.MAX_VALUE);
        IVS.putIfAbsent(stat, ivAttribute);
        return ivAttribute;
    }

    private static String pathIvByStat(Stats stat){
        return rootPath("iv.stat." + stat.name().toLowerCase());
    }

    public static Holder<Attribute> getByElementalType(ElementalType status){
        return BuiltInRegistries.ATTRIBUTE.getHolderOrThrow(ResourceKey.create(Registries.ATTRIBUTE, BetterBadges.of(pathByElementalType(status))));
    }

    private static Holder<Attribute> registerByElementalType(ElementalType type){
        return BetterBadgesAttributes.registerRanged(pathByElementalType(type));
    }

    private static String pathByElementalType(ElementalType type){
        return rootPath("type." + type.getName().toLowerCase());
    }

    private static String rootPath(String string){
        return "player.cobbleattribbutes.spawning." + string;
    }

    public static void registerSpawningAttributes(){
        for (ElementalType type : ElementalTypes.all()){
            registerByElementalType(type);
        }

        for (SpawnBucket spawnBucket : BestSpawner.INSTANCE.getConfig().getBuckets()){
            registerByRarityBucket(spawnBucket);
        }
    }
}
