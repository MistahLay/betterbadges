package com.lay.betterbadges.inventory;

import com.lay.betterbadges.emblem.Emblem;
import com.lay.betterbadges.emblem.EmblemTargetItem;
import com.lay.betterbadges.league.Badge;
import com.lay.betterbadges.league.BadgeAttribute;
import com.lay.betterbadges.league.League;
import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmblemBadgesManager {

    private static final Logger log = LoggerFactory.getLogger(EmblemBadgesManager.class);
    private final Map<Emblem, NonNullList<EmblemTargetItem>> targetItems;

    public Map<Emblem, List<EmblemTargetItem>> serialize(){
        Map<Emblem, List<EmblemTargetItem>> newMap = new HashMap<>();
        for (Map.Entry<Emblem, NonNullList<EmblemTargetItem>> item : targetItems.entrySet()){
            Emblem key = item.getKey();
            List<EmblemTargetItem> newList = new ArrayList<>();
            for (EmblemTargetItem targetItem : item.getValue()) if (targetItem != EmblemTargetItem.EMPTY) {
                newList.add(targetItem);
            }
            newMap.put(key, newList);
        }
        return newMap;
    }

    public static EmblemBadgesManager deserialize(Map<Emblem, List<EmblemTargetItem>> targetItems){
        Map<Emblem, NonNullList<EmblemTargetItem>> newMap = new HashMap<>();
        for (Map.Entry<Emblem, List<EmblemTargetItem>> item : targetItems.entrySet()){
            Emblem key = item.getKey();
            NonNullList<EmblemTargetItem> newList = NonNullList.withSize(key.getTotalSlots(), EmblemTargetItem.EMPTY);
            for (EmblemTargetItem targetItem : item.getValue()) newList.set(targetItem.currentSlot(), targetItem);
            newMap.put(item.getKey(), newList);
        }
        return new EmblemBadgesManager(newMap);
    }

    public EmblemBadgesManager(Map<Emblem, NonNullList<EmblemTargetItem>> targetItems) {
        this.targetItems = targetItems;
    }

    @Nullable
    public List<BadgeAttribute> getBoosts(Emblem emblem, LeagueBadgesManager badgesInventories){
        List<EmblemTargetItem> targets = targetItems.get(emblem);
        if(targets == null || targets.isEmpty()) return null;
        List<BadgeAttribute> boosts = new ArrayList<>();
        for (EmblemTargetItem item : targets) {
            League league = item.league();
            SimpleContainer targetContainer = badgesInventories.getBadgeContainer(league);
            Badge badge = league.getBadgeFromItem(targetContainer.getItem(item.targetSlot()));
            boosts.add(badge.getAttribute(emblem.getSlot(item.currentSlot()).category()));
        }
        return boosts;
    }

    public void removeTarget(Emblem emblem, int targetSlot){
        List<EmblemTargetItem> targets = this.targetItems.get(emblem);
        if(targets == null) return;
        targets.set(targetSlot, EmblemTargetItem.EMPTY);
    }

    public void setTarget(Emblem emblem, League league, int targetSlot, int slotIndex){
        setTarget(emblem, league, targetSlot, slotIndex, false);
    }

    public void setTarget(Emblem emblem, League league, int targetSlot, int slotIndex, boolean override){
        List<EmblemTargetItem> targets = this.targetItems.get(emblem);
        if(targets == null) return;
        EmblemTargetItem target = targets.get(slotIndex);
        if(!override) if(target != EmblemTargetItem.EMPTY) return;
        targets.set(slotIndex, new EmblemTargetItem(league, targetSlot, slotIndex));
    }

    public void addEmblem(Emblem emblem) {
        targetItems.put(emblem, NonNullList.withSize(emblem.getTotalSlots(), EmblemTargetItem.EMPTY));
    }

    public NonNullList<EmblemTargetItem> getTargets(Emblem emblem){
        return this.targetItems.get(emblem);
    }

    public EmblemTargetItem getTarget(Emblem emblem, int slotIndex){
        return this.targetItems.get(emblem).get(slotIndex);
    }

    public EmblemTargetItem findTarget(League league, int targetSlot, Emblem emblem, int exclude){
        if(!containsEmblem(emblem)) return null;
        for (EmblemTargetItem target : this.targetItems.get(emblem)){
            if(target == EmblemTargetItem.EMPTY) continue;
//            log.info("current {} : target {} : targetLeague {} : exclude {} : size {} : league {} : targetSlot {}", target.currentSlot(), target.targetSlot(), target.league().getId().toString(), exclude, this.targetItems.size(), league.getId().toString(), targetSlot);
//            log.info("{}", targetSlot == target.targetSlot());
            if(target.league() == league && target.targetSlot() == targetSlot && target.currentSlot() != exclude) return target;
        }
        return null;
    }

    public boolean containsEmblem(Emblem emblem){
        return this.targetItems.containsKey(emblem);
    }

    public Map<Emblem, NonNullList<EmblemTargetItem>> getTargetItems(){
        return targetItems;
    }

}
