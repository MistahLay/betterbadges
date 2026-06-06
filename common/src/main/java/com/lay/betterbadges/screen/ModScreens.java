package com.lay.betterbadges.screen;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.screen.badgecase.BadgeCaseScreenHandler;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.platform.Platform;
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
            .register(ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, "badge_case_gui"),
                    () -> MenuRegistry.ofExtended(((id, inventory, buf) -> {
                        SlotAccess handStack = SlotAccess.forEquipmentSlot(inventory.player, buf.readJsonWithCodec(EquipmentSlot.CODEC));
                        return new BadgeCaseScreenHandler(id, inventory, handStack);
            })));

    public static ResourceLocation getGuiTexture(String path){
        return ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, "textures/gui/" + path).withSuffix(".png");
    }

    public static void registerScreenHandlers(){
        MENU_TYPES.register();
    }

}
