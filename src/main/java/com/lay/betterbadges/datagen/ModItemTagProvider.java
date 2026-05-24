package com.lay.betterbadges.datagen;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.item.ModItems;
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
        addBadge(ModItems.BOULDER_BADGE);
        addBadge(ModItems.CASCADE_BADGE);
        addBadge(ModItems.THUNDER_BADGE);
        addBadge(ModItems.RAINBOW_BADGE);
        addBadge(ModItems.SOUL_BADGE);
        addBadge(ModItems.MARSH_BADGE);
        addBadge(ModItems.VOLCANO_BADGE);
        addBadge(ModItems.EARTH_BADGE);

        addBadge(ModItems.ZEPHYR_BADGE);
        addBadge(ModItems.HIVE_BADGE);
        addBadge(ModItems.PLAIN_BADGE);
        addBadge(ModItems.FOG_BADGE);
        addBadge(ModItems.STORM_BADGE);
        addBadge(ModItems.MINERAL_BADGE);
        addBadge(ModItems.GLACIER_BADGE);
        addBadge(ModItems.RISING_BADGE);

        getOrCreateTagBuilder(BADGE_CASES)
                .add(ModItems.BADGE_CASE);
    }
}
