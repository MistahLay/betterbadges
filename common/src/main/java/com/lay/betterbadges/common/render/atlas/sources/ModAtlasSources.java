package com.lay.betterbadges.common.render.atlas.sources;

import com.lay.betterbadges.common.mixin.SpriteSourcesMixin;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;

public class ModAtlasSources {

    public static final SpriteSourceType ANIMATION_OVERLAY = SpriteSourcesMixin.betterbadges$register("animation_overlay", AnimationOverlayPermutations.CODEC);

    public static void init(){ }

}
