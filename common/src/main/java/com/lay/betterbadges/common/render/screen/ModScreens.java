package com.lay.betterbadges.common.render.screen;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.render.screen.badgecase.BadgeCaseScreenHandler;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.inventory.MenuType;

public class ModScreens {

    private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BetterBadges.MOD_ID, Registries.MENU);

    public static final RegistrySupplier<MenuType<BadgeCaseScreenHandler>> BADGE_CASE_SCREEN_HANDLER = MENU_TYPES
            .register(BetterBadges.of("badge_case_gui"),
                    () -> MenuRegistry.ofExtended(((id, inventory, buf) -> {
                        SlotAccess handStack = SlotAccess.forEquipmentSlot(inventory.player, buf.readJsonWithCodec(EquipmentSlot.CODEC));
                        return new BadgeCaseScreenHandler(id, inventory, handStack);
            })));

    public static ResourceLocation getGuiTexture(String path){
        return BetterBadges.of("textures/gui/" + path).withSuffix(".png");
    }

    public static void registerScreenHandlers(){
        MENU_TYPES.register();
    }

}
