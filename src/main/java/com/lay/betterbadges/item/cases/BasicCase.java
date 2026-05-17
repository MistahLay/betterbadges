package com.lay.betterbadges.item.cases;

import com.lay.betterbadges.league.League;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.UUID;


public class BasicCase extends Item {

    private League currentLeague;
    private UUID originalOwner;

    public BasicCase(Properties properties) {
        super(properties);
    }

    public League getCurrentLeague() {
        return this.currentLeague;
    }

    public UUID getOriginalOwner(){
        return this.originalOwner;
    }

    public boolean canPlayerUse(Player player){
        return player.getUUID() == this.originalOwner;
    }
}
