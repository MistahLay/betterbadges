package com.lay.betterbadges.common.api.league;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.emblem.Emblem;
import com.lay.betterbadges.common.item.badges.BadgeItem;
import com.lay.betterbadges.common.registry.ModRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class League {

    public static final League EMPTY = new League(BetterBadges.of("none"), new ArrayList<>());

    public static final Codec<League> CODEC = Codec.STRING.comapFlatMap(
            to -> {
                try {
                    League league = ModRegistries.LEAGUE.get(ResourceLocation.parse(to));
                    return DataResult.success(league);
                } catch (Exception e){
                    return DataResult.success(EMPTY);
                }
            },
            from -> from.id.toString()
    );


    private final ResourceLocation id;
    private final List<BadgeSlot> badgeSlots;

    public League(ResourceLocation id, List<BadgeSlot> badgeSlots){
        this.id = id;
        this.badgeSlots = badgeSlots;
    }

    public BadgeSlot getRequiredBadgeAt(int index){
        return this.badgeSlots.get(index);
    }

    public String toString(){
        return "League@" + this.id + this.badgeSlots;
    }

    public ResourceLocation getId(){
        return this.id;
    }

    public boolean canInsertBadgeAtSlot(Item item, int slot){
        return item == this.getRequiredBadgeAt(slot).item();
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

    public BadgeSlot getBadge(int index){
        return this.badgeSlots.get(index);
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

        public Builder add(DeferredSupplier<BadgeItem> item, int x, int y){
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
            return ModRegistries.LEAGUE.register(this.id, () -> new League(this.id, this.badges));
        }
    }

}