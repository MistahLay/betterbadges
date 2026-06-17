package com.lay.betterbadges.common.util;

import com.cobblemon.mod.common.api.pokeball.catching.CatchRateModifier;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.lay.betterbadges.common.api.attribute.cobblemon.CatchingAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

public class CatchModifierUtil {

    public static float increaseModifierValue(Float original, LivingEntity thrower, Pokemon pokemon) {
        float total = 1.0f;
        for (ElementalType type : pokemon.getTypes()){ // Which means that if there's like dual type pokemon, 1.5x1.5
            AttributeInstance instance = thrower.getAttribute(CatchingAttributes.getByElementalType(type));
            if (instance == null) continue;
            total *= (float) instance.getValue();
        }
        return CatchRateModifier.Behavior.MULTIPLY.getMutator().invoke(original, total); // Ohhh that's why people choice kotlin
    }

}
