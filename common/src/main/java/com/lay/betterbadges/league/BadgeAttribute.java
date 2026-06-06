package com.lay.betterbadges.league;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.emblem.Boost;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Optional;

public final class BadgeAttribute {

    private final Boost type;
    private final String operation;
    private final double value;
    private final ResourceLocation modifierName;
    private final ResourceLocation targetAttribute;

    public BadgeAttribute(ResourceLocation badge, Boost type, ResourceLocation targetAttribute, String operation, double value) {
        this.type = type;
        this.operation = operation;
        this.value = value;

        this.targetAttribute = targetAttribute;
        this.modifierName = this.createModifierName(badge);
    }

    public AttributeModifier createModifier() {
        return new AttributeModifier(this.modifierName, value, AttributeModifier.Operation.valueOf(operation.toUpperCase()));
    }
    public void applyAttributeModifier(LivingEntity entity){
        this.applyAttributeModifier(entity, false);
    }

    public void applyAttributeModifier(LivingEntity entity, boolean remove){
        // Fix Soon™
        AttributeInstance instance = entity.getAttribute(this.getAttribute().orElseThrow());
        if(instance == null) return;
        if(remove) instance.removeModifier(this.modifierName);
        else instance.addOrUpdateTransientModifier(this.createModifier());
    }

    public Optional<Holder.Reference<Attribute>> getAttribute(){
        return BuiltInRegistries.ATTRIBUTE.getHolder(targetAttribute);
    }

    public Boost getBoostType(){
        return this.type;
    }

    private ResourceLocation createModifierName(ResourceLocation badge) {
        return ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, badge.withSuffix("." + type.name().toLowerCase()).toString().replace(":", "."));
    }

}