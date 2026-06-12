package com.lay.betterbadges.common.datapack.attributes;

import com.google.gson.*;
import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.emblem.Boost;
import com.lay.betterbadges.common.item.badges.BadgeItem;
import com.lay.betterbadges.common.league.League;
import com.lay.betterbadges.common.registry.ModRegistries;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
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

        int total = 0;

        try {
            for (Map.Entry<ResourceLocation, JsonElement> json : result.entrySet()){
                ResourceLocation path = json.getKey();
                JsonObject element = json.getValue().getAsJsonObject();

                String[] pathSegments = path.getPath().split("/");

                if (pathSegments.length >= 2) {
                    Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(element.getAsJsonObject().get("item").getAsString()));
                    if (item instanceof BadgeItem badgeItem) {
                        Map<Boost, BadgeAttribute> badgeAttributes = new HashMap<>();
                        for (JsonElement jsonSetting : element.getAsJsonArray("modifiers").asList()){
                            BadgeAttributeModifierSetting setting = JsonOps.INSTANCE.withDecoder(BadgeAttributeModifierSetting.CODEC.decoder()).apply(jsonSetting).getOrThrow().getFirst();

                            BadgeAttribute attribute = new BadgeAttribute(badgeItem, setting);
                            badgeAttributes.put(setting.boost(), attribute);
                        }
                        attributes.putIfAbsent(badgeItem, badgeAttributes);
                        total++;
                    }
                }
            }

            BetterBadges.LOGGER.info("Successfully registered {} badge attributes", total);
        } catch (Exception e){
            BetterBadges.LOGGER.error("Can't register attributes {}", e.getMessage());
        }

    }

}
