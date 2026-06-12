package com.lay.betterbadges.common.emblem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.ChatFormatting;

public enum Boost {

    ADVENTURE(ChatFormatting.RED), // Anything related to mostly vanilla attributes, like mining and speed
    CATCHING(ChatFormatting.BLUE), // Related to catching Pokemon in general
    SPAWNING(ChatFormatting.GREEN); // Modification of Pokemon spawning around the player

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

    Boost(ChatFormatting displayColor) {
        this.displayColor = displayColor;
    }
}
