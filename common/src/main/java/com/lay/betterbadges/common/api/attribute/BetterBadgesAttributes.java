package com.lay.betterbadges.common.api.attribute;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.attribute.cobblemon.BattleRewardsAttributes;
import com.lay.betterbadges.common.api.attribute.cobblemon.CatchingAttributes;
import com.lay.betterbadges.common.api.attribute.cobblemon.MiscCobblemonAttributes;
import com.lay.betterbadges.common.api.attribute.cobblemon.SpawningAttributes;
import com.lay.betterbadges.common.api.attribute.vanilla.MiscVanillaAttributes;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class BetterBadgesAttributes{

    static {
        BattleRewardsAttributes.registerAttributes();
        SpawningAttributes.registerAttributes();
        CatchingAttributes.registerAttributes();
        MiscCobblemonAttributes.registerAttributes();

        MiscVanillaAttributes.registerAttributes();
    }

    public static void applyToBuilder(AttributeSupplier.Builder builder) {
        BattleRewardsAttributes.applyToBuilder(builder);
        SpawningAttributes.applyToBuilder(builder);
        CatchingAttributes.applyToBuilder(builder);
        MiscCobblemonAttributes.applyToBuilder(builder);

        MiscVanillaAttributes.applyToBuilder(builder);
    }

    public static void registerAttributes() { }

    public static Holder<Attribute> registerBoolean(String attributeId, boolean defaulted){
        return register(BetterBadges.of(attributeId), new BooleanAttribute(attributeTranslation(attributeId), defaulted).setSyncable(true));
    }

    public static Holder<Attribute> registerRanged(String attributeId, double defaulted, double min, double max){
        return register(BetterBadges.of(attributeId), new RangedAttribute(attributeTranslation(attributeId), defaulted, min, max).setSyncable(true));
    }

    public static Holder<Attribute> registerRanged(String attributeId, double defaulted){
        return registerRanged(attributeId, defaulted, 0.0d, 1024.0d);
    }

    public static Holder<Attribute> registerRanged(String attributeId){
        return registerRanged(attributeId,1.0d, 0.0d, 1024.0d);
    }

    public static Holder<Attribute> registerPercentage(String attributeId, double defaulted){
        return registerRanged(attributeId, defaulted, 0.0d, 1.0d);
    }

    public static Holder<Attribute> registerPercentage(String attributeId){
        return registerPercentage(attributeId, 0.0d);
    }

    public static Holder<Attribute> get(String id){
        return BuiltInRegistries.ATTRIBUTE.getHolderOrThrow(ResourceKey.create(Registries.ATTRIBUTE, BetterBadges.of(id)));
    }

    @ExpectPlatform
    public static Holder<Attribute> register(ResourceLocation id, Attribute attribute){
        throw new AssertionError();
    }

    private static String attributeTranslation(String name){
        return "attribute.name." + name;
    }
}
