package com.lay.betterbadges.common.render.screen.badgecase;

import com.lay.betterbadges.common.item.badges.BadgeItem;
import com.lay.betterbadges.common.render.atlas.managers.ShineSpritesManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.inventory.Slot;

import java.util.HashMap;
import java.util.Map;

public class BadgeShineAnimation {

    public static int MAX_FRAMES = 10;

    private HashMap<Slot, Integer> animated = new HashMap<>();
    private int addedFrame = 0;

    public void reset(){
        this.animated = new HashMap<>();
    }

    public void addFrame(){
        this.addedFrame = 1;
    }

    public void addToRender(Slot slot){
        if (!(slot.getItem().getItem() instanceof BadgeItem)) return;
        this.animated.putIfAbsent(slot, 0);
    }

    public boolean isRendering(Slot slot){
        return this.animated.containsKey(slot);
    }

    public void render(GuiGraphics guiGraphics){
        for (Map.Entry<Slot, Integer> slotIntegerEntry : new HashMap<>(this.animated).entrySet()){
            int frame = slotIntegerEntry.getValue() + this.addedFrame;
            Slot slot = slotIntegerEntry.getKey();
            BadgeItem badgeItem = (BadgeItem) slot.getItem().getItem();
            if (frame >= MAX_FRAMES) {
                this.animated.remove(slot);
                continue;
            } else {
                this.animated.put(slot, frame);
            }
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0.0F, 0.0F, 300.0F);
            guiGraphics.blit(
                    slot.x, slot.y, 5,
                    16, 16,
                    ShineSpritesManager.MANAGER.get(badgeItem, "badge/", frame)
            );
            guiGraphics.pose().popPose();
        }
        this.addedFrame = 0;
    }

}
