package com.lay.betterbadges.common.render.atlas.managers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;

public class ModAtlasManagers {

    public static TextureManager TEXTURE_MANAGER = Minecraft.getInstance().getTextureManager();

    public static ShineSpritesManager SHINE_SPRITES = new ShineSpritesManager(TEXTURE_MANAGER);

}