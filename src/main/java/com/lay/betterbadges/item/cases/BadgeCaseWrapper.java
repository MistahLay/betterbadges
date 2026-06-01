package com.lay.betterbadges.item.cases;

import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.emblem.Emblem;
import com.lay.betterbadges.emblem.EmblemTargetItem;
import com.lay.betterbadges.inventory.EmblemBadgesManager;
import com.lay.betterbadges.inventory.LeagueBadgesManager;
import com.lay.betterbadges.league.League;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BadgeCaseWrapper {

    private final ItemStack badgeCase;

    public BadgeCaseWrapper(ItemStack badgeCase) {
        this.badgeCase = badgeCase;
    }

    public String getItemOwner(){
        return this.badgeCase.get(ModDataComponents.ITEM_OWNER);
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
        return this.badgeCase.get(ModDataComponents.CURRENT_EMBLEM);
    }

    public void setCurrentEmblem(Emblem emblem) {
        this.badgeCase.set(ModDataComponents.CURRENT_EMBLEM, emblem);
    }

    public void setEmblemInventoryManager(EmblemBadgesManager manager){
        this.badgeCase.set(ModDataComponents.EMBLEM_INVENTORY_CONTENTS, manager.serialize());
    }

    public EmblemBadgesManager getEmblemInventoryManager(){
        Map<Emblem, List<EmblemTargetItem>> data = this.badgeCase.get(ModDataComponents.EMBLEM_INVENTORY_CONTENTS);
        if(data == null) return new EmblemBadgesManager(new HashMap<>());
        return EmblemBadgesManager.deserialize(data);
    }

    // Leagues
    public League getCurrentLeague() {
        return this.badgeCase.get(ModDataComponents.CURRENT_LEAGUE);
    }

    public void setCurrentLeague(League league){
        this.badgeCase.set(ModDataComponents.CURRENT_LEAGUE, league);
    }

    public void setInventoryManager(LeagueBadgesManager manager, RegistryAccess access){
        this.badgeCase.set(ModDataComponents.LEAGUE_INVENTORY_CONTENTS, manager.serialize(access));
    }
    public LeagueBadgesManager getLeagueInventoryManager(RegistryAccess registryAccess){
        return LeagueBadgesManager.deserialize(this.badgeCase, registryAccess);
    }


}
