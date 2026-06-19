package com.lay.betterbadges.fabric.mixin.entity;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.attribute.BooleanAttribute;
import com.lay.betterbadges.common.api.attribute.vanilla.MiscVanillaAttributes;
import com.lay.betterbadges.fabric.extension.PlayerExtension;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
class PlayerMixin implements PlayerExtension {

    @Inject(
            method = "causeFallDamage",
            at = @At("HEAD"),
            cancellable = true
    )
    public void betterbadges$causeFallDamage(float f, float g, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir){
        Player player = (Player) (Object) this;
        if (player.canFly()) cir.cancel();
    }

    @Override
    public boolean canFly() {
        Player player = (Player) (Object) this;
        return player.getAbilities().mayfly || BooleanAttribute.toBoolean(player.getAttributeValue(MiscVanillaAttributes.CREATIVE_FLIGHT));
    }
}
