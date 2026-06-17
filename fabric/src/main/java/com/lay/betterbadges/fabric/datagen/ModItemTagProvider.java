package com.lay.betterbadges.fabric.datagen;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.item.BetterBadgesItems;
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
        addBadge(BetterBadgesItems.BOULDER_BADGE.get());
        addBadge(BetterBadgesItems.CASCADE_BADGE.get());
        addBadge(BetterBadgesItems.THUNDER_BADGE.get());
        addBadge(BetterBadgesItems.RAINBOW_BADGE.get());
        addBadge(BetterBadgesItems.SOUL_BADGE.get());
        addBadge(BetterBadgesItems.MARSH_BADGE.get());
        addBadge(BetterBadgesItems.VOLCANO_BADGE.get());
        addBadge(BetterBadgesItems.EARTH_BADGE.get());

        addBadge(BetterBadgesItems.ZEPHYR_BADGE.get());
        addBadge(BetterBadgesItems.HIVE_BADGE.get());
        addBadge(BetterBadgesItems.PLAIN_BADGE.get());
        addBadge(BetterBadgesItems.FOG_BADGE.get());
        addBadge(BetterBadgesItems.STORM_BADGE.get());
        addBadge(BetterBadgesItems.MINERAL_BADGE.get());
        addBadge(BetterBadgesItems.GLACIER_BADGE.get());
        addBadge(BetterBadgesItems.RISING_BADGE.get());

        getOrCreateTagBuilder(BADGE_CASES)
                .add(BetterBadgesItems.BADGE_CASE.get());
    }
}