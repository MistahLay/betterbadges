package com.lay.betterbadges.common.mixin;

import io.wispforest.owo.ui.component.TextureComponent;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TextureComponent.class)
public interface TextureComponentMixin {

    @Mutable
    @Accessor("texture")
    void betterbadges$setTexture(ResourceLocation texture);

}
