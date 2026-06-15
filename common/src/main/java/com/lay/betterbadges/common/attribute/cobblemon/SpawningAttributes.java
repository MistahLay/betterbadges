package com.lay.betterbadges.common.attribute.cobblemon;

import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.api.pokemon.status.Status;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.pokemon.IVs;
import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.attribute.ModAttributes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class SpawningAttributes {

    public static Holder<Attribute> SHINY_SPAWNING = ModAttributes.registerRanged(rootPath("shiny"), 0.0, 0.0, 1.0);
    public static Holder<Attribute> NATURAL_SPAWNING = ModAttributes.registerRanged(rootPath("shiny"));

    public static Holder<Attribute> COMMON_BUCKET = registerByRarityBucket("common");
    public static Holder<Attribute> UNCOMMON_BUCKET = registerByRarityBucket("uncommon");
    public static Holder<Attribute> RARE_BUCKET = registerByRarityBucket("rare");
    public static Holder<Attribute> ULTRA_RARE_BUCKET = registerByRarityBucket("ultra-rare");

    public static Holder<Attribute> HP_IV = registerIvByStat(Stats.HP);
    public static Holder<Attribute> SPEED_IV = registerIvByStat(Stats.SPEED);
    public static Holder<Attribute> SPECIAL_ATTACK_IV = registerIvByStat(Stats.SPECIAL_ATTACK);
    public static Holder<Attribute> SPECIAL_DEFENCE_IV = registerIvByStat(Stats.SPECIAL_DEFENCE);
    public static Holder<Attribute> ATTACK_IV = registerIvByStat(Stats.ATTACK);
    public static Holder<Attribute> DEFENCE_IV = registerIvByStat(Stats.DEFENCE);

    public static Holder<Attribute> getByRarityBucketName(String spawnBucket){
        return BuiltInRegistries.ATTRIBUTE.getHolderOrThrow(ResourceKey.create(Registries.ATTRIBUTE, BetterBadges.of(pathByBucket(spawnBucket))));
    }

    private static Holder<Attribute> registerByRarityBucket(String bucket){
        return ModAttributes.registerRanged(pathByBucket(bucket), 0.0, 0.0, 1.0);
    }

    private static String pathByBucket(String spawnBucket){
        return rootPath("spawnbucket." + spawnBucket);
    }

    public static Holder<Attribute> getByIvStat(Stats stat){
        return BuiltInRegistries.ATTRIBUTE.getHolderOrThrow(ResourceKey.create(Registries.ATTRIBUTE, BetterBadges.of(pathIvByStat(stat))));
    }

    private static Holder<Attribute> registerIvByStat(Stats stat){
        return ModAttributes.registerRanged(pathIvByStat(stat), 0.0, 0.0, IVs.MAX_VALUE);
    }

    private static String pathIvByStat(Stats stat){
        return rootPath("iv.stat." + stat.name().toLowerCase());
    }

    public static Holder<Attribute> getByElementalType(ElementalType status){
        return BuiltInRegistries.ATTRIBUTE.getHolderOrThrow(ResourceKey.create(Registries.ATTRIBUTE, BetterBadges.of(pathByElementalType(status))));
    }

    private static Holder<Attribute> registerByElementalType(ElementalType type){
        return ModAttributes.registerRanged(pathByElementalType(type));
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
    }

}
