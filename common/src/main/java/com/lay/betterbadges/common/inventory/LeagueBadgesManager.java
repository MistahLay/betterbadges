package com.lay.betterbadges.common.inventory;

import com.lay.betterbadges.common.component.BetterBadgesDataComponents;
import com.lay.betterbadges.common.api.league.League;
import com.lay.betterbadges.common.registry.BetterBadgesRegistries;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implements the league badge inventory
 * You can probably call it a wrapper for LeagueInventoryContents
 */
public class LeagueBadgesManager {

    private final Map<League, SimpleContainer> inventories;

    private LeagueBadgesManager(Map<League, SimpleContainer> inventories){
        this.inventories = inventories;
    }

    public static LeagueBadgesManager createEmpty(){
        Map<League, SimpleContainer> inventories = new HashMap<>();
        for (League league : BetterBadgesRegistries.LEAGUE){
            if(league == League.EMPTY) continue;
            inventories.put(league, new SimpleContainer(league.getTotalBadges()));
        }
        return new LeagueBadgesManager(inventories);
    }

    public static LeagueBadgesManager deserialize(ItemStack itemStack, RegistryAccess registryAccess){

        Map<League, CompoundTag> inventoryContents = itemStack.get(BetterBadgesDataComponents.LEAGUE_INVENTORY_CONTENTS.get());

        if(inventoryContents == null) return createEmpty();

        Map<League, SimpleContainer> inventories = new HashMap<>();

        BetterBadgesRegistries.LEAGUE.forEach(league -> {
            if(league == League.EMPTY) return;

            if(!inventoryContents.containsKey(league)){
                inventories.put(league, new SimpleContainer(league.getTotalBadges()));
                return;
            }

            CompoundTag tag = inventoryContents.get(league);
            NonNullList<ItemStack> items = NonNullList.withSize(league.getTotalBadges(), ItemStack.EMPTY);
            ContainerHelper.loadAllItems(tag, items, registryAccess);
            SimpleContainer leagueInventory = new SimpleContainer(items.toArray(new ItemStack[0]));

            inventories.put(league, leagueInventory);
        });
        return new LeagueBadgesManager(inventories);
    }

    // Serialization part
    public Map<League, CompoundTag> serialize(RegistryAccess registryAccess){
        Map<League, CompoundTag> serializedResult = new HashMap<>();
        for (League league : this.inventories.keySet()){
            CompoundTag tag = new CompoundTag();
            ContainerHelper.saveAllItems(tag, this.inventories.get(league).getItems(), registryAccess);
            serializedResult.put(league, tag);
        }
        return serializedResult;
    }

    public @NotNull SimpleContainer getBadgeContainer(League league){
        return this.inventories.get(league);
    }

    public Set<League> getLeagues(){
        return this.inventories.keySet();
    }

}
