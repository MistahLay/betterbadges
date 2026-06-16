package com.lay.betterbadges.fabric.mixin;

import com.lay.betterbadges.common.render.screen.badgecase.BadgeCaseScreen;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {

    @WrapOperation(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/screens/inventory/AbstractContainerScreen.renderSlotHighlight (Lnet/minecraft/client/gui/GuiGraphics;III)V"))
    public void betterbadges$wrapRenderHighlightedSlotOperation(GuiGraphics arg, int i, int j, int k, Operation<Void> original) {
        AbstractContainerScreen screen = (AbstractContainerScreen) (Object) this;
        if (screen instanceof BadgeCaseScreen badgeCaseScreen && badgeCaseScreen.renderHighlightedSlotOverride(arg, i, j, 0)) return;
        original.call(arg, i, j, k);
    }

}
