package com.lay.betterbadges.registry;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.config.ModConfigs;
import com.lay.betterbadges.screen.ModScreens;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BadgeCaseCover {

    public static final String translatablePrefix = "betterBadges.design.cover";

    public static void registerFromConfig(){
        for (String cover : ModConfigs.DESIGN_CONFIG.cover()){
            ModRegistries.BADGE_CASE_COVER.register(ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, "cover/" + cover), () -> new BadgeCaseCover(cover, createTranslation(cover), createTexturePath(cover)));
        }
    }

    private static ResourceLocation createTexturePath(String texture){
        return ModScreens.getGuiTexture("cover/" + texture);
    }

    private static Component createTranslation(String id){
        return Component.translatable(translatablePrefix + id);
    }

    private final String id;
    private final Component displayName;

    private final ResourceLocation texturePath;

    public BadgeCaseCover(String id, Component displayName, ResourceLocation texturePath){
        this.id = id;
        this.displayName = displayName;
        this.texturePath = texturePath;
    }

    public String getId(){
        return this.id;
    }

    public ResourceLocation getTexturePath(){
        return this.texturePath;
    }

    public Component getDisplayName(){
        return this.displayName.plainCopy();
    }

}
