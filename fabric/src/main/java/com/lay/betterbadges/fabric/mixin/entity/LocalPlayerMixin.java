package com.lay.betterbadges.fabric.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @WrapOperation(
            method = "aiStep",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;mayfly:Z", opcode = Opcodes.GETFIELD)
    )
    public boolean betterbadges$aiStepCreativeFlight(Abilities instance, Operation<Boolean> original){
        Player player = (Player) (Object) this;
        return instance.mayfly || player.canFly();
    }

    @WrapOperation(
            method = "hasEnoughFoodToStartSprinting",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;mayfly:Z", opcode = Opcodes.GETFIELD)
    )
    private boolean betterbadges$hasEnoughFoodToStartSprintingCreativeFlight(Abilities instance, Operation<Boolean> original){
        Player player = (Player) (Object) this;
        return instance.mayfly || player.canFly();
    }

}
