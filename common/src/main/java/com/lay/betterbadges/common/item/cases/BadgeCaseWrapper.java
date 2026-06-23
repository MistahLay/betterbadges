package com.lay.betterbadges.common.item.cases;

import com.lay.betterbadges.common.component.BetterBadgesDataComponents;
import com.lay.betterbadges.common.api.emblem.Boost;
import com.lay.betterbadges.common.api.emblem.Emblem;
import com.lay.betterbadges.common.api.emblem.EmblemTargetItem;
import com.lay.betterbadges.common.inventory.EmblemBadgesManager;
import com.lay.betterbadges.common.inventory.LeagueBadgesManager;
import com.lay.betterbadges.common.item.bounded.BoundItemWrapper;
import com.lay.betterbadges.common.api.league.BadgeSlot;
import com.lay.betterbadges.common.api.league.attributes.BadgeAttribute;
import com.lay.betterbadges.common.api.league.League;
import com.lay.betterbadges.common.render.screen.badgecase.BadgeCaseScreenHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.*;

public class BadgeCaseWrapper extends BoundItemWrapper {

    public BadgeCaseWrapper(ItemStack item) {
        super(item);
    }

    public boolean hasActiveLeague(){
        League currentLeague = getCurrentLeague();
        return currentLeague != League.EMPTY && currentLeague != null;
    }

    private void updateBoosts(EmblemBadgesManager manager, Emblem currentEmblem){
        ItemAttributeModifiers modifiers = ItemAttributeModifiers.EMPTY;
        for (EmblemTargetItem target : manager.getTargets(currentEmblem)) {
            if (target.league() == League.EMPTY) continue;
            Boost boost = currentEmblem.getSlot(target.currentSlot()).category();
            BadgeSlot badgeSlot = target.league().getRequiredBadgeAt(target.targetSlot());
            if (!badgeSlot.containsBoost(boost)) continue;
            BadgeAttribute badgeAttribute = badgeSlot.getAttribute(boost);
            if (badgeAttribute == null) continue;
            Holder<Attribute> attribute = badgeAttribute.setting().attribute();
            modifiers = modifiers.withModifierAdded(
                attribute,
                badgeAttribute.createModifier(),
                EquipmentSlotGroup.ANY
            );
        }
        this.item.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
    }

    // Emblems
    public Emblem getCurrentEmblem() {
        return this.item.get(BetterBadgesDataComponents.CURRENT_EMBLEM.get());
    }

    public void setCurrentEmblem(Emblem emblem, boolean updateBoost) {
        this.item.set(BetterBadgesDataComponents.CURRENT_EMBLEM.get(), emblem);
        if (updateBoost) this.updateBoosts(this.getEmblemInventoryManager(), emblem);
    }

    public void setCurrentEmblem(Emblem emblem) {
        this.setCurrentEmblem(emblem, true);
    }

    public void setEmblemInventoryManager(EmblemBadgesManager manager, boolean updateBoost){
        this.item.set(BetterBadgesDataComponents.EMBLEM_INVENTORY_CONTENTS.get(), manager.serialize());
        if(updateBoost) this.updateBoosts(manager, this.getCurrentEmblem());
    }

    public void setEmblemInventoryManager(EmblemBadgesManager manager){
        this.setEmblemInventoryManager(manager, true);
    }

    public EmblemBadgesManager getEmblemInventoryManager(){
        Map<Emblem, List<EmblemTargetItem>> data = this.item.get(BetterBadgesDataComponents.EMBLEM_INVENTORY_CONTENTS.get());
        if(data == null) return new EmblemBadgesManager(new HashMap<>());
        return EmblemBadgesManager.deserialize(data);
    }

    // Leagues
    public League getCurrentLeague() {
        return this.item.get(BetterBadgesDataComponents.CURRENT_LEAGUE.get());
    }

    public void setCurrentLeague(League league){
        this.item.set(BetterBadgesDataComponents.CURRENT_LEAGUE.get(), league);
    }

    public void setInventoryManager(LeagueBadgesManager manager, RegistryAccess access){
        this.item.set(BetterBadgesDataComponents.LEAGUE_INVENTORY_CONTENTS.get(), manager.serialize(access));
    }

    public LeagueBadgesManager getLeagueInventoryManager(RegistryAccess registryAccess){
        return LeagueBadgesManager.deserialize(this.item, registryAccess);
    }

    public boolean isGuiOpen(){
        var result = this.item.get(BetterBadgesDataComponents.ITEM_GUI_OPEN.get());
        return result != null ? result : false;
    }

    public void setGuiOpen(boolean isOPen){
        this.item.set(BetterBadgesDataComponents.ITEM_GUI_OPEN.get(), isOPen);
    }

}
