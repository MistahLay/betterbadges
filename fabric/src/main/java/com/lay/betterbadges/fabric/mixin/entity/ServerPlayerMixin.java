package com.lay.betterbadges.fabric.mixin.entity;

import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Inject(
            method = "doTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getArmorValue()I", ordinal = 0)
    )
    private void betterbadges$doTickCanFlyInject(CallbackInfo ci){
        ServerPlayer player = (ServerPlayer) (Object) this;
        if (player.getAbilities().flying && !player.canFly()){
            player.getAbilities().flying = false;
            player.onUpdateAbilities();
        }
    }

}
