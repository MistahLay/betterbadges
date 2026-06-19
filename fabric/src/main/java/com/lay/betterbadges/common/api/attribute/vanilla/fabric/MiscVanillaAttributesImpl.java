package com.lay.betterbadges.common.api.attribute.vanilla.fabric;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.attribute.BooleanAttribute;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class MiscVanillaAttributesImpl {

    public static Holder<Attribute> creativeFlight(){
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, BetterBadges.of("player.creative_flight"), new BooleanAttribute("player.creative_flight", false).setSyncable(true));
    }

}
