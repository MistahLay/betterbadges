package com.lay.betterbadges.common.util.texture;

import com.lay.betterbadges.common.BetterBadges;
import net.minecraft.resources.ResourceLocation;

public final class TextureHelper {

    public static ResourceLocation ofTexture(String path){
        return ofTexture(path, true);
    }

    public static ResourceLocation ofTexture(String path, boolean addSuffix){
        return addSuffix ? png(BetterBadges.of("textures/" + path)) : BetterBadges.of("textures/" + path);
    }

    public static ResourceLocation ofGui(String path){
        return ofGui("gui/" + path, true);
    }

    public static ResourceLocation ofGui(String path, boolean addSuffix){
        return ofTexture("gui/" + path, addSuffix);
    }

    public static ResourceLocation ofBadgeCase(String path){
        return ofBadgeCase("badgecase/" + path, true);
    }

    public static ResourceLocation ofBadgeCase(String path, boolean addSuffix){
        return ofGui("badgecase/" + path, addSuffix);
    }

    // Applies a png suffix
    public static ResourceLocation png(ResourceLocation path){
        return path.withSuffix(".png");
    }

}
