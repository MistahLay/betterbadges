package com.lay.betterbadges.emblem;

import net.minecraft.ChatFormatting;

public enum Boost {

    ADVENTURE(ChatFormatting.RED), // Anything related to mostly vanilla attributes, like mining and speed
    CATCHING(ChatFormatting.BLUE), // Related to catching Pokemon in general
    SPAWNING(ChatFormatting.GREEN); // Modification of Pokemon spawning around the player

    public final ChatFormatting displayColor;

    Boost(ChatFormatting displayColor) {
        this.displayColor = displayColor;
    }
}
