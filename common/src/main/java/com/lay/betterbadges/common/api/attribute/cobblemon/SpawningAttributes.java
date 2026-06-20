package com.lay.betterbadges.common.api.attribute.cobblemon;

import com.cobblemon.mod.common.api.pokemon.stats.Stat;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.api.spawning.BestSpawner;
import com.cobblemon.mod.common.api.spawning.SpawnBucket;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.pokemon.IVs;
import com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes;
import com.lay.betterbadges.common.api.attribute.MultiAttributes;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import static com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes.registerRanged;

public class SpawningAttributes {

    public static Holder<Attribute> SHINY_SPAWNING = registerRanged(rootPath("shiny"), 1.0, 0.0, 8192.0f);

    public static MultiAttributes<SpawnBucket> RARITY_BUCKET_SPAWNING = new MultiAttributes<>(
            rootPath("spawnbucket."),
            SpawnBucket::getName,
            id -> registerRanged(id, 0.0d, 0.0d, 100.0d),
            BestSpawner.INSTANCE.getConfig().getBuckets()
    );

    public static MultiAttributes<Stat> IV_BOOSTED_SPAWNING = new MultiAttributes<>(
            rootPath("iv."),
            original -> original.getIdentifier().getPath(),
            id -> registerRanged(id, 0.0, 0.0, IVs.MAX_VALUE),
            Stats.Companion.getPERMANENT()
    );

    public static MultiAttributes<ElementalType> TYPE_BOOSTED_SPAWNING = new MultiAttributes<>(
            rootPath("type."),
            original -> original.getName().toLowerCase(),
            BetterBadgesAttributes::registerRanged,
            ElementalTypes.all()
    );

    private static String rootPath(String string){
        return "player.cobbleattribbutes.spawning." + string;
    }

    public static void registerAttributes(){ }

    public static void applyToBuilder(AttributeSupplier.Builder builder){
        TYPE_BOOSTED_SPAWNING.applyToBuilder(builder);
        RARITY_BUCKET_SPAWNING.applyToBuilder(builder);
        IV_BOOSTED_SPAWNING.applyToBuilder(builder);

        builder.add(SHINY_SPAWNING);
    }
}
