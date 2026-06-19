package com.lay.betterbadges.common.api.attribute.neoforge;

import com.lay.betterbadges.common.BetterBadges;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BetterBadgesAttributesImpl {

    public static DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, BetterBadges.MOD_ID);

    public static Holder<Attribute> register(ResourceLocation id, Attribute attribute){
        return ATTRIBUTES.register(id.getPath(), () -> attribute);
    }

}
