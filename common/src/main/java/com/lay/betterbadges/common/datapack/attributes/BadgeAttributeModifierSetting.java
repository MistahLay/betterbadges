package com.lay.betterbadges.common.datapack.attributes;

import com.lay.betterbadges.common.emblem.Boost;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public record BadgeAttributeModifierSetting(Boost boost, Holder<Attribute> attribute, AttributeModifier.Operation operation, double value) {

    public static MapCodec<BadgeAttributeModifierSetting> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Boost.CODEC.fieldOf("boost").forGetter(badgeAttribute -> badgeAttribute.boost),
            BuiltInRegistries.ATTRIBUTE.holderByNameCodec().fieldOf("attribute").forGetter(badgeAttribute -> badgeAttribute.attribute),
            AttributeModifier.Operation.CODEC.fieldOf("operation").forGetter(badgeAttribute -> badgeAttribute.operation),
            Codec.DOUBLE.fieldOf("value").forGetter(badgeAttribute -> badgeAttribute.value)
    ).apply(instance, BadgeAttributeModifierSetting::new));

}
