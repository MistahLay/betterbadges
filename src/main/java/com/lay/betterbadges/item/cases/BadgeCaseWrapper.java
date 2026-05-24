package com.lay.betterbadges.item.cases;

import com.lay.betterbadges.component.ModDataComponents;
import com.lay.betterbadges.component.custom.LeagueInventoryContents;
import com.lay.betterbadges.inventory.LeagueBadgesManager;
import com.lay.betterbadges.item.ModItems;
import com.lay.betterbadges.league.League;
import com.lay.betterbadges.registry.ModRegistries;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BadgeCaseWrapper {

    private final ItemStack badgeCase;

    @Nullable
    public static BadgeCaseWrapper wrap(ItemStack badgeCase){
        return badgeCase.is(ModItems.BADGE_CASE) ? new BadgeCaseWrapper(badgeCase) : null;
    }

    private BadgeCaseWrapper(ItemStack badgeCase) {
        this.badgeCase = badgeCase;
    }

    public LeagueBadgesManager getInventoryManager(RegistryAccess registryAccess){
        return LeagueBadgesManager.createFromItem(this.badgeCase, registryAccess);
    }

    public League getCurrentLeague() {
        return this.badgeCase.get(ModDataComponents.CURRENT_LEAGUE);
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
