package com.lay.betterbadges.common.render.atlas.managers;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.render.atlas.sources.AnimationOverlayPermutations;
import com.lay.betterbadges.common.util.texture.TextureHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ShineSpritesManager extends TextureAtlasHolder {

    public static ShineSpritesManager MANAGER;

    public static void register(TextureManager textureManager){ // :{
        MANAGER = new ShineSpritesManager(textureManager);
    }

    public static ResourceLocation ID = BetterBadges.of("shine_sprites");

    public ShineSpritesManager(TextureManager textureManager) {
        super(textureManager, TextureHelper.ofAtlas("shine_sprites"), ID);
    }

    public TextureAtlasSprite get(Item item, String additionalPrefix, int frame){
        return this.getSprite(AnimationOverlayPermutations.createPath(BuiltInRegistries.ITEM.getKey(item).withPrefix("textures/item/" + additionalPrefix), "shine", frame));
    }
}
