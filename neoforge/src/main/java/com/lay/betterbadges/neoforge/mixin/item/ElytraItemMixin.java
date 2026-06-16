package com.lay.betterbadges.neoforge.mixin.item;

import com.lay.betterbadges.common.attribute.ModAttributes;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElytraItem.class)
public class ElytraItemMixin {
    @Inject(
            method = "canElytraFly",
            at = @At("RETURN"),
            cancellable = true
    )
    private void betterbadges$canElytraFly(ItemStack stack, LivingEntity entity, CallbackInfoReturnable<Boolean> cir){
        if (ModAttributes.hasNaturalElytra(entity)) cir.setReturnValue(true);
    }

    @WrapWithCondition(
            method = "elytraFlightTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V")
    )
    private boolean betterbadges$elytraFlightTick(ItemStack instance, int i, LivingEntity entity, EquipmentSlot equipmentSlot){
        return !ModAttributes.hasNaturalElytra(entity);
    }
}
