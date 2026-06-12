package com.lay.betterbadges.common.emblem;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.registry.ModRegistries;
import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Emblem {

    public static final Codec<Emblem> CODEC = Codec.STRING.xmap(
            to -> {
                Emblem emblem = ModRegistries.EMBLEM.get(ResourceLocation.parse(to));
                return emblem == null ? Emblem.EMPTY.get() : emblem;
            },
            from -> from.id.toString()
    );

    public static final int MAX_SLOTS = 16;

    public static final RegistrySupplier<Emblem> EMPTY = Emblem.registerEmptyEmblem();

    private static RegistrySupplier<Emblem> registerEmptyEmblem(){
        ResourceLocation path = ResourceLocation.fromNamespaceAndPath(BetterBadges.MOD_ID, "empty_emblem");
        return ModRegistries.EMBLEM.register(path, () -> new Emblem(path, null, new ArrayList<>()));
    }

    public static Emblem getEmblemFromItem(Item item) {
        for (Emblem emblem : ModRegistries.EMBLEM) {
            if(emblem.getEmblemItem() == item) return emblem;
        }
        return Emblem.EMPTY.get();
    }

    private final List<EmblemSlot> slots;

    private final ResourceLocation id;

    private final Item emblemItem;

    public Emblem(ResourceLocation id, Item item, List<EmblemSlot> slots) {
        this.slots = slots;
        if (slots.size() > MAX_SLOTS) {
            throw new RuntimeException("Provided more than the max provided slots");
        }
        this.id = id;
        this.emblemItem = item;
    }

    public ResourceLocation getId(){
        return this.id;
    }

    public List<EmblemSlot> getSlots(){
        return new ArrayList<>(this.slots);
    }

    public List<EmblemSlot> getSlots(Boost type){
        return this.slots.stream().filter(slot -> slot.category() == type).collect(Collectors.toList());
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

    public static class Builder {

        private final ResourceLocation id;
        private final Item item;
        private final ArrayList<EmblemSlot> slots = new ArrayList<>();

        public static Builder create(ResourceLocation id, Item item){
            return new Builder(id, item);
        }

        public Builder(ResourceLocation id, Item item) {
            this.id = id;
            this.item = item;
        }

        public Builder add(EmblemSlot slot){
            this.slots.add(slot);
            return this;
        }

        public Builder add(Boost category, int x, int y){
            this.add(new EmblemSlot(category, x, y, this.slots.size()));
            return this;
        }

        public RegistrySupplier<Emblem> build(){
            return ModRegistries.EMBLEM.register(id, () -> new Emblem(id, item, slots));
        }
    }

}
