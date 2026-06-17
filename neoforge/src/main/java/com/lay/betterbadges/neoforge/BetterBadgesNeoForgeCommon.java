package com.lay.betterbadges.neoforge;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.attribute.ModAttributes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

@EventBusSubscriber(modid = BetterBadges.MOD_ID)
public class BetterBadgesNeoForgeCommon {

    @SubscribeEvent
    public static void onEntityAttributeSetup(EntityAttributeModificationEvent event){ }

}
