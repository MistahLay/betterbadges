package com.lay.betterbadges.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Slot.class)
public interface SlotMixin {
    @Mutable
    @Accessor("container")
    void betterbadges$setContainer(Container container);

    @Mutable
    @Accessor("x")
    void betterbadges$setX(int x);

    @Mutable
    @Accessor("y")
    void betterbadges$setY(int y);
}