package com.lay.betterbadges.component.custom;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.inventory.LeagueBadgesManager;
import com.lay.betterbadges.league.League;
import com.lay.betterbadges.registry.ModRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class LeagueInventoryContents {

    public static final Codec<LeagueInventoryContents> CODEC = Codec.unboundedMap(League.CODEC, CompoundTag.CODEC).xmap(
            LeagueInventoryContents::new,
            from -> from.leagueBadges
    );

    final Map<League, CompoundTag> leagueBadges;

    public LeagueInventoryContents(Map<League, CompoundTag> badgeContents){
        this.leagueBadges = badgeContents;
    }

    public int amountOfLeagues(){
        return this.leagueBadges.size();
    }

    public Map<League, CompoundTag> getLeagueBadgeContents() {
        return this.leagueBadges;
    }

    public boolean hasLeague(League league){
        return this.leagueBadges.containsKey(league);
    }

    public CompoundTag getBadgeContentsOfLeague(League league){
        return this.leagueBadges.get(league);
    }

    public String toString() {
        return "LeagueInventoryContents@" + this.leagueBadges;
    }

    public static LeagueInventoryContents createEmpty(){
        Map<League, CompoundTag> inventories = new HashMap<>();

        return new LeagueInventoryContents(inventories);
    }

    public static class Mutable {

        private Map<League, CompoundTag> leagueBadges;

        Mutable(LeagueInventoryContents original){
            this.leagueBadges = new HashMap<>(original.leagueBadges);
        }

        public CompoundTag getLeagueBadgeContents(League league){
            return this.leagueBadges.get(league);
        }

        public void setLeagueBadgeContents(League league, CompoundTag tag){
            this.leagueBadges.put(league, tag);
        }

        public Map<League, CompoundTag> getAllLeagueBadgeContents(){
            return this.leagueBadges;
        }

        public void setAllLeagueBadgeContents(Map<League, CompoundTag> badgeContents){
            this.leagueBadges = badgeContents;
        }

        public LeagueInventoryContents getImmutable(){
            return new LeagueInventoryContents(this.leagueBadges);
        }
    }
}
