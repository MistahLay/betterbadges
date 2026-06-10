package com.lay.betterbadges.common.util.texture;

import com.lay.betterbadges.common.league.BadgeSlot;
import com.lay.betterbadges.common.league.League;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public record LeagueTexture(League league) {

    public static LeagueTexture of(League league){
        return new LeagueTexture(league);
    }

    public ResourceLocation getLeaguePath(){
        return TextureHelper.ofBadgeCase("league/" + league.getId().getPath() + "/", false);
    }

    public ResourceLocation tag(){
        return TextureHelper.png(getLeaguePath().withSuffix("tag"));
    }

    public ResourceLocation base(){
        return TextureHelper.png(getLeaguePath().withSuffix("base"));
    }

    public ResourceLocation badge(int index){
        return badge(league.getBadge(index));
    }

    public ResourceLocation badge(BadgeSlot slot){
        return badge(slot.item());
    }

    public ResourceLocation badge(Item item){
        return TextureHelper.png(this.getLeaguePath().withSuffix(BuiltInRegistries.ITEM.getKey(item).getPath()));
    }

}
