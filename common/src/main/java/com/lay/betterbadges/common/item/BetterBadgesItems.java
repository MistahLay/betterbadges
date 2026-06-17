package com.lay.betterbadges.common.item;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.item.badges.BadgeItem;
import com.lay.betterbadges.common.item.cases.BasicCase;
import com.lay.betterbadges.common.api.league.LeagueKeys;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Supplier;

public class BetterBadgesItems {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BetterBadges.MOD_ID, Registries.ITEM);

    // Kanto Badges
    public static final RegistrySupplier<BadgeItem> BOULDER_BADGE = registerBadge(LeagueKeys.KANTO,"boulder");
    public static final RegistrySupplier<BadgeItem> CASCADE_BADGE = registerBadge(LeagueKeys.KANTO, "cascade");
    public static final RegistrySupplier<BadgeItem> THUNDER_BADGE = registerBadge(LeagueKeys.KANTO, "thunder");
    public static final RegistrySupplier<BadgeItem> RAINBOW_BADGE = registerBadge(LeagueKeys.KANTO, "rainbow");
    public static final RegistrySupplier<BadgeItem> SOUL_BADGE = registerBadge(LeagueKeys.KANTO, "soul");
    public static final RegistrySupplier<BadgeItem> MARSH_BADGE = registerBadge(LeagueKeys.KANTO, "marsh");
    public static final RegistrySupplier<BadgeItem> VOLCANO_BADGE = registerBadge(LeagueKeys.KANTO, "volcano");
    public static final RegistrySupplier<BadgeItem> EARTH_BADGE = registerBadge(LeagueKeys.KANTO, "earth");

    // Badge Case
    public static final RegistrySupplier<BasicCase> BADGE_CASE = registerItem("badge_case", () -> new BasicCase(
            new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
                    .fireResistant()
                    .arch$tab(BetterBadgesCreativeTab.BETTER_BADGES_ITEM_GROUP)
    ));

    // Johto Badges
    public static final RegistrySupplier<BadgeItem> ZEPHYR_BADGE = registerBadge(LeagueKeys.JOHTO, "zephyr");
    public static final RegistrySupplier<BadgeItem> HIVE_BADGE = registerBadge(LeagueKeys.JOHTO, "hive");
    public static final RegistrySupplier<BadgeItem> PLAIN_BADGE = registerBadge(LeagueKeys.JOHTO, "plain");
    public static final RegistrySupplier<BadgeItem> FOG_BADGE = registerBadge(LeagueKeys.JOHTO, "fog");
    public static final RegistrySupplier<BadgeItem> STORM_BADGE = registerBadge(LeagueKeys.JOHTO, "storm");
    public static final RegistrySupplier<BadgeItem> MINERAL_BADGE = registerBadge(LeagueKeys.JOHTO, "mineral");
    public static final RegistrySupplier<BadgeItem> GLACIER_BADGE = registerBadge(LeagueKeys.JOHTO, "glacier");
    public static final RegistrySupplier<BadgeItem> RISING_BADGE = registerBadge(LeagueKeys.JOHTO, "rising");

    public static void registerModItems(){
        BetterBadges.LOGGER.info("Registering custom items for " + BetterBadges.MOD_ID);
        ITEMS.register();
    }

    private static <T extends Item> RegistrySupplier<T> registerItem(String name, Supplier<T> item){
        return ITEMS.register(BetterBadges.of(name), item);
    }

    private static RegistrySupplier<BadgeItem> registerBadge(ResourceLocation league, String badgeName){
        return registerItem(badgeName + "_badge", () -> new BadgeItem(new Item.Properties()
                .stacksTo(1)
                .rarity(Rarity.RARE)
                .fireResistant()
                .arch$tab(BetterBadgesCreativeTab.BETTER_BADGES_ITEM_GROUP),
                league
        ));
    }

}
