package com.lay.betterbadges.inventory;

import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.component.custom.LeagueInventoryContents;
import com.lay.betterbadges.item.ModItems;
import com.lay.betterbadges.league.League;
import com.lay.betterbadges.registry.ModRegistries;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements the league badge inventory
 * You can probably call it a wrapper for LeagueInventoryContents
 */
public class LeagueBadgesManager {

    private final ItemStack activeCase;
    private final Map<League, SimpleContainer> inventories;

    private LeagueBadgesManager(ItemStack activeCase, Map<League, SimpleContainer> inventories){
        this.activeCase = activeCase;
        this.inventories = inventories;
    }

    public static LeagueBadgesManager createEmpty(ItemStack badgeCase){
        Map<League, SimpleContainer> inventories = new HashMap<>();
        for (League league : ModRegistries.LEAGUE){
            inventories.put(league, new SimpleContainer(league.getTotalBadges()));
        }
        return new LeagueBadgesManager(badgeCase, inventories);
    }

    public static LeagueBadgesManager createFromItem(ItemStack itemStack, RegistryAccess registryAccess){
        LeagueInventoryContents contents = itemStack.get(ModDataComponents.LEAGUE_INVENTORY_CONTENTS);
        if(contents == null) return createEmpty(itemStack);

        Map<League, CompoundTag> inventoryContents = contents.getLeagueBadgeContents();

        Map<League, SimpleContainer> inventories = new HashMap<>();

        ModRegistries.LEAGUE.forEach(league -> {
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

        return new LeagueBadgesManager(itemStack, inventories);
    }

    // Serialization part
    public LeagueInventoryContents getLeagueInventoryContents(RegistryAccess registryAccess){
        Map<League, CompoundTag> serializedResult = new HashMap<>();
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

    public @NotNull SimpleContainer getBadgeContainer(League league){
        return this.inventories.get(league);
    }

}
