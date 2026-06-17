package com.lay.betterbadges.common.item;

import com.lay.betterbadges.common.BetterBadges;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import dev.architectury.registry.CreativeTabRegistry;

public class BetterBadgesCreativeTab {

    public static final DeferredRegister<CreativeModeTab> BETTER_BADGES_TAB_REGISTRY = DeferredRegister.create(BetterBadges.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> BETTER_BADGES_ITEM_GROUP = // This is very long if I say so myself
            BETTER_BADGES_TAB_REGISTRY.register("betterbadges_tab",
                    () -> CreativeTabRegistry.create(Component.translatable("itemGroup.better-badges"),
                            () -> new ItemStack(BetterBadgesItems.VOLCANO_BADGE.get())
                    ));

    public static void registerItemGroups(){
        BETTER_BADGES_TAB_REGISTRY.register();
    }
}
