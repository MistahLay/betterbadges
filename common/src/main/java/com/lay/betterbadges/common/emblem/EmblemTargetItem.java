package com.lay.betterbadges.common.emblem;

import com.lay.betterbadges.common.league.League;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record EmblemTargetItem(League league, int targetSlot, int currentSlot) {

    public static final EmblemTargetItem EMPTY = new EmblemTargetItem(League.EMPTY.get(), 0, 0);

    public static final Codec<EmblemTargetItem> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        League.CODEC.fieldOf("league").forGetter(EmblemTargetItem::league),
        Codec.INT.fieldOf("targetSlot").forGetter(EmblemTargetItem::targetSlot),
        Codec.INT.fieldOf("currentSlot").forGetter(EmblemTargetItem::currentSlot))
    .apply(instance, EmblemTargetItem::new));

}