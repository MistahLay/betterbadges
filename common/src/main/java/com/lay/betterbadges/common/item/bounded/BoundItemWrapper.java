package com.lay.betterbadges.common.item.bounded;

import com.lay.betterbadges.common.component.ModDataComponents;
import com.lay.betterbadges.common.item.ItemStackWrapper;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.UUID;

public class BoundItemWrapper extends ItemStackWrapper {

    public static String UNKNOWN = "unknown";

    public BoundItemWrapper(ItemStack item) {
        super(item);
    }

    public UUID getItemOwner(){
        return this.item.get(ModDataComponents.ITEM_OWNER.get());
    }

    public String getItemOwnerName(MinecraftServer server){
        UUID uuid = this.getItemOwner();
        GameProfileCache cache = server.getProfileCache();
        if(cache == null || uuid == null) return UNKNOWN;
        Optional<GameProfile> profile = cache.get(uuid);
        if(profile.isEmpty()) return UNKNOWN;
        return profile.get().getName();
    }

    public void setItemOwner(Player player){
        this.setItemOwner(player.getUUID());
    }

    public void setItemOwner(UUID uuid){
        this.item.set(ModDataComponents.ITEM_OWNER.get(), uuid);
    }

    public boolean hasOwner() {
        return this.item.get(ModDataComponents.ITEM_OWNER.get()) != null;
    }

    public boolean canUse(Player player){
        return canUse(player.getUUID(), false);
    }

    public boolean canUse(UUID uuid){
        return canUse(uuid, false);
    }

    public boolean canUse(Player player, boolean overrideEmpty){
        return canUse(player.getUUID(), overrideEmpty);
    }

    public boolean canUse(UUID uuid, boolean overrideEmpty){
        UUID owner = this.getItemOwner();
        if (owner == null) {
            if (overrideEmpty) {
                this.setItemOwner(uuid);
                return true;
            }
            return false;
        }
        return owner.equals(uuid);
    }
}
