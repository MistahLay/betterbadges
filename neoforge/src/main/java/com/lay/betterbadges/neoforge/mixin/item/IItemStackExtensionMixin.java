package com.lay.betterbadges.neoforge.mixin.item;

import com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IItemStackExtension.class)
public interface IItemStackExtensionMixin {

    @Inject(
            method = "canElytraFly",
            at = @At("RETURN"),
            cancellable = true
    )
    default void betterbadges$canElytraFly(LivingEntity entity, CallbackInfoReturnable<Boolean> cir){
        if (BetterBadgesAttributes.hasNaturalElytra(entity)) cir.setReturnValue(true);
    }

    @WrapOperation(
            method = "elytraFlightTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;elytraFlightTick(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;I)Z")
    )
    private boolean betterbadges$elytraFlightTick(Item instance, ItemStack itemStack, LivingEntity entity, int i, Operation<Boolean> original){
        if (BetterBadgesAttributes.hasNaturalElytra(entity)) {
            return Items.ELYTRA.elytraFlightTick(itemStack, entity, i);
        }
        return original.call(instance, itemStack, entity, i);
    }
}
