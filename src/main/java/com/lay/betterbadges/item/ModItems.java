package com.lay.betterbadges.item;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.component.custom.LeagueInventoryContents;
import com.lay.betterbadges.config.ModConfigs;
import com.lay.betterbadges.item.badges.BasicBadge;
import com.lay.betterbadges.item.cases.BasicCase;
import com.lay.betterbadges.league.League;
import com.lay.betterbadges.registry.ModRegistries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;


public class ModItems {

    // Kanto Badges
    public static final Item BOULDER_BADGE = registerBadge("boulder");
    public static final Item CASCADE_BADGE = registerBadge("cascade");
    public static final Item THUNDER_BADGE = registerBadge("thunder");
    public static final Item RAINBOW_BADGE = registerBadge("rainbow");
    public static final Item SOUL_BADGE = registerBadge("soul");
    public static final Item MARSH_BADGE = registerBadge("marsh");
    public static final Item VOLCANO_BADGE = registerBadge("volcano");
    public static final Item EARTH_BADGE = registerBadge("earth");

    // Johto Badges
    public static final Item ZEPHYR_BADGE = registerBadge("zephyr");
    public static final Item HIVE_BADGE = registerBadge("hive");
    public static final Item PLAIN_BADGE = registerBadge("plain");
    public static final Item FOG_BADGE = registerBadge("fog");
    public static final Item STORM_BADGE = registerBadge("storm");
    public static final Item MINERAL_BADGE = registerBadge("mineral");
    public static final Item GLACIER_BADGE = registerBadge("glacier");
    public static final Item RISING_BADGE = registerBadge("rising");

    // Badge Case
    public static final Item BADGE_CASE = registerItem("badge_case", new BasicCase(
            new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
                    .fireResistant()
    ));

    private static League getInitializedLeague(){
        return ModRegistries.LEAGUE.get(ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, ModConfigs.LEAGUE_CONFIG.leagues().get(0).id));
    }

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

            itemGroup.accept(ZEPHYR_BADGE);
            itemGroup.accept(HIVE_BADGE);
            itemGroup.accept(PLAIN_BADGE);
            itemGroup.accept(FOG_BADGE);
            itemGroup.accept(STORM_BADGE);
            itemGroup.accept(MINERAL_BADGE);
            itemGroup.accept(GLACIER_BADGE);
            itemGroup.accept(RISING_BADGE);

            itemGroup.accept(BADGE_CASE);
        });
    }

    private static Item registerItem(String name, Item item){
        return Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, name), item);
    }

    private static Item registerBadge(String badgeName){
        ResourceLocation badgeID = ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, badgeName + "_badge");
        return Registry.register(BuiltInRegistries.ITEM, badgeID, new BasicBadge(
                new Item.Properties()
                        .stacksTo(1)
                        .rarity(Rarity.RARE)
                        .fireResistant()
        ));
    }

}
