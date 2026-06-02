package com.lay.betterbadges.league;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.config.LeaguesConfigModel;
import com.lay.betterbadges.config.ModConfigs;
import com.lay.betterbadges.config.ModConfigurationException;
import com.lay.betterbadges.emblem.BoostTypes;
import com.lay.betterbadges.registry.ModRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class League {

    public static final TagKey<Item> LEAGUE_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, "league"));

    public static final Codec<League> CODEC = Codec.STRING.xmap(
            to -> {
                League league = ModRegistries.LEAGUE.get(ResourceLocation.parse(to));
                return league == null ? League.EMPTY : league;
            },
            from -> from.id.toString()
    );

    public static final League EMPTY = Builder.create("empty").addBadge(Badge.EMPTY).build();

    /**
     * e.g. "betterbadges:name_league"
     */
    private final ResourceLocation id;
    private final List<Badge> badges;

    private League(ResourceLocation id, List<Badge> badges){
        this.id = id;
        this.badges = badges;
    }

    public Badge getRequiredBadgeAt(int index){
        return badges.get(index);
    }

    public String toString(){
        return "League@" + this.id + this.badges;
    }

    public ResourceLocation getId(){
        return this.id;
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

    public Badge getBadgeFromItem(ItemStack stack){
        return this.getBadgeFromItem(stack.getItem());
    }

    public Badge getBadgeFromItem(Item item){
        for (Badge badge : this.badges) {
            if (badge.getItem() == item) return badge;
        }
        return Badge.EMPTY;
    }

    public List<Badge> getBadges(){
        return new ArrayList<>(this.badges);
    }

    public int getTotalBadges(){
        return this.badges.size();
    }

    public static void createAndRegisterFromConfig(){
        List<LeaguesConfigModel.LeagueConfig> configLeagues = ModConfigs.LEAGUE_CONFIG.leagues();

        if(configLeagues.isEmpty()) {
            BetterBadges.LOGGER.error("Provided Empty Leagues at Mod Config");
            ModConfigs.throwException("Provided Empty Leagues at Mod Config");
        }

        for(LeaguesConfigModel.LeagueConfig configLeague : configLeagues){
            Builder builder = Builder.create(configLeague.id);

            for (int i = 0; i < configLeague.badges.size(); i++) {
                LeaguesConfigModel.BadgeConfig configBadge = configLeague.badges.get(i);

                ResourceLocation itemPath = ResourceLocation.parse(configBadge.id);

                Item item = BuiltInRegistries.ITEM.get(itemPath);

                if(item == Items.AIR) {
                    BetterBadges.LOGGER.error("Provided Invalid Item. Provided item id: {}. In {} league", configBadge.id, configLeague.id);
                    ModConfigs.throwException("Provided Invalid Item.");
                }

                // Currently Hard Coded ahh, can only have the big three
                Map<BoostTypes, BadgeAttribute> badgeAttributes = new HashMap<>();
                if(configBadge.adventure != null) badgeAttributes.put(BoostTypes.ADVENTURE, createAttributeFromConfig(BoostTypes.ADVENTURE, configBadge.adventure, itemPath));
                if(configBadge.catching != null) badgeAttributes.put(BoostTypes.CATCHING, createAttributeFromConfig(BoostTypes.CATCHING, configBadge.catching, itemPath));
                if(configBadge.spawning != null) badgeAttributes.put(BoostTypes.SPAWNING, createAttributeFromConfig(BoostTypes.SPAWNING, configBadge.spawning, itemPath));

                builder.addBadge(new Badge(
                        item,
                        configBadge.x,
                        configBadge.y,
                        i,
                        badgeAttributes
                ));
            }

            builder.build();
        }
    }

    @Nullable
    private static BadgeAttribute createAttributeFromConfig(BoostTypes type, LeaguesConfigModel.BadgeAttributeConfig config, ResourceLocation item){
        if (config == null) return null;
        ResourceLocation attributeLocation = ResourceLocation.parse(config.id);
        var attribute = BuiltInRegistries.ATTRIBUTE.get(attributeLocation);
        if(attribute == null) throw new ModConfigurationException("Attribute: " + config.id + " does not exists");
        return new BadgeAttribute(item, attributeLocation, type, config.operation, config.value);
    }

    public static class Builder {

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
            ResourceLocation id = createPath();

            Map<Item, Badge> itemBadgeMap = new HashMap<>();
            League league = new League(id, this.badges);
            Registry.register(ModRegistries.LEAGUE, id, league);
            return league;
        }

        private ResourceLocation createPath() {
            return ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, this.id + "_league");
        }
    }

}