package com.lay.betterbadges.common.mixin.item;

import com.lay.betterbadges.common.api.attribute.vanilla.MiscVanillaAttributes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(
            method = "hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/server/level/ServerPlayer;Ljava/util/function/Consumer;)V"),
            cancellable = true
    )
    private void betterbadges$hurtAndBreak(int i, LivingEntity livingEntity, EquipmentSlot equipmentSlot, CallbackInfo ci){
        if (livingEntity instanceof ServerPlayer player){ // TODO: Add blacklist tags and item
            if (
                (equipmentSlot == EquipmentSlot.MAINHAND && player.getAttributeValue(MiscVanillaAttributes.UNBREAKABLE_HAND) > 0) ||
                (equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR && player.getAttributeValue(MiscVanillaAttributes.UNBREAKABLE_HAND) > 0)
            ) ci.cancel();
        }
    }

}
