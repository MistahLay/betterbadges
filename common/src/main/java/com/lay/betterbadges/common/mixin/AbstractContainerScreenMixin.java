package com.lay.betterbadges.common.mixin;

import com.lay.betterbadges.common.league.League;
import com.lay.betterbadges.common.screen.CustomHighlightSlot;
import com.lay.betterbadges.common.screen.badgecase.BadgeCaseScreen;
import com.lay.betterbadges.common.util.texture.LeagueTexture;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin  {

//    @Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At(value = "STORE", ordinal = 10))
//    public void betterbadges$render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci, @Local(ordinal = 2) int x, @Local(ordinal = 3) int y){
//        AbstractContainerScreen screen = (AbstractContainerScreen) (Object) this;
//        if (screen instanceof BadgeCaseScreen) {
//
//        }
//    }

    @Shadow
    @Nullable
    protected Slot hoveredSlot;

    @WrapOperation(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/screens/inventory/AbstractContainerScreen.renderSlotHighlight (Lnet/minecraft/client/gui/GuiGraphics;III)V"))
    public void betterbadges$wrapRenderHighlightedSlotOperation(GuiGraphics arg, int i, int j, int k, Operation<Void> original){
        AbstractContainerScreen screen = (AbstractContainerScreen) (Object) this;
        if (screen instanceof CustomHighlightSlot highlightable) {
            ResourceLocation texture = highlightable.highlightSlotTexture(this.hoveredSlot);
            if (texture != null) {
                arg.blit(
                        texture,
                        i - 1, j - 1, 4,
                        191, 0,
                        18, 18,
                        256, 256
                );
                return;
            } else if (screen instanceof BadgeCaseScreen badgecase) {
            }
        }
        original.call(arg, i, j, k);
    }

//    @ModifyExpressionValue(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At(value = "INVOKE", target = "net/minecraft/world/inventory/Slot.isHighlightable ()Z"))
//    public boolean betterbadges$highlightSlotCondition(boolean isHighlighted){
//        AbstractContainerScreen screen = (AbstractContainerScreen) (Object) this;
//        return !(screen instanceof BadgeCaseScreen) && isHighlighted;
//    }

}
