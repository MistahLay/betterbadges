package com.lay.betterbadges.emblem;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.config.EmblemConfigModel;
import com.lay.betterbadges.config.ModConfigs;
import com.lay.betterbadges.league.Badge;
import com.lay.betterbadges.league.League;
import com.lay.betterbadges.registry.ModRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Emblem {

    public static final Codec<Emblem> CODEC = Codec.STRING.xmap(
            to -> {
                Emblem league = ModRegistries.EMBLEM.get(ResourceLocation.parse(to));
                return league == null ? Emblem.EMPTY : league;
            },
            from -> from.id.toString()
    );

    public static final Emblem EMPTY = Emblem.registerEmptyEmblem();

    private static Emblem registerEmptyEmblem(){
        ResourceLocation path = ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, "empty_emblem");
        Emblem emblem = new Emblem(new ArrayList<>(), path, null);
        Registry.register(ModRegistries.EMBLEM, path, emblem);
        return emblem;
    }

    public static void createAndRegisterFromConfig(){
        List<EmblemConfigModel.Emblem> config = ModConfigs.EMBLEM_CONFIG.emblems();

        for (EmblemConfigModel.Emblem emblemConfig : config){
            List<EmblemSlot> emblemSlots = new ArrayList<>();
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(emblemConfig.item));
            int i = 0;
            if(emblemConfig.adventure != null) for (EmblemConfigModel.BoostConfig slot : emblemConfig.adventure) emblemSlots.add(new EmblemSlot(BoostTypes.ADVENTURE, slot.x, slot.y, i++));
            if(emblemConfig.catching != null) for (EmblemConfigModel.BoostConfig slot : emblemConfig.catching) emblemSlots.add(new EmblemSlot(BoostTypes.CATCHING, slot.x, slot.y, i++));
            if(emblemConfig.spawning != null) for (EmblemConfigModel.BoostConfig slot : emblemConfig.spawning) emblemSlots.add(new EmblemSlot(BoostTypes.SPAWNING, slot.x, slot.y, i++));
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, emblemConfig.id + "_emblem");
            Registry.register(ModRegistries.EMBLEM, id, new Emblem(emblemSlots, id, item));
            BetterBadges.LOGGER.info("Registed Emblem: {}", id);
        }
    }

    public static Emblem getEmblemFromItem(Item item) {
        for (Emblem emblem : ModRegistries.EMBLEM) {
            if(emblem.getEmblemItem() == item) return emblem;
        }
        return Emblem.EMPTY;
    }

    private final List<EmblemSlot> slots;

    private final ResourceLocation id;

    private final Item emblemItem;

    public Emblem(List<EmblemSlot> slots, ResourceLocation id, Item item) {
        this.slots = slots;
        this.id = id;
        this.emblemItem = item;
    }

    public ResourceLocation getId(){
        return this.id;
    }

    public List<EmblemSlot> getSlots(){
        return new ArrayList<>(this.slots);
    }

    public List<EmblemSlot> getSlots(BoostTypes type){
        return this.slots.stream().filter(slot -> slot.category == type).collect(Collectors.toList());
    }

    public EmblemSlot getSlot(int i){
        return this.slots.get(i);
    }

    public int getTotalSlots() {
        return this.slots.size();
    }

    public Item getEmblemItem() {
        return this.emblemItem;
    }

    public record EmblemSlot(BoostTypes category, int x, int y, int index) {
        public boolean isValid(League league, Item item){
            Badge badge = league.getBadgeFromItem(item);
            return badge.containsBoost(this.category);
        }
    }
}
