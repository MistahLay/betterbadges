package com.lay.betterbadges.common.mixin.entity;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.attribute.BetterBadgesAttributes;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {

    @ModifyReturnValue(
            method = "createAttributes()Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;",
            at = @At("RETURN")
    )
    private static AttributeSupplier.Builder betterbadges$createAttributes(AttributeSupplier.Builder builder){
        for (Holder<Attribute> attribute : BetterBadgesAttributes.ATTRIBUTES){
            builder.add(BuiltInRegistries.ATTRIBUTE.getHolderOrThrow(attribute.unwrapKey().get()));
        }
        return builder;
    }

}
