package com.lay.betterbadges.common.util;

import com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;

public abstract class PlayerAttributeHelper {

    protected final ServerPlayer player;

    public PlayerAttributeHelper(ServerPlayer player){
        this.player = player;
    }

    protected float attributeValue(Holder<Attribute> attribute){
        return (float) this.player.getAttributeValue(attribute);
    }


}
