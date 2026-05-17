package com.lay.betterbadges.league;

import com.google.common.collect.BiMap;
import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.component.custom.BadgeContents;
import com.lay.betterbadges.component.custom.LeagueInventoryContents;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public record League(String leagueName, List<Item> badges, int minmax) {

    public static final Codec<League> CODEC = Codec.STRING.xmap(
            League::getLeague,
            from -> from.leagueName
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, BadgeContents> STREAM_CODEC;

    public static final League NONE = new League("none", new ArrayList<>(), 0);

    private static final HashMap<String, League> leagues = new HashMap<>();

    private static void addLeague(League league){
        League.leagues.put(league.leagueName, league);
    }

    public static League getLeague(String leagueName){
        return League.leagues.getOrDefault(leagueName, League.NONE);
    }

    public static class Builder {
        private String leagueName;
        private int minmax;
        List<Item> badges;

        public static Builder create(String leagueName, int minmax){
            Builder builder = new Builder();
            builder.leagueName = leagueName;
            builder.minmax = minmax;
            return builder;
        }

        public static Builder create(String leagueName){
            return Builder.create(leagueName, BadgeContents.DEFAULT_SIZE);
        }

        public Builder addBadge(Item badge){
            this.badges.add(badge);
            return this;
        }

        public League build(){
            // TODO: Add overwrite check
            int count = this.badges.size();
            if(count != this.minmax){
                BetterBadges.LOGGER.error("League required badges did not met the exact amount, the amount of given badges was {} out of {}", count, this.minmax);
            }
            League league = new League(this.leagueName, this.badges, minmax);
            League.addLeague(league);
            return league;
        }
    }

    public Item getRequiredBadgeAt(int index){
        return badges.get(index);
    }

    public String toString(){
        return "League@" + this.leagueName + this.badges + this.minmax;
    }

    public boolean canInsertBadgeAtSlot(Item item, int slot){
        return item == getRequiredBadgeAt(slot);
    }

    public int getSlotForBadge(Item item){
        return badges.indexOf(item);
    }

}