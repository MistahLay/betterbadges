package com.lay.betterbadges.mixin;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.config.ConfigEndecs;
import com.lay.betterbadges.config.EmblemConfigModel;
import com.lay.betterbadges.config.LeaguesConfigModel;
import io.wispforest.endec.impl.ReflectiveEndecBuilder;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.serialization.endec.MinecraftEndecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ConfigWrapper.class)
abstract class ConfigWrapperMixin {

    @Shadow
    @Final
    @Mutable
    protected ReflectiveEndecBuilder builder;

    @Inject(method = "<init>(Ljava/lang/Class;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "blue/endless/jankson/Jankson$Builder.registerDeserializer (Ljava/lang/Class;Ljava/lang/Class;Lblue/endless/jankson/api/DeserializerFunction;)Lblue/endless/jankson/Jankson$Builder;"))
    public void onConstruct(Class clazz, Consumer janksonBuilder, CallbackInfo ci){
        if(clazz == LeaguesConfigModel.class){
            this.builder = MinecraftEndecs.addDefaults(new ReflectiveEndecBuilder()
                    .register(ConfigEndecs.LEAGUE_ENDEC, LeaguesConfigModel.League.class)
                    .register(ConfigEndecs.BADGE_ENDEC, LeaguesConfigModel.Badge.class)
            );
        } else if (clazz == EmblemConfigModel.class){
            this.builder = MinecraftEndecs.addDefaults(new ReflectiveEndecBuilder()
                    .register(ConfigEndecs.EMBLEM_ENDEC, EmblemConfigModel.Emblem.class)
                    .register(ConfigEndecs.SLOT_POSITION_ENDEC, EmblemConfigModel.SlotPosition.class)
            );
        }
    }

}
