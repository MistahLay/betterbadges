package com.lay.betterbadges.common.mixin;

import com.lay.betterbadges.common.render.screen.CustomHighlightSlot;
import com.lay.betterbadges.common.render.screen.badgecase.BadgeCaseScreen;
import com.lay.betterbadges.common.render.screen.badgecase.BadgeCaseScreenHandler;
import com.lay.betterbadges.common.render.screen.badgecase.BadgeShineAnimation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {

    @Shadow
    @Nullable
    protected Slot hoveredSlot;

    @Shadow
    protected abstract void renderSlot(GuiGraphics guiGraphics, Slot slot);

    @WrapOperation(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/screens/inventory/AbstractContainerScreen.renderSlotHighlight (Lnet/minecraft/client/gui/GuiGraphics;III)V"))
    public void betterbadges$wrapRenderHighlightedSlotOperation(GuiGraphics arg, int i, int j, int k, Operation<Void> original) {
        AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) (Object) this;
        if (!(screen instanceof CustomHighlightSlot highlightable)) {
            original.call(arg, i, j, k);
        } else {
            ResourceLocation texture = highlightable.highlightSlotTexture(this.hoveredSlot);
            if (texture != null) {
                arg.blit(
                        texture,
                        i - 1, j - 1, 4,
                        191, 0,
                        18, 18,
                        256, 256
                );
            }
        }
        if (!(screen instanceof BadgeCaseScreen badgeCaseScreen)) return;
        if (this.hoveredSlot instanceof BadgeCaseScreenHandler.ScreenBadgeSlot || this.hoveredSlot instanceof BadgeCaseScreenHandler.ScreenEmblemSlot) {
            badgeCaseScreen.shineHoveredSlot();
        }
    }

}
