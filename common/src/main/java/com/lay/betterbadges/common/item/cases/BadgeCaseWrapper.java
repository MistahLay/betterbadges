package com.lay.betterbadges.common.item.cases;

import com.lay.betterbadges.common.component.ModDataComponents;
import com.lay.betterbadges.common.emblem.Boost;
import com.lay.betterbadges.common.emblem.Emblem;
import com.lay.betterbadges.common.emblem.EmblemTargetItem;
import com.lay.betterbadges.common.inventory.EmblemBadgesManager;
import com.lay.betterbadges.common.inventory.LeagueBadgesManager;
import com.lay.betterbadges.common.item.bounded.BoundItemWrapper;
import com.lay.betterbadges.common.league.BadgeSlot;
import com.lay.betterbadges.common.league.BadgeAttribute;
import com.lay.betterbadges.common.league.League;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.*;

public class BadgeCaseWrapper extends BoundItemWrapper {

    public BadgeCaseWrapper(ItemStack item) {
        super(item);
    }

    public boolean hasActiveLeague(){
        League currentLeague = getCurrentLeague();
        return currentLeague != League.EMPTY.get() && currentLeague != null;
    }

    private void updateBoosts(EmblemBadgesManager manager, Emblem currentEmblem){
        if (Platform.getEnv() == Env.CLIENT.toPlatform()) return;
        ItemAttributeModifiers modifiers = ItemAttributeModifiers.EMPTY;
        for (EmblemTargetItem target : manager.getTargets(currentEmblem)) {
            Boost boost = currentEmblem.getSlot(target.currentSlot()).category();
            BadgeSlot badgeSlot = target.league().getRequiredBadgeAt(target.targetSlot());
            if (!badgeSlot.containsBoost(boost)) continue;
            BadgeAttribute badgeAttribute = badgeSlot.getAttribute(boost);
            Holder.Reference<Attribute> attribute = badgeAttribute.getAttribute().orElseThrow();
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
        return this.item.get(ModDataComponents.CURRENT_EMBLEM);
    }

    public void setCurrentEmblem(Emblem emblem) {
        this.item.set(ModDataComponents.CURRENT_EMBLEM, emblem);
        this.updateBoosts(this.getEmblemInventoryManager(), emblem);
    }

    public void setEmblemInventoryManager(EmblemBadgesManager manager){
        this.item.set(ModDataComponents.EMBLEM_INVENTORY_CONTENTS, manager.serialize());
        this.updateBoosts(manager, this.getCurrentEmblem());
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
