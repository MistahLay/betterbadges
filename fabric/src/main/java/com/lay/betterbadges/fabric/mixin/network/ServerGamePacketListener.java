package com.lay.betterbadges.fabric.mixin.network;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.Abilities;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListener {

    @WrapOperation(
            method = "handleMovePlayer",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;mayfly:Z", opcode = Opcodes.GETFIELD)
    )
    private boolean betterbadges$handleMovePlayerIsFlightAllowed(Abilities instance, Operation<Boolean> original){
        ServerGamePacketListenerImpl listener = (ServerGamePacketListenerImpl) (Object) this;
        return listener.player.canFly();
    }

    @Inject(
            method = "handlePlayerAbilities",
            at = @At("TAIL")
    )
    private void betterbadges$handlePlayerAbilities(ServerboundPlayerAbilitiesPacket serverboundPlayerAbilitiesPacket, CallbackInfo ci){
        ServerGamePacketListenerImpl listener = (ServerGamePacketListenerImpl) (Object) this;
        listener.player.getAbilities().flying = serverboundPlayerAbilitiesPacket.isFlying() && listener.player.canFly();
    }

}
