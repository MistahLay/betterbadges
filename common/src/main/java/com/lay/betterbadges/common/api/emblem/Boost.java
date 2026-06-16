package com.lay.betterbadges.common.api.emblem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.ChatFormatting;

public enum Boost {

    ADVENTURE(ChatFormatting.RED, 255.0f, 0.0f, 0.0f), // Anything related to mostly vanilla attributes, like mining and speed
    SPAWNING(ChatFormatting.GREEN, 0.0f, 255.0f, 0.0f), // Modification of Pokemon spawning around the player
    CATCHING(ChatFormatting.BLUE, 0.0f, 0.0f, 255.0f); // Related to catching Pokemon in general

    public static Codec<Boost> CODEC = Codec.STRING.comapFlatMap(
            str -> {
                try {
                    return DataResult.success(Boost.valueOf(str.toUpperCase()));
                } catch (IllegalArgumentException e){
                    return DataResult.error(() -> "Unknown Boost: " + str);
                }
            }, boost -> boost.name().toLowerCase()
    );

    public final ChatFormatting displayColor;
    public final float r;
    public final float g;
    public final float b;

    Boost(ChatFormatting displayColor, float r, float g, float b) {
        this.displayColor = displayColor;
        this.r = r;
        this.g = g;
        this.b = b;
    }
}
