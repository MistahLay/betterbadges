package com.lay.betterbadges.common.league.attributes;

import com.lay.betterbadges.common.item.badges.BadgeItem;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.wispforest.endec.StructEndec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;

public record BadgeAttribute(BadgeItem badgeItem, BadgeAttributeModifierSetting setting) {

    public static MapCodec<BadgeAttribute> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BadgeItem.CODEC.fieldOf("badgeItem").forGetter(badgeAttribute -> badgeAttribute.badgeItem),
            BadgeAttributeModifierSetting.CODEC.fieldOf("setting").forGetter(badgeAttribute -> badgeAttribute.setting)
        ).apply(instance, BadgeAttribute::new));

    public AttributeModifier createModifier() {
        return new AttributeModifier(createModifierName(badgeItem), setting.value(), setting.operation());
    }

    public ResourceLocation createModifierName(BadgeItem badgeItem) {
        return BuiltInRegistries.ITEM.getKey(badgeItem).withSuffix("/").withSuffix(setting.boost().name().toLowerCase()); // BetterBadges:item_badge/adventure
    }

}
