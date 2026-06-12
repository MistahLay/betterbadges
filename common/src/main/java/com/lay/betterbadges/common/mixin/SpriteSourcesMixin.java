package com.lay.betterbadges.common.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.client.renderer.texture.atlas.SpriteSources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpriteSources.class)
public interface SpriteSourcesMixin {

    @Invoker("register")
    static SpriteSourceType betterbadges$register(String string, MapCodec<? extends SpriteSource> mapCodec){
        throw new AssertionError();
    }

}
