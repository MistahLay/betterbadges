package com.lay.betterbadges.common.datapack.attributes;

import com.google.gson.*;
import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.emblem.Boost;
import com.lay.betterbadges.common.item.badges.BadgeItem;
import com.lay.betterbadges.common.league.attributes.BadgeAttribute;
import com.lay.betterbadges.common.league.attributes.BadgeAttributeModifierSetting;
import com.lay.betterbadges.common.league.attributes.BadgeAttributesManager;
import com.lay.betterbadges.common.network.ModNetworkChannel;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class BadgeAttributesReloadListener extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public BadgeAttributesReloadListener() {
        super(GSON, "leagues");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> result, ResourceManager resourceManager, ProfilerFiller profiler) {
        BadgeAttributesManager.reset();

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
                        BadgeAttributesManager.register(badgeItem, badgeAttributes);
                        total++;
                    }
                }
            }

            BetterBadges.LOGGER.info("Successfully registered {} badge attributes", total);
        } catch (Exception e){
            BetterBadges.LOGGER.error("Only got to register {} badge attributes: {}", total, e.getMessage());
        } finally {
            if (BetterBadges.SERVER != null) BadgeAttributesManager.updateClients();
        }
    }

}
