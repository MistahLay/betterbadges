package com.lay.betterbadges.common.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighlightEntityManager {

    public static List<HighlightEntityManager> managers = new ArrayList<>();

    public HighlightEntityManager(){
        managers.add(this);
    }

    private final Map<Integer, Integer> highlighted = new HashMap<>();

    public void highlight(Entity entity, int argbColor) {
        this.highlighted.put(entity.getId(), argbColor);
    }

    public void clear(Entity entity) {
        this.highlighted.remove(entity.getId());
    }

    public void clearAll() {
        this.highlighted.clear();
    }

    public boolean isHighlighted(Entity entity) {
        return this.highlighted.containsKey(entity.getId());
    }

    public int getColor(Entity entity) {
        return this.highlighted.getOrDefault(entity.getId(), 0xFFFFFF);
    }

}
