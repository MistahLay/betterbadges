package com.lay.betterbadges.item;

import com.lay.betterbadges.BetterBadges;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTab {

    public static final ResourceKey<CreativeModeTab> BETTER_BADGES_ITEM_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, "item_group"));
    public static final CreativeModeTab BETTER_BADGES_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.VOLCANO_BADGE))
            .title(Component.translatable("itemGroup.better-badges"))
            .build();

    public static void registerItemGroups(){
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, BETTER_BADGES_ITEM_GROUP_KEY, BETTER_BADGES_ITEM_GROUP);
        BetterBadges.LOGGER.info("Creating Item Groups for " + BetterBadges.MOD_ID);
    }

}
