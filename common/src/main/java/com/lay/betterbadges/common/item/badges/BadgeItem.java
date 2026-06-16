package com.lay.betterbadges.common.item.badges;

import com.lay.betterbadges.common.api.emblem.Boost;
import com.lay.betterbadges.common.item.bounded.BoundItem;
import com.lay.betterbadges.common.api.league.BadgeSlot;
import com.lay.betterbadges.common.api.league.League;
import com.lay.betterbadges.common.api.league.attributes.BadgeAttribute;
import com.lay.betterbadges.common.api.league.attributes.BadgeAttributesManager;
import com.lay.betterbadges.common.registry.ModRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BadgeItem extends BoundItem {

    public static final Codec<BadgeItem> CODEC = BuiltInRegistries.ITEM.byNameCodec().comapFlatMap(
            item -> {
                if (item instanceof BadgeItem) return DataResult.success((BadgeItem) item);
                return DataResult.error(() -> "Not a badge item");
            }, Item::asItem
    );

    private final ResourceLocation leagueLocation;

    public BadgeItem(Properties properties, ResourceLocation leagueLocation) {
        super(properties);
        this.leagueLocation = leagueLocation;
    }

    public League getLeague(){
        League league = ModRegistries.LEAGUE.get(this.leagueLocation);
        if(league == null || league == League.EMPTY) throw new RuntimeException("League: " + this.leagueLocation.toString() + " does not exists");
        return league;
    }

    public BadgeSlot getBadgeSlot(){
        return this.getLeague().getBadgeFromItem(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        HashMap<Boost, BadgeAttribute> badgeAttributes = BadgeAttributesManager.getAttributes(this);
        if (badgeAttributes == null) return;
        for (Map.Entry<Boost, BadgeAttribute> badgeAttribute : badgeAttributes.entrySet()) {
            tooltipComponents.add(Component.empty());
            Boost boost = badgeAttribute.getKey();
            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("tooltip.betterbadges.boost." + boost.name().toLowerCase()).withStyle(boost.displayColor));
            AttributeModifier attributeModifier = badgeAttribute.getValue().createModifier();

            Holder<Attribute> holder = badgeAttribute.getValue().setting().attribute();
            double d = attributeModifier.amount();

            double e;
            if (attributeModifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    || attributeModifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                e = d * 100.0;
            } else if (holder.is(Attributes.KNOCKBACK_RESISTANCE)) {
                e = d * 10.0;
            } else {
                e = d;
            }

            if (d > 0.0) {
                tooltipComponents.add(Component.translatable(
                                "attribute.modifier.plus." + attributeModifier.operation().id(),
                                ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(e),
                                Component.translatable(holder.value().getDescriptionId())
                        )
                        .withStyle(holder.value().getStyle(true)));
            } else if (d < 0.0) {
                tooltipComponents.add(Component.translatable(
                                "attribute.modifier.take." + attributeModifier.operation().id(),
                                ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(-e),
                                Component.translatable(holder.value().getDescriptionId())
                        )
                        .withStyle(holder.value().getStyle(false)));
            }
        }
    }
}
