package com.lay.betterbadges.common.api.attribute.fabric;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class BetterBadgesAttributesImpl {

    public static Holder<Attribute> register(ResourceLocation id, Attribute attribute){
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, id, attribute);
    }

}
