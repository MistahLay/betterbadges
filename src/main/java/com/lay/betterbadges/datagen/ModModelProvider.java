package com.lay.betterbadges.datagen;

import com.lay.betterbadges.item.ModItems;
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

public class ModModelProvider extends FabricModelProvider  {

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
        registerBadgeModel(ModItems.BOULDER_BADGE, itemModelGenerator);
        registerBadgeModel(ModItems.CASCADE_BADGE, itemModelGenerator);
        registerBadgeModel(ModItems.THUNDER_BADGE, itemModelGenerator);
        registerBadgeModel(ModItems.RAINBOW_BADGE, itemModelGenerator);
        registerBadgeModel(ModItems.SOUL_BADGE, itemModelGenerator);
        registerBadgeModel(ModItems.MARSH_BADGE, itemModelGenerator);
        registerBadgeModel(ModItems.VOLCANO_BADGE, itemModelGenerator);
        registerBadgeModel(ModItems.EARTH_BADGE, itemModelGenerator);

        registerUniquePathFlatModel(ModItems.BADGE_CASE, itemModelGenerator, BADGE_CASE_PREFIX_PATH);
    }

    private static void registerBadgeModel(Item item, ItemModelGenerators itemModelGenerator){
        registerUniquePathFlatModel(item, itemModelGenerator, BADGE_PREFIX_PATH);
    }

    private static void registerUniquePathFlatModel(Item item, ItemModelGenerators itemModelGenerator, String prefix){
        ResourceLocation path = getItemLocationWithNewPath(item, prefix);
        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(path), itemModelGenerator.output);
    }

    private static ResourceLocation getItemLocationWithNewPath(Item item, String path){
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(item);
        return resourceLocation.withPrefix(path);
    }
}
