package com.lay.betterbadges.neoforge.mixin;

import com.lay.betterbadges.common.render.screen.badgecase.BadgeCaseScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {

    @Inject(
            method = "renderSlotHighlight(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/inventory/Slot;IIF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderSlotHighlight(Lnet/minecraft/client/gui/GuiGraphics;IIII)V"),
            cancellable = true
    )
    private void betterbadges$wrapRenderHighlightedSlotOperation(GuiGraphics guiGraphics, Slot slot, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        AbstractContainerScreen screen = (AbstractContainerScreen) (Object) this;
        if (screen instanceof BadgeCaseScreen badgeCaseScreen && badgeCaseScreen.renderHighlightedSlotOverride(guiGraphics, mouseX, mouseY, 0)) ci.cancel();
    }

}
