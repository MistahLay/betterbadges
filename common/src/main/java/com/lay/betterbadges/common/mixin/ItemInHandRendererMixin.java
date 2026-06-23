package com.lay.betterbadges.common.mixin;

import com.lay.betterbadges.common.item.cases.BasicCase;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin { // A copy of HeldItemRendererMixin from Fabric

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private ItemStack mainHandItem;

    @Shadow
    private ItemStack offHandItem;

    @Inject(method = "tick", at = @At("HEAD"))
    private void betterbadges$disableItemBob(CallbackInfo ci){

        // Modify main hand
        ItemStack newMainStack = minecraft.player.getMainHandItem();

        if (this.mainHandItem.getItem() == newMainStack.getItem()) {
            if (this.mainHandItem.getItem() instanceof BasicCase) {
                this.mainHandItem = newMainStack;
            }
        }

        // Modify off hand
        ItemStack newOffStack = minecraft.player.getOffhandItem();

        if (this.offHandItem.getItem() == newOffStack.getItem()) {
            if (this.offHandItem.getItem() instanceof BasicCase) {
                this.offHandItem = newOffStack;
            }
        }
    }

}
