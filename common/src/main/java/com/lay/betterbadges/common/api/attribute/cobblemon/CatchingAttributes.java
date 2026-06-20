package com.lay.betterbadges.common.api.attribute.cobblemon;

import com.cobblemon.mod.common.api.pokemon.status.Status;
import com.cobblemon.mod.common.api.pokemon.status.Statuses;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes;
import com.lay.betterbadges.common.api.attribute.MultiAttributes;
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

    public static MultiAttributes<ElementalType> TYPE_SPECIFIC_CATCHING = new MultiAttributes<>(
            rootPath("type."),
            original -> original.getName().toLowerCase(),
            BetterBadgesAttributes::registerRanged,
            ElementalTypes.all()
    );

    public static MultiAttributes<Status> STATUS_SPECIFIC_CATCHING = new MultiAttributes<>(
            rootPath("status."),
            original -> original.getName().getPath().toLowerCase(),
            BetterBadgesAttributes::registerRanged,
            Statuses.getPersistentStatuses()
    );

    private static String rootPath(String string){
        return "player.cobbleattribbutes.catching." + string;
    }

    public static void registerAttributes() { }

    public static void applyToBuilder(AttributeSupplier.Builder builder){
        TYPE_SPECIFIC_CATCHING.applyToBuilder(builder);
        STATUS_SPECIFIC_CATCHING.applyToBuilder(builder);

        builder
                .add(FLAT_CATCHING)
                .add(OUTSIDE_BATTLE_CATCHING)
                .add(IN_BATTLE_CATCHING)
                .add(SPECIAL_OUTSIDE_BATTLE_CATCHING)
                .add(SPECIAL_IN_BATTLE_CATCHING)
                .add(FLAT_STATUS_CATCHING);
    }
}
