package com.lay.betterbadges.fabric.mixin.entity;

import com.lay.betterbadges.common.api.attribute.ModAttributes;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Definition(id = "is", method = "net/minecraft/world/item/ItemStack.is")
    @Definition(id = "ELYTRA", field = "net/minecraft/world/item/Items.ELYTRA : Lnet/minecraft/world/item/Item;")
    @Definition(id = "itemStack", local = @Local(type = ItemStack.class, name = "itemStack"))
    @Expression("itemStack.is(ELYTRA)")
    @ModifyExpressionValue(
            method = "updateFallFlying",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private boolean betterbadges$updateFallFlyingIsFlying(boolean original){
        LivingEntity entity = (LivingEntity) (Object) this;
        return original || ModAttributes.hasNaturalElytra(entity);
    }


    @Definition(id = "isFlyEnabled", method = "net/minecraft/world/item/ElytraItem.isFlyEnabled")
    @Expression("isFlyEnabled(?)")
    @ModifyExpressionValue(
            method = "updateFallFlying",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private boolean betterbadges$updateFallFlyingIsFlyEnabled(boolean original){
        LivingEntity entity = (LivingEntity) (Object) this;
        return original || ModAttributes.hasNaturalElytra(entity);
    }

    @Expression("? % 2 == 0")
    @ModifyExpressionValue(
            method = "updateFallFlying",
            at = @At(value = "MIXINEXTRAS:EXPRESSION")
    )
    private boolean betterbadges$updateFallFlyingElytraDamage(boolean original){
        LivingEntity entity = (LivingEntity) (Object) this;
        return original && !ModAttributes.hasNaturalElytra(entity);
    }

}
