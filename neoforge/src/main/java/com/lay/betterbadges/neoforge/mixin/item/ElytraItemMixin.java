package com.lay.betterbadges.neoforge.mixin.item;

import com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ElytraItem.class)
public class ElytraItemMixin {

    /**
     * Cancels the elytra tick damage when the attribute is applied
     **/
    @WrapWithCondition(
            method = "elytraFlightTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V")
    )
    private boolean betterbadges$elytraFlightTick(ItemStack instance, int i, LivingEntity livingEntity, EquipmentSlot equipmentSlot){
        return !BetterBadgesAttributes.hasNaturalElytra(livingEntity);
    }
}
