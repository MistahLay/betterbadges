package com.lay.betterbadges.common.registry;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.config.ModConfigs;
import com.lay.betterbadges.common.render.screen.ModScreens;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BadgeCaseBase {

    public static final String translatablePrefix = "betterBadges.design.base";

    public static void registerFromConfig(){
        for (String base : ModConfigs.DESIGN_CONFIG.base()){
            ModRegistries.BADGE_CASE_BASE.register(ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, "base/" + base), () -> new BadgeCaseBase(base, createTranslation(base), createTexturePath(base)));
        }
    }

    private static ResourceLocation createTexturePath(String texture){
        return ModScreens.getGuiTexture("base/" + texture);
    }

    private static Component createTranslation(String id){
        return Component.translatable(translatablePrefix + id);
    }

    private final String id;
    private final Component displayName;

    private final ResourceLocation texturePath;

    public BadgeCaseBase(String id, Component displayName, ResourceLocation texturePath){
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
