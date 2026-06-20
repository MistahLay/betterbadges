package com.lay.betterbadges.common.api.attribute.vanilla;

import com.lay.betterbadges.common.api.attribute.BooleanAttribute;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import static com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes.registerBoolean;
import static com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes.registerRanged;

public class MiscVanillaAttributes {

    public static Holder<Attribute> ELYTRA_FLIGHT = registerBoolean(rootPath("elytra_flight"), false);
    public static Holder<Attribute> CREATIVE_FLIGHT = creativeFlight();

    public static Holder<Attribute> SHOOTABLE_ENDER_PEARL = registerBoolean(rootPath("shootable_ender_pearl"), false);
    public static Holder<Attribute> NIGHT_VISION = registerBoolean(rootPath("night_vision"), false);
    public static Holder<Attribute> DOUBLE_JUMP = registerRanged(rootPath("double_jump"));
    public static Holder<Attribute> UNBREAKABLE_HAND = registerRanged(rootPath("unbreakable_hand"));
    public static Holder<Attribute> UNBREAKABLE_ARMOR = registerRanged(rootPath("unbreakable_armor"));

    private static String rootPath(String id){
        return "generic.vanilla." + id;
    }

    public static void registerAttributes(){ }

    public static void applyToBuilder(AttributeSupplier.Builder builder){
        builder
                .add(ELYTRA_FLIGHT)
                .add(SHOOTABLE_ENDER_PEARL)
                .add(NIGHT_VISION)
                .add(DOUBLE_JUMP, 0.0f)
                .add(UNBREAKABLE_HAND)
                .add(UNBREAKABLE_ARMOR);
        builder.add(CREATIVE_FLIGHT); // Since neoforge will already register it
    }

    public static boolean hasNaturalElytra(LivingEntity entity){
        AttributeInstance instance = entity.getAttribute(ELYTRA_FLIGHT);
        if (instance == null) return false;
        return BooleanAttribute.toBoolean(instance.getValue());
    }

    @ExpectPlatform
    public static Holder<Attribute> creativeFlight(){
        throw new AssertionError();
    }

}
