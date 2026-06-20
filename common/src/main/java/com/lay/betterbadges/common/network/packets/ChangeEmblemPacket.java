package com.lay.betterbadges.common.network.packets;

import com.lay.betterbadges.common.api.emblem.Emblem;
import com.lay.betterbadges.common.registry.BetterBadgesRegistries;
import net.minecraft.resources.ResourceLocation;

public record ChangeEmblemPacket(String emblem) {
    public Emblem getRegisteredEmblem(){
        return BetterBadgesRegistries.EMBLEM.get(ResourceLocation.parse(emblem));
    }
}
