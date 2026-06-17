package com.lay.betterbadges.fabric.datagen;

import com.lay.betterbadges.common.item.BetterBadgesItems;
import com.lay.betterbadges.fabric.mixin.ItemModelGeneratorsWidener;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModModelProvider extends FabricModelProvider {

    private static String BADGE_PREFIX_PATH = "item/badge/";
    private static String BADGE_CASE_PREFIX_PATH = "item/badge_case/";

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels() {
        generateBlockStateModels(null);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        registerBadgeModel(BetterBadgesItems.BOULDER_BADGE.get(), itemModelGenerator);
        registerBadgeModel(BetterBadgesItems.CASCADE_BADGE.get(), itemModelGenerator);
        registerBadgeModel(BetterBadgesItems.THUNDER_BADGE.get(), itemModelGenerator);
        registerBadgeModel(BetterBadgesItems.RAINBOW_BADGE.get(), itemModelGenerator);
        registerBadgeModel(BetterBadgesItems.SOUL_BADGE.get(), itemModelGenerator);
        registerBadgeModel(BetterBadgesItems.MARSH_BADGE.get(), itemModelGenerator);
        registerBadgeModel(BetterBadgesItems.VOLCANO_BADGE.get(), itemModelGenerator);
        registerBadgeModel(BetterBadgesItems.EARTH_BADGE.get(), itemModelGenerator);

        registerBadgeModel(BetterBadgesItems.ZEPHYR_BADGE.get(), itemModelGenerator);
        registerBadgeModel(BetterBadgesItems.HIVE_BADGE.get(), itemModelGenerator);
        registerBadgeModel(BetterBadgesItems.PLAIN_BADGE.get(), itemModelGenerator);
        registerBadgeModel(BetterBadgesItems.FOG_BADGE.get(), itemModelGenerator);
        registerBadgeModel(BetterBadgesItems.STORM_BADGE.get(), itemModelGenerator);
        registerBadgeModel(BetterBadgesItems.MINERAL_BADGE.get(), itemModelGenerator);
        registerBadgeModel(BetterBadgesItems.GLACIER_BADGE.get(), itemModelGenerator);
        registerBadgeModel(BetterBadgesItems.RISING_BADGE.get(), itemModelGenerator);

        registerUniquePathFlatModel(BetterBadgesItems.BADGE_CASE.get(), itemModelGenerator, BADGE_CASE_PREFIX_PATH);
    }

    private static void registerBadgeModel(Item item, ItemModelGenerators itemModelGenerator){
        registerUniquePathFlatModel(item, itemModelGenerator, BADGE_PREFIX_PATH);
    }

    private static void registerUniquePathFlatModel(Item item, ItemModelGenerators itemModelGenerator, String prefix){
        ResourceLocation path = getItemLocationWithNewPath(item, prefix);
        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(path), ((ItemModelGeneratorsWidener) itemModelGenerator).betterbadges$output());
    }

    private static ResourceLocation getItemLocationWithNewPath(Item item, String path){
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(item);
        return resourceLocation.withPrefix(path);
    }
}
