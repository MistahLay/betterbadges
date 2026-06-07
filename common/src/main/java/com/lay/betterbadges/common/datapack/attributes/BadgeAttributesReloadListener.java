package com.lay.betterbadges.common.datapack.attributes;

import com.google.gson.*;
import com.lay.betterbadges.common.emblem.Boost;
import com.lay.betterbadges.common.item.badges.BadgeItem;
import com.lay.betterbadges.common.league.BadgeAttribute;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class BadgeAttributesReloadListener extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static Map<BadgeItem, Map<Boost, BadgeAttribute>> attributes = new HashMap<>();

    public static @Nullable HashMap<Boost, BadgeAttribute> getAttributes(BadgeItem badge){
        if(!attributes.containsKey(badge)) return null;
        return new HashMap<>(attributes.get(badge));
    }

    public static @Nullable BadgeAttribute getAttribute(BadgeItem badge, Boost boost){
        final var attributes = getAttributes(badge);
        if(attributes == null) return null;
        return attributes.getOrDefault(boost, null);
    }

    public static boolean containsAttribute(BadgeItem badge, Boost boost){
        final var attributes = getAttributes(badge);
        if(attributes == null) return false;
        return attributes.containsKey(boost);
    }

    public BadgeAttributesReloadListener() {
        super(GSON, "leagues");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> result, ResourceManager resourceManager, ProfilerFiller profiler) {
        attributes = new HashMap<>();

        for (Map.Entry<ResourceLocation, JsonElement> json : result.entrySet()){
            ResourceLocation path = json.getKey();
            JsonElement element = json.getValue();

            String[] pathSegments = path.getPath().split("/");

            if (pathSegments.length >= 2) {
                String parentFolder = pathSegments[pathSegments.length - 2];

                System.out.println("Target folder: " + parentFolder); // Outputs: "c"
            }
        }
    }

}
