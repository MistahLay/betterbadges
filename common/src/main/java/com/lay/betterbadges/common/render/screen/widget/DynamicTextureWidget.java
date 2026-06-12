package com.lay.betterbadges.common.render.screen.widget;

import com.lay.betterbadges.common.mixin.TextureComponentMixin;
import io.wispforest.owo.ui.component.TextureComponent;
import io.wispforest.owo.ui.core.OwoUIDrawContext;
import io.wispforest.owo.ui.core.Positioning;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class DynamicTextureWidget extends TextureComponent {

    private Supplier<ResourceLocation> updateTexture;
    private Supplier<Positioning> updatePosition;

    public static DynamicTextureWidget create(ResourceLocation texture, int u, int v, int regionWidth, int regionHeight, int textureWidth, int textureHeight){
        return new DynamicTextureWidget(texture, u, v, regionWidth, regionHeight, textureWidth, textureHeight);
    }

    protected DynamicTextureWidget(ResourceLocation texture, int u, int v, int regionWidth, int regionHeight, int textureWidth, int textureHeight) {
        super(texture, u, v, regionWidth, regionHeight, textureWidth, textureHeight);
    }

    @Override
    public void draw(OwoUIDrawContext context, int mouseX, int mouseY, float partialTicks, float delta) {
        this.updateTexture();
        this.updatePosition();

        super.draw(context, mouseX, mouseY, partialTicks, delta);
    }

    public DynamicTextureWidget dynamicTexture(Supplier<ResourceLocation> updateTexture){
        this.updateTexture = updateTexture;
        return this;
    }

    public DynamicTextureWidget dynamicPosition(Supplier<Positioning> dynamicPositioning){
        this.updatePosition = dynamicPositioning;
        return this;
    }

    public void updatePosition(){
        if (this.updatePosition == null) return;;
        this.positioning.set(this.updatePosition.get());
    }

    public void updateTexture(){
        if (this.updateTexture == null) return;
        ResourceLocation newTexture = this.updateTexture.get();
        ((TextureComponentMixin) this).betterbadges$setTexture(newTexture);
    }

    @Override
    public boolean canFocus(FocusSource source) {
        return true;
    }
}
