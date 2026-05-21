package com.lay.betterbadges.item;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.item.badges.BasicBadge;
import com.lay.betterbadges.item.cases.BasicCase;
import com.lay.betterbadges.league.LeagueRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;


public class ModItems {

    // Basic Badges
    public static final Item BOULDER_BADGE = registerBadge("boulder", 0);
    public static final Item CASCADE_BADGE = registerBadge("cascade", 1);
    public static final Item THUNDER_BADGE = registerBadge("thunder", 2);
    public static final Item RAINBOW_BADGE = registerBadge("rainbow", 3);
    public static final Item SOUL_BADGE = registerBadge("soul", 4);
    public static final Item MARSH_BADGE = registerBadge("marsh", 5);
    public static final Item VOLCANO_BADGE = registerBadge("volcano", 6);
    public static final Item EARTH_BADGE = registerBadge("earth", 7);

    // Badge Case
    public static final Item BADGE_CASE = registerItem("badge_case", new BasicCase(
            new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
                    .fireResistant()
    ));

    public static void registerModItems(){
        BetterBadges.LOGGER.info("Registering custom items for " + BetterBadges.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ModCreativeTab.BETTER_BADGES_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.accept(BOULDER_BADGE);
            itemGroup.accept(CASCADE_BADGE);
            itemGroup.accept(THUNDER_BADGE);
            itemGroup.accept(RAINBOW_BADGE);
            itemGroup.accept(SOUL_BADGE);
            itemGroup.accept(MARSH_BADGE);
            itemGroup.accept(VOLCANO_BADGE);
            itemGroup.accept(EARTH_BADGE);

            itemGroup.accept(BADGE_CASE);
        });
    }

    private static Item registerItem(String name, Item item){
        return Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, name), item);
    }

    private static Item registerBadge(String badgeName, int slot){
        ResourceLocation badgeID = ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, badgeName + "_badge");
        return Registry.register(BuiltInRegistries.ITEM, badgeID, new BasicBadge(
                new Item.Properties()
                        .stacksTo(1)
                        .rarity(Rarity.RARE)
                        .fireResistant(),
                slot
        ));
    }

}
