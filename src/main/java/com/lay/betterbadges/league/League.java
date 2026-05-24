package com.lay.betterbadges.league;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.config.LeaguesConfigModel;
import com.lay.betterbadges.config.ModConfigs;
import com.lay.betterbadges.registry.ModRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import javax.naming.ConfigurationException;
import java.util.*;


public class League {

    public static final Codec<League> CODEC = Codec.STRING.xmap(
            to -> {
                League league = ModRegistries.LEAGUE.get(ResourceLocation.parse(to));
                return league == null ? League.EMPTY : league;
            },
            from -> from.id
    );

    public static final League EMPTY = Builder.create("empty").addBadge(new Badge(Items.AIR, 0, 0, 0)).build();

    private final String id;
    private final List<Badge> badges;

    private League(String id, List<Badge> badges){
        this.id = id;
        this.badges = badges;
    }

    public Badge getRequiredBadgeAt(int index){
        return badges.get(index);
    }

    public String toString(){
        return "League@" + this.id + this.badges;
    }

    public boolean canInsertBadgeAtSlot(Item item, int slot){
        return item == getRequiredBadgeAt(slot).getItem();
    }

    public int getSlotForBadge(Item item){
        int slot = -1;
        for(Badge badge : this.badges) {
            if(badge.getItem() == item) {
                slot = badge.getSlot();
            }
        }
        return slot;
    }

    public List<Badge> getBadges(){
        return this.badges;
    }

    public int getTotalBadges(){
        return this.badges.size();
    }

    public static class Builder {

        public static void fromConfig(){
            List<LeaguesConfigModel.League> configLeagues = ModConfigs.LEAGUE_CONFIG.leagues();

            if(configLeagues.isEmpty()) {
                BetterBadges.LOGGER.error("Provided Empty Leagues at Mod Config");
                ModConfigs.throwException("Provided Empty Leagues at Mod Config");
            }

            for(LeaguesConfigModel.League configLeague : configLeagues){
                Builder builder = Builder.create(configLeague.id);

                for (int i = 0; i < configLeague.badges.size(); i++) {
                    LeaguesConfigModel.Badge configBadge = configLeague.badges.get(i);

                    Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(configBadge.id));

                    if(item == Items.AIR) {
                        BetterBadges.LOGGER.error("Provided Invalid Item. Provided id: {}. In {} league", configBadge.id, configLeague.id);
                        ModConfigs.throwException("Provided Invalid Item.");
                    }

                    builder.addBadge(new Badge(
                            BuiltInRegistries.ITEM.get(ResourceLocation.parse(configBadge.id)),
                            configBadge.x,
                            configBadge.y,
                            i
                    ));
                }

                builder.build();
            }
        }

        private String id;
        List<Badge> badges = new ArrayList<>();

        public static Builder create(String id){
            Builder builder = new Builder();
            builder.id = id;
            return builder;
        }

        public Builder addBadge(Badge badge){
            this.badges.add(badge);
            return this;
        }

        public League build(){
            // TODO: Add overwrite check
            int count = this.badges.size();
            if(count == 0){
                BetterBadges.LOGGER.error("League: {} has no badges added", this.id);
            }
            League league = new League(this.id, this.badges);
            Registry.register(ModRegistries.LEAGUE, this.createPath(), league);
            return league;
        }

        private ResourceLocation createPath(){
            return ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, this.id);
        }
    }

}