package com.lay.betterbadges.screen;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.screen.badgecase.BadgeCaseGui;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.inventory.MenuType;

public class ModScreenHandler {

    public static final MenuType<BadgeCaseGui> BADGE_CASE_GUI = Registry.register(
            BuiltInRegistries.MENU,
            ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, "badge_case_gui"),
            new ExtendedScreenHandlerType<>((syncId, inventory, slot) -> {
                SlotAccess handStack = SlotAccess.forEquipmentSlot(inventory.player, slot);
                return new BadgeCaseGui(syncId, inventory, handStack);
            }, ByteBufCodecs.fromCodec(EquipmentSlot.CODEC).cast()));


    public static ResourceLocation getGuiTexture(String path){
        return ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, "textures/gui/" + path).withSuffix(".png");
    }

    public static void registerScreenHandlers(){

    }

}
