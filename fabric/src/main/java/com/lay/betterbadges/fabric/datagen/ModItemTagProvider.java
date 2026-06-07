package com.lay.betterbadges.fabric.datagen;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider<Item> {

    public static final TagKey<Item> BADGES = createTag("badges");
    public static final TagKey<Item> BADGE_CASES = createTag("badge_cases");

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ITEM, registriesFuture);
    }

    private static TagKey<Item> createTag(String tagName){
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, tagName));
    }

    public static TagKey<Item> createRegionTag(String tagName){
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, "regions/" + tagName));
    }

    private void addBadge(Item item){
        getOrCreateTagBuilder(BADGES).add(item);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        addBadge(ModItems.BOULDER_BADGE.get());
        addBadge(ModItems.CASCADE_BADGE.get());
        addBadge(ModItems.THUNDER_BADGE.get());
        addBadge(ModItems.RAINBOW_BADGE.get());
        addBadge(ModItems.SOUL_BADGE.get());
        addBadge(ModItems.MARSH_BADGE.get());
        addBadge(ModItems.VOLCANO_BADGE.get());
        addBadge(ModItems.EARTH_BADGE.get());

        addBadge(ModItems.ZEPHYR_BADGE.get());
        addBadge(ModItems.HIVE_BADGE.get());
        addBadge(ModItems.PLAIN_BADGE.get());
        addBadge(ModItems.FOG_BADGE.get());
        addBadge(ModItems.STORM_BADGE.get());
        addBadge(ModItems.MINERAL_BADGE.get());
        addBadge(ModItems.GLACIER_BADGE.get());
        addBadge(ModItems.RISING_BADGE.get());

        getOrCreateTagBuilder(BADGE_CASES)
                .add(ModItems.BADGE_CASE.get());
    }
}