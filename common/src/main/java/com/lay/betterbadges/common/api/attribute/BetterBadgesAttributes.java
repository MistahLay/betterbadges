package com.lay.betterbadges.common.api.attribute;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.attribute.cobblemon.BattleRewardsAttributes;
import com.lay.betterbadges.common.api.attribute.cobblemon.CatchingAttributes;
import com.lay.betterbadges.common.api.attribute.cobblemon.SpawningAttributes;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import java.util.function.Supplier;

public class BetterBadgesAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES  = DeferredRegister.create(BetterBadges.MOD_ID, Registries.ATTRIBUTE);

    // Vanilla
    public static Holder<Attribute> ELYTRA_FLIGHT = registerBoolean("generic.vanilla.elytra.natural", false);
    public static Holder<Attribute> ELYTRA_SPEED = registerRanged("generic.vanilla.elytra.speed", 1.0d); // Might Be too Op

    // Cobblemon
    // Probability For the chance of using a pokeball TODO: Add a blacklist
    public static Holder<Attribute> POKEBALL_USE = registerRanged("player.cobblemon.pokeball.use", 1.0, 0.0, 1.0);

    static {
        BattleRewardsAttributes.registerBattlingAttributes();
        SpawningAttributes.registerSpawningAttributes();
        CatchingAttributes.registerCatchingAttributes();

        ATTRIBUTES.register();
    }

    public static void registerAttributes() { }

    private static Holder<Attribute> register(String string, Supplier<Attribute> attribute) {
        return ATTRIBUTES.register(string, attribute);
    }

    public static Holder<Attribute> registerRanged(String attributeId, double defaulted, double min, double max){
        return register(attributeId, () -> new RangedAttribute(attributePrefix(attributeId), defaulted, min, max).setSyncable(true));
    }

    public static Holder<Attribute> registerRanged(String attributeId, double max){
        return registerRanged(attributeId, 0.0, 0.0, max);
    }

    public static Holder<Attribute> registerRanged(String attributeId){
        return register(attributeId, () -> new RangedAttribute(attributePrefix(attributeId), 1.0d, 0.0d, 1024.0d).setSyncable(true));
    }

    public static Holder<Attribute> registerBoolean(String attributeId, boolean defaulted){
        return register(attributeId, () -> new BooleanAttribute(attributePrefix(attributeId), defaulted).setSyncable(true));
    }

    private static String attributePrefix(String name){
        return "attribute.name." + name;
    }

    public static boolean hasNaturalElytra(LivingEntity entity){
        AttributeInstance instance = entity.getAttribute(BuiltInRegistries.ATTRIBUTE.getHolderOrThrow(BetterBadgesAttributes.ELYTRA_FLIGHT.unwrapKey().get()));
        if (instance == null) return false;
        return BooleanAttribute.toBoolean(instance.getValue());
    }

    // TODO: Fix the constants to work properly
    public static Holder<Attribute> actual(Holder<Attribute> original){
        return BuiltInRegistries.ATTRIBUTE.getHolderOrThrow(original.unwrapKey().get());
    }
}
