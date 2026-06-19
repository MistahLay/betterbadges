package com.lay.betterbadges.common.api.attribute.cobblemon;

import com.cobblemon.mod.common.api.pokemon.status.Status;
import com.cobblemon.mod.common.api.pokemon.status.Statuses;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import static com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes.registerRanged;

public class CatchingAttributes {
    public static Holder<Attribute> FLAT_CATCHING = registerRanged(rootPath("flat")); // A flat increase of all catch rates
    public static Holder<Attribute> OUTSIDE_BATTLE_CATCHING = registerRanged(rootPath("outside_battle")); // An increase in outside battle catching
    public static Holder<Attribute> IN_BATTLE_CATCHING = registerRanged(rootPath("in_battle")); // An increase in battle catching

    public static Holder<Attribute> SPECIAL_OUTSIDE_BATTLE_CATCHING = registerRanged(rootPath("outside_battle.special")); // A increase in outside battle catching of special balls
    public static Holder<Attribute> SPECIAL_IN_BATTLE_CATCHING = registerRanged(rootPath("in_battle.special")); // A increase in outside battle catching of special balls

    public static Holder<Attribute> FLAT_STATUS_CATCHING = registerRanged(rootPath("status.flat"));

    public static Holder<Attribute> getByElementalType(ElementalType type){
        return BetterBadgesAttributes.get(pathByElementalType(type));
    }

    public static Holder<Attribute> getByStatus(Status status){
        return BetterBadgesAttributes.get(pathByStatus(status));
    }

    private static Holder<Attribute> registerByElementalType(ElementalType type){
        return registerRanged(pathByElementalType(type));
    }

    private static String pathByElementalType(ElementalType type){
        return rootPath("type." + type.getName().toLowerCase());
    }

    public static Holder<Attribute> registerByStatus(Status status){
        return registerRanged(pathByStatus(status));
    }

    private static String pathByStatus(Status status){
        return rootPath("status." + status.getName().getPath().toLowerCase());
    }

    private static String rootPath(String string){
        return "player.cobbleattribbutes.catching." + string;
    }

    public static void registerAttributes() {
        for (ElementalType type : ElementalTypes.all()){
            registerByElementalType(type);
        }

        for (Status status : Statuses.getPersistentStatuses()){
            registerByStatus(status);
        }
    }

    public static void applyToBuilder(AttributeSupplier.Builder builder){
        for (ElementalType type : ElementalTypes.all()){
            builder.add(getByElementalType(type));
        }

        for (Status status : Statuses.getPersistentStatuses()){
            builder.add(getByStatus(status));
        }

        builder
                .add(FLAT_CATCHING)
                .add(OUTSIDE_BATTLE_CATCHING)
                .add(IN_BATTLE_CATCHING)
                .add(SPECIAL_OUTSIDE_BATTLE_CATCHING)
                .add(SPECIAL_IN_BATTLE_CATCHING)
                .add(FLAT_STATUS_CATCHING);
    }
}
