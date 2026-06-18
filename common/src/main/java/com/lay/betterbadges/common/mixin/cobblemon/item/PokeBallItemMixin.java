package com.lay.betterbadges.common.mixin.cobblemon.item;

import com.cobblemon.mod.common.item.PokeBallItem;
import com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

// Temporarily here, not yet implemented
@Mixin(PokeBallItem.class)
public class PokeBallItemMixin {

    @Inject(
            method = "use",
            at = @At("HEAD")
    )
    private void betterbadges$rollAtUse(Level world, Player player, InteractionHand usedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir, @Share("result") LocalBooleanRef resultRef){
        float toUse = (float) player.getAttributeValue(BetterBadgesAttributes.actual(BetterBadgesAttributes.POKEBALL_USE));
        Random rand = new Random();
        resultRef.set(toUse < rand.nextFloat());
    }

    @WrapWithCondition(
            method = "use",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;consume(ILnet/minecraft/world/entity/LivingEntity;)V")
    )
    private boolean betterbadges$sometimesConsume(ItemStack stack, int i, LivingEntity livingEntity, @Share("result") LocalBooleanRef resultRef){
        return resultRef.get();
    }

    @Inject(
            method = "throwPokeBall",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z")
    )
    private void betterbadges$addAttribute(Level world, ServerPlayer player, CallbackInfo ci){
    }

}
