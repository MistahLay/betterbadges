package com.lay.betterbadges.fabric.mixin.entity;

import com.lay.betterbadges.common.attribute.ModAttributes;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {
    @Definition(id = "is", method = "net/minecraft/world/item/ItemStack.is")
    @Definition(id = "ELYTRA", field = "net/minecraft/world/item/Items.ELYTRA : Lnet/minecraft/world/item/Item;")
    @Definition(id = "itemStack", local = @Local(type = ItemStack.class, name = "itemStack"))
    @Expression("itemStack.is(ELYTRA)")
    @ModifyExpressionValue(
            method = "tryToStartFallFlying",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private boolean betterbadges$startFallingIsFlying(boolean original){
        Player entity = (Player) (Object) this;
        return original || ModAttributes.hasNaturalElytra(entity);
    }

    @Definition(id = "isFlyEnabled", method = "net/minecraft/world/item/ElytraItem.isFlyEnabled")
    @Expression("isFlyEnabled(?)")
    @ModifyExpressionValue(
            method = "tryToStartFallFlying",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private boolean betterbadges$startFallingIsFlyEnabled(boolean original){
        Player entity = (Player) (Object) this;
        return original || ModAttributes.hasNaturalElytra(entity);
    }
}
