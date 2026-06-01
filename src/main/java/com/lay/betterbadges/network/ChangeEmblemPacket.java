package com.lay.betterbadges.network;

import com.lay.betterbadges.emblem.Emblem;
import com.lay.betterbadges.registry.ModRegistries;
import net.minecraft.resources.ResourceLocation;

public record ChangeEmblemPacket(String emblem) {
    public Emblem getRegisteredEmblem(){
        return ModRegistries.EMBLEM.get(ResourceLocation.parse(emblem));
    }
}
