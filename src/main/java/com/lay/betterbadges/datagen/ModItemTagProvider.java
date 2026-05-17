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

    public static final TagKey<Item> KANTO_BADGES = createRegionTag("kanto_badges");

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ITEM, registriesFuture);
    }

    private static TagKey<Item> createTag(String tagName){
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, tagName));
    }

    public static TagKey<Item> createRegionTag(String tagName){
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, "regions/" + tagName));
    }

    private void addKantoBadge(Item item){
        getOrCreateTagBuilder(BADGES).add(item);
        getOrCreateTagBuilder(KANTO_BADGES).add(item);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        addKantoBadge(ModItems.BOULDER_BADGE);
        addKantoBadge(ModItems.CASCADE_BADGE);
        addKantoBadge(ModItems.THUNDER_BADGE);
        addKantoBadge(ModItems.RAINBOW_BADGE);
        addKantoBadge(ModItems.SOUL_BADGE);
        addKantoBadge(ModItems.MARSH_BADGE);
        addKantoBadge(ModItems.VOLCANO_BADGE);
        addKantoBadge(ModItems.EARTH_BADGE);
        getOrCreateTagBuilder(BADGE_CASES)
                .add(ModItems.BADGE_CASE);
    }
}
