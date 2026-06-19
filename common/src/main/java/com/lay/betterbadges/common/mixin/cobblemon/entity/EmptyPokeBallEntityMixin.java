package com.lay.betterbadges.common.mixin.cobblemon.entity;

import com.cobblemon.mod.common.entity.pokeball.EmptyPokeBallEntity;
import com.lay.betterbadges.common.api.attribute.cobblemon.MiscCobblemonAttributes;
import com.lay.betterbadges.common.config.BetterBadgesConfigs;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Random;

@Mixin(EmptyPokeBallEntity.class)
public class EmptyPokeBallEntityMixin {

    @ModifyArg(
            method = "breakFree",
            at = @At(value = "INVOKE", target = "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;after(FLkotlin/jvm/functions/Function0;)Lcom/cobblemon/mod/common/api/scheduling/ScheduledTask;", ordinal = 2),
            index = 1
    )
    private Function0<Unit> betterbadges$breakFreeModifier(Function0<Unit> action){
        EmptyPokeBallEntity entity = (EmptyPokeBallEntity) (Object) this;
        return () -> {
            if (BetterBadgesConfigs.canPokeballBeReturned(entity.getPokeBall().item())
                    && entity.getOwner() instanceof ServerPlayer player
                    && !player.isCreative()
                    && entity.level() instanceof ServerLevel serverLevel
            ) {
                float toUse = (float) player.getAttributeValue(MiscCobblemonAttributes.POKEBALL_USE);
                Random rand = new Random();
                if (toUse < rand.nextFloat()) {
                    ItemStack pokeballStack = new ItemStack(entity.getPokeBall().item());

                    ItemEntity itemEntity = new ItemEntity(
                            serverLevel,
                            entity.getX(), entity.getEyeY() - 0.3, entity.getZ(),
                            pokeballStack
                    );

                    RandomSource random = entity.getRandom();

                    float horizontalSpeed = random.nextFloat() * 0.5F;
                    float angle = random.nextFloat() * 6.2831855F;

                    double vx = -Math.sin(angle) * horizontalSpeed;
                    double vy = 0.4F;
                    double vz =  Math.cos(angle) * horizontalSpeed;

                    itemEntity.setDeltaMovement(vx, vy, vz);
                    itemEntity.setPickUpDelay(40);
                    serverLevel.addFreshEntity(itemEntity);
                }
            }
            return action.invoke();
        };
    }

}
