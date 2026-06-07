package com.lay.betterbadges.common.league;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.item.badges.BadgeItem;
import com.lay.betterbadges.common.registry.ModRegistries;
import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class League {

    public static final TagKey<Item> LEAGUE_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, "league"));

    public static final Codec<League> CODEC = Codec.STRING.xmap(
            to -> {
                League league = ModRegistries.LEAGUE.get(ResourceLocation.parse(to));
                return league == null ? League.EMPTY.get() : league;
            },
            from -> from.id.toString()
    );

    public static final RegistrySupplier<League> EMPTY = Builder.create(BetterBadges.of("none_league")).build();

    /**
     * e.g. "betterbadges:name_league"
     */
    private final ResourceLocation id;
    private final List<BadgeSlot> badgeSlots;

    public League(ResourceLocation id, List<BadgeSlot> badgeSlots){
        this.id = id;
        this.badgeSlots = badgeSlots;
    }

    public BadgeSlot getRequiredBadgeAt(int index){
        return badgeSlots.get(index);
    }

    public String toString(){
        return "League@" + this.id + this.badgeSlots;
    }

    public ResourceLocation getId(){
        return this.id;
    }

    public boolean canInsertBadgeAtSlot(Item item, int slot){
        return item == getRequiredBadgeAt(slot).item();
    }

    public int getSlotForBadge(Item item){
        int slot = -1;
        for(BadgeSlot badgeSlot : this.badgeSlots) {
            if(badgeSlot.item() == item) {
                slot = badgeSlot.slot();
            }
        }
        return slot;
    }

    public @Nullable BadgeSlot getBadgeFromItem(ItemStack stack){
        Item item = stack.getItem();
        if(item instanceof BadgeItem badge) return this.getBadgeFromItem(badge);
        return null;
    }

    public @Nullable BadgeSlot getBadgeFromItem(BadgeItem item){
        for (BadgeSlot badgeSlot : this.badgeSlots) {
            if (badgeSlot.item() == item) return badgeSlot;
        }
        return null;
    }

    public List<BadgeSlot> getBadges(){
        return new ArrayList<>(this.badgeSlots);
    }

    public int getTotalBadges(){
        return this.badgeSlots.size();
    }

    public static class Builder {

        private ResourceLocation id;
        List<BadgeSlot> badges = new ArrayList<>();

        public static Builder create(ResourceLocation id){
            Builder builder = new Builder();
            builder.id = id;
            return builder;
        }

        public Builder add(BadgeItem item, int x, int y){
            this.add(new BadgeSlot(item, x, y, this.badges.size()));
            return this;
        }

        public Builder add(BadgeSlot slot){
            this.badges.add(slot);
            return this;
        }

        public RegistrySupplier<League> build(){
            // TODO: Add overwrite check
            int count = this.badges.size();
            if(count == 0){
                BetterBadges.LOGGER.error("League: {} has no badges added", this.id);
            }
            return ModRegistries.LEAGUE.register(id, () -> new League(id, this.badges));
        }
    }

}