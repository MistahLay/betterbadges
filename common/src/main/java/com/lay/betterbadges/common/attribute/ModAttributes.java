package com.lay.betterbadges.common.attribute;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.attribute.cobblemon.BattlingAttributes;
import com.lay.betterbadges.common.attribute.cobblemon.CatchingAttributes;
import com.lay.betterbadges.common.attribute.cobblemon.SpawningAttributes;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import java.util.ArrayList;
import java.util.List;

public class ModAttributes {

    private static List<Holder<Attribute>> toAddToPlayers = new ArrayList<>();

    // Vanilla
    public static Holder<Attribute> ELYTRA_FLIGHT = registerBoolean("generic.vanilla.elytra.natural", false);
    public static Holder<Attribute> ELYTRA_SPEED = registerRanged("generic.vanilla.elytra.speed", 1.0d); // Might Be too Op

    // Cobblemon
    // Probability For the chance of using a pokeball TODO: Add a blacklist
    public static Holder<Attribute> POKEBALL_USE = registerRanged("player.cobblemon.pokeball.use", 1.0, 0.0, 1.0);

    static {
        BattlingAttributes.registerBattlingAttributes();
        SpawningAttributes.registerSpawningAttributes();
        CatchingAttributes.registerCatchingAttributes();
    }

    public static void registerAttributes() {}

    public static AttributeSupplier.Builder registerAllToPlayer(AttributeSupplier.Builder builder){
        for (Holder<Attribute> attribute : toAddToPlayers){
            builder.add(attribute);
        }
        return builder;
    }

    private static Holder<Attribute> register(String string, Attribute attribute, boolean isSyncable) {
        Holder<Attribute> holder = Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, BetterBadges.of(string), attribute.setSyncable(isSyncable));
        toAddToPlayers.add(holder);
        return holder;
    }

    private static Holder<Attribute> register(String string, Attribute attribute) {
        return register(string, attribute, true);
    }

    public static Holder<Attribute> registerRanged(String attributeId, double defaulted, double min, double max){
        return register(attributeId, new RangedAttribute(attributePrefix(attributeId), defaulted, min, max));
    }

    public static Holder<Attribute> registerRanged(String attributeId, double max){
        return registerRanged(attributeId, 0.0, 0.0, max);
    }

    public static Holder<Attribute> registerRanged(String attributeId){
        return register(attributeId, new RangedAttribute(attributePrefix(attributeId), 0.0d, 0.0d, 1024.0d));
    }

    public static Holder<Attribute> registerBoolean(String attributeId, boolean defaulted){
        return register(attributeId, new BooleanAttribute(attributePrefix(attributeId), defaulted));
    }

    private static String attributePrefix(String name){
        return "attribute.name." + name;
    }

    public static boolean hasNaturalElytra(LivingEntity entity){
        AttributeInstance instance = entity.getAttribute(ModAttributes.ELYTRA_FLIGHT);
        if (instance == null) return false;
        return BooleanAttribute.toBoolean(instance.getValue());
    }
}
