package com.lay.betterbadges.common.render.atlas.managers;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.util.texture.TextureHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;

public class ShineSpritesManager extends TextureAtlasHolder {

    public ShineSpritesManager(TextureManager textureManager) {
        super(textureManager, TextureHelper.ofAtlas("shine_sprites"), BetterBadges.of("shine_sprites"));
    }

}
