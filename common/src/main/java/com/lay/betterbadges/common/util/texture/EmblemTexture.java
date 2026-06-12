package com.lay.betterbadges.common.util.texture;

import com.lay.betterbadges.common.emblem.Boost;
import com.lay.betterbadges.common.emblem.Emblem;
import net.minecraft.resources.ResourceLocation;

public record EmblemTexture(Emblem emblem) {

    public static EmblemTexture of(Emblem emblem){
        return new EmblemTexture(emblem);
    }

    public ResourceLocation emblemTexture(){
        return TextureHelper.ofBadgeCase("emblem/" + emblem.getId().getPath());
    }

    public static ResourceLocation boostTexture(Boost boost){
        return TextureHelper.ofGui("slot/" + boost.name().toLowerCase());
    }

}
