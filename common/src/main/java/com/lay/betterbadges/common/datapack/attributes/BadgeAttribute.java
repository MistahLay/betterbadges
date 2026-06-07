package com.lay.betterbadges.common.datapack.attributes;

import com.lay.betterbadges.common.emblem.Boost;
import com.lay.betterbadges.common.item.badges.BadgeItem;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Optional;

public record BadgeAttribute(BadgeItem badgeItem, Boost boost, ResourceLocation attribute, AttributeModifier.Operation operation, double value) {

    public Optional<Holder.Reference<Attribute>> getAttribute(){
        return BuiltInRegistries.ATTRIBUTE.getHolder(attribute);
    }

    public AttributeModifier createModifier() {
        return new AttributeModifier(createModifierName(badgeItem), value, operation);
    }

    public ResourceLocation createModifierName(BadgeItem badgeItem) {
        return BuiltInRegistries.ITEM.getKey(badgeItem).withSuffix("#").withSuffix(boost.name().toLowerCase()); // BetterBadges:item_badge#adventure
    }

}
