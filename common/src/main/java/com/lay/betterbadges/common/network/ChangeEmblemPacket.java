package com.lay.betterbadges.common.network;

import com.lay.betterbadges.common.emblem.Emblem;
import com.lay.betterbadges.common.registry.ModRegistries;
import net.minecraft.resources.ResourceLocation;

public record ChangeEmblemPacket(String emblem) {
    public Emblem getRegisteredEmblem(){
        return ModRegistries.EMBLEM.get(ResourceLocation.parse(emblem));
    }
}
