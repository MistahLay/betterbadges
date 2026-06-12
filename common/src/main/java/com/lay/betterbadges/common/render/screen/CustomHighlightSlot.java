package com.lay.betterbadges.common.render.screen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

public interface CustomHighlightSlot {

    @Nullable
    ResourceLocation highlightSlotTexture(Slot slot);

}
