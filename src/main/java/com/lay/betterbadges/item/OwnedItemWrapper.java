package com.lay.betterbadges.item;

import com.lay.betterbadges.component.ModDataComponents;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.UUID;

public class OwnedItemWrapper {

    protected final ItemStack item;

    public OwnedItemWrapper(ItemStack item) {
        this.item = item;
    }

    public String getItemOwner(){
        return this.item.get(ModDataComponents.ITEM_OWNER);
    }

    public String getItemOwnerName(MinecraftServer server){
        String rawUUID = this.getItemOwner();
        if(rawUUID == null || rawUUID.length() != 36) return "unknown";
        UUID uuid = UUID.fromString(rawUUID);
        GameProfileCache cache = server.getProfileCache();
        if(cache == null) return "unknown";
        Optional<GameProfile> profile = cache.get(uuid);
        if(profile.isEmpty()) return "unknown";
        return profile.get().getName();
    }
}
