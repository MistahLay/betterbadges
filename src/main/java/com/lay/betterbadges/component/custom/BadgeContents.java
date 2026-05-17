package com.lay.betterbadges.component.custom;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Stream;

public class BadgeContents {

    public static final Codec<BadgeContents> CODEC = ItemStack.CODEC.listOf().xmap(BadgeContents::createFromList, v -> v.items);
    public static final StreamCodec<RegistryFriendlyByteBuf, BadgeContents> STREAM_CODEC = ItemStack.STREAM_CODEC
            .apply(ByteBufCodecs.list())
            .map(BadgeContents::createFromList, badgeContents -> badgeContents.items);

    public static final int DEFAULT_SIZE = 8;
    private static BadgeContents createFromList(List<ItemStack> list){
        return new BadgeContents(list.isEmpty() ? NonNullList.withSize(DEFAULT_SIZE, ItemStack.EMPTY) : (NonNullList<ItemStack>) list);
    }

    final NonNullList<ItemStack> items;

    BadgeContents(NonNullList<ItemStack> items){
        this.items = items;
    }

    public List<ItemStack> getItems(){
        return this.items;
    }

    public boolean isEmpty(){
        return this.items.isEmpty();
    }

    public int getSize(){
        return this.items.size();
    }

    public ItemStack getItemUnsafe(int i) {
        return (ItemStack)this.items.get(i);
    }

    public Stream<ItemStack> itemCopyStream() {
        return this.items.stream().map(ItemStack::copy);
    }

    public Iterable<ItemStack> items() {
        return this.items;
    }

    public Iterable<ItemStack> itemsCopy() {
        return Lists.<ItemStack, ItemStack>transform(this.items, ItemStack::copy);
    }

    public String toString() {
        return "BadgeContents@" + this.items;
    }

    public boolean canAddItem(ItemStack itemStack, int slot){
        ItemStack targetStack = this.items.get(slot);
        return !itemStack.isEmpty() &&
                targetStack.isEmpty();
    }

    public static class Mutable {

        private NonNullList<ItemStack> items;

        Mutable(BadgeContents original){
            NonNullList<ItemStack> copy = NonNullList.create();
            copy.addAll(original.items);
            this.items = copy;
        }

        // Will 100% move the whole stack, although, this shouldn't be a problem since badges should not contain more than one item
        public ItemStack addItem(ItemStack itemStack, int slot){
            if(!canAddItem(itemStack, slot)) return itemStack;
            ItemStack itemStack2 = itemStack.copy();
            setItem(itemStack2, slot);
            return ItemStack.EMPTY;
        }

        public void setItem(ItemStack itemStack, int slot){
            this.items.set(slot, itemStack);
        }

        // Cannot swap stacks, so if there's somehow a different item on boulderbadge slot, you have to remove it manually first then insert the correct badge
        public boolean canAddItem(ItemStack itemStack, int slot){
            ItemStack targetStack = this.items.get(slot);
            return !itemStack.isEmpty() &&
                    targetStack.isEmpty();
        }

        // Will move the entire stack, again, will not consider if there's more than one item
        public ItemStack removeItem(int slot){
            ItemStack item = items.get(slot).copy();
            this.items.remove(slot);
            return item;
        }

        public BadgeContents toImmutable(){
            return new BadgeContents(this.items);
        }
    }
}
