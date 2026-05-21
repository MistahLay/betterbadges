package com.lay.betterbadges.inventory;

import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.component.custom.LeagueInventoryContents;
import com.lay.betterbadges.item.ModItems;
import com.lay.betterbadges.league.League;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements the league badge inventory
 */
public class LeagueBadgesManager {

    private final ItemStack activeCase;
    private final Map<League, BadgeContainer> inventories;

    LeagueBadgesManager(ItemStack activeCase){
        this.activeCase = activeCase;
        this.inventories = createEmptyBadgesInventory(activeCase);
    }

    LeagueBadgesManager(ItemStack activeCase, Map<League, BadgeContainer> inventories){
        this.activeCase = activeCase;
        this.inventories = inventories;
    }

    @Nullable
    public static LeagueBadgesManager createFromItem(ItemStack itemStack, Player player){
        if(!itemStack.is(ModItems.BADGE_CASE)) return null;
        LeagueInventoryContents contents = itemStack.get(ModDataComponents.LEAGUE_INVENTORY_CONTENTS);
        if(contents == null){
            return new LeagueBadgesManager(itemStack, LeagueBadgesManager.createEmptyBadgesInventory(itemStack));
        }
        Map<String, League> leagues = new HashMap<>(League.getAllLeagues());
        Map<League, BadgeContainer> inventories = new HashMap<>();
        RegistryAccess registryAccess = player.registryAccess();
        for (Map.Entry<League, CompoundTag> entry : contents.getLeagueBadgeContents().entrySet()){
            League league = entry.getKey();
            if(!leagues.containsValue(league)) continue;
            CompoundTag tag = entry.getValue();
            NonNullList<ItemStack> items = NonNullList.withSize(league.minmax(), ItemStack.EMPTY);
            ContainerHelper.loadAllItems(tag, items, registryAccess);
            BadgeContainer leagueInventory = new BadgeContainer(itemStack.get(ModDataComponents.ITEM_OWNER), items.toArray(new ItemStack[0]));
            leagues.remove(league.leagueName());
            inventories.put(league, leagueInventory);
        }

        if(leagues.isEmpty()) return new LeagueBadgesManager(itemStack, inventories);
        for (League league : leagues.values()){
            inventories.put(league, new BadgeContainer(itemStack.get(ModDataComponents.ITEM_OWNER), league.minmax()));
        }
        return new LeagueBadgesManager(itemStack, inventories);
    }

    public static Map<League, BadgeContainer> createEmptyBadgesInventory(ItemStack itemStack){
        Map<League, BadgeContainer> mainInventory = new HashMap<>();
        for (League league : League.getAllLeagues().values()) mainInventory.put(league, new BadgeContainer(itemStack.get(ModDataComponents.ITEM_OWNER), league.minmax()));
        return mainInventory;
    }

    public LeagueInventoryContents getLeagueInventoryContents(Player player){
        Map<League, CompoundTag> serializedResult = new HashMap<>();
        RegistryAccess registryAccess = player.registryAccess();
        for (League league : this.inventories.keySet()){
            CompoundTag tag = new CompoundTag();
            ContainerHelper.saveAllItems(tag, this.inventories.get(league).items, registryAccess);
            serializedResult.put(league, tag);
        }
        return new LeagueInventoryContents(serializedResult);
    }

    public ItemStack getActiveCase(){
        return this.activeCase;
    }

    public @NotNull BadgeContainer getBadgeContainer(League league){
        return this.inventories.get(league);
    }

}
