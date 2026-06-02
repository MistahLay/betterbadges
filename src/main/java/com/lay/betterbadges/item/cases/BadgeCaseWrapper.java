package com.lay.betterbadges.item.cases;

import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.emblem.Emblem;
import com.lay.betterbadges.emblem.EmblemTargetItem;
import com.lay.betterbadges.inventory.EmblemBadgesManager;
import com.lay.betterbadges.inventory.LeagueBadgesManager;
import com.lay.betterbadges.item.OwnedItemWrapper;
import com.lay.betterbadges.league.League;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BadgeCaseWrapper extends OwnedItemWrapper {

    public BadgeCaseWrapper(ItemStack item) {
        super(item);
    }

    public boolean canPlayerUse(Player player){
        return Objects.equals(getItemOwner(), player.getUUID().toString());
    }

    public boolean hasActiveLeague(){
        League currentLeague = getCurrentLeague();
        return currentLeague != League.EMPTY;
    }

    // Emblems
    public Emblem getCurrentEmblem() {
        return this.item.get(ModDataComponents.CURRENT_EMBLEM);
    }

    public void setCurrentEmblem(Emblem emblem) {
        this.item.set(ModDataComponents.CURRENT_EMBLEM, emblem);
    }

    public void setEmblemInventoryManager(EmblemBadgesManager manager){
        this.item.set(ModDataComponents.EMBLEM_INVENTORY_CONTENTS, manager.serialize());
    }

    public EmblemBadgesManager getEmblemInventoryManager(){
        Map<Emblem, List<EmblemTargetItem>> data = this.item.get(ModDataComponents.EMBLEM_INVENTORY_CONTENTS);
        if(data == null) return new EmblemBadgesManager(new HashMap<>());
        return EmblemBadgesManager.deserialize(data);
    }

    // Leagues
    public League getCurrentLeague() {
        return this.item.get(ModDataComponents.CURRENT_LEAGUE);
    }

    public void setCurrentLeague(League league){
        this.item.set(ModDataComponents.CURRENT_LEAGUE, league);
    }

    public void setInventoryManager(LeagueBadgesManager manager, RegistryAccess access){
        this.item.set(ModDataComponents.LEAGUE_INVENTORY_CONTENTS, manager.serialize(access));
    }
    public LeagueBadgesManager getLeagueInventoryManager(RegistryAccess registryAccess){
        return LeagueBadgesManager.deserialize(this.item, registryAccess);
    }


}
