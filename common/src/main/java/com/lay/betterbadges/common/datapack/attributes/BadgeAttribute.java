package com.lay.betterbadges.common.datapack.attributes;

import com.lay.betterbadges.common.item.badges.BadgeItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public record BadgeAttribute(BadgeItem badgeItem, BadgeAttributeModifierSetting setiing) {

    public AttributeModifier createModifier() {
        return new AttributeModifier(createModifierName(badgeItem), setiing.value(), setiing.operation());
    }

    public ResourceLocation createModifierName(BadgeItem badgeItem) {
        return BuiltInRegistries.ITEM.getKey(badgeItem).withSuffix("/").withSuffix(setiing.boost().name().toLowerCase()); // BetterBadges:item_badge/adventure
    }

}
