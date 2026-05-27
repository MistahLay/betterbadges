package com.lay.betterbadges.item.cases;

import com.lay.betterbadges.BetterBadges;
import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.component.custom.LeagueInventoryContents;
import com.lay.betterbadges.inventory.LeagueBadgesManager;
import com.lay.betterbadges.item.ModItems;
import com.lay.betterbadges.league.League;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BadgeCaseWrapper {

    private final ItemStack badgeCase;

    public BadgeCaseWrapper(ItemStack badgeCase) {
        this.badgeCase = badgeCase;
    }

    public LeagueBadgesManager getInventoryManager(RegistryAccess registryAccess){
        return LeagueBadgesManager.createFromItem(this.badgeCase, registryAccess);
    }

    public League getCurrentLeague() {
        return this.badgeCase.get(ModDataComponents.CURRENT_LEAGUE);
    }

    public void setCurrentLeague(League league){
        this.badgeCase.set(ModDataComponents.CURRENT_LEAGUE, league);
    }

    public String getItemOwner(){
        return this.badgeCase.get(ModDataComponents.ITEM_OWNER);
    }

    public void setInventoryManager(LeagueBadgesManager manager, RegistryAccess access){
        this.badgeCase.set(ModDataComponents.LEAGUE_INVENTORY_CONTENTS, manager.getLeagueInventoryContents(access));
    }

    public boolean canPlayerUse(Player player){
        return Objects.equals(getItemOwner(), player.getUUID().toString());
    }

    public boolean hasActiveLeague(){
        League currentLeague = getCurrentLeague();
        return currentLeague != League.EMPTY;
    }

}
