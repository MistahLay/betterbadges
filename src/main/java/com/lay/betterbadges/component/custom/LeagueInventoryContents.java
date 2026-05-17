package com.lay.betterbadges.component.custom;

import com.lay.betterbadges.league.League;
import com.mojang.serialization.Codec;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class LeagueInventoryContents {

    public static final Codec<LeagueInventoryContents> CODEC = Codec.unboundedMap(League.CODEC, BadgeContents.CODEC).xmap(
            LeagueInventoryContents::new,
            from -> from.leagueBadgeContents
    );

    final Map<League, BadgeContents> leagueBadgeContents;

    LeagueInventoryContents(Map<League, BadgeContents> badgeContents){
        this.leagueBadgeContents = badgeContents;
    }

    public int amountOfLeagues(){
        return this.leagueBadgeContents.size();
    }

    public Map<League, BadgeContents> getLeagueBadgeContents() {
        return this.leagueBadgeContents;
    }

    public boolean hasLeague(League league){
        return this.leagueBadgeContents.containsKey(league);
    }

    public BadgeContents getBadgeContentsOfLeague(League league){
        return this.leagueBadgeContents.getOrDefault(league, new BadgeContents(NonNullList.withSize(0, ItemStack.EMPTY)));
    }

    public String toString() {
        return "LeagueInventoryContents@" + this.leagueBadgeContents;
    }

    public static class Mutable {

        private Map<League, BadgeContents> leagueBadgeContents;

        Mutable(Map<League, BadgeContents> leagueBadgeContents){
            this.leagueBadgeContents = new HashMap<>(leagueBadgeContents);
        }

        public BadgeContents.Mutable getMutatedBadgeContents(League league){
            return new BadgeContents.Mutable(this.leagueBadgeContents.getOrDefault(league, new BadgeContents(NonNullList.create())));
        }

        public Map<League, BadgeContents> getAllLeagueBadgeContents(){
            return this.leagueBadgeContents;
        }

        public void setAllLeagueBadgeContents(Map<League, BadgeContents> badgeContents){
            this.leagueBadgeContents = badgeContents;
        }

        public void setMutatedBadgeContents(League league, BadgeContents.Mutable badgeContents){
            this.leagueBadgeContents.put(league, badgeContents.toImmutable());
        }

        public LeagueInventoryContents getImmutable(){
            return new LeagueInventoryContents(this.leagueBadgeContents);
        }
    }
}
