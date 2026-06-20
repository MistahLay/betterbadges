package com.lay.betterbadges.common.event;

import com.cobblemon.mod.common.platform.events.PlatformEvents;
import com.lay.betterbadges.common.api.attribute.BooleanAttribute;
import com.lay.betterbadges.common.api.attribute.cobblemon.MiscCobblemonAttributes;
import com.lay.betterbadges.common.api.attribute.vanilla.MiscVanillaAttributes;
import com.lay.betterbadges.common.util.PlayerCooldown;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class ServerEventsListener {

    private static PlayerCooldown cooldown;

    public static void listen(){
        PlatformEvents.SERVER_PLAYER_TICK_POST.subscribe(event -> { // Ok this is technically from cobblemon, but it's still vanilla thing
            ServerPlayer player = event.getPlayer();
            boolean applyNightVision = BooleanAttribute.toBoolean(player.getAttributeValue(MiscVanillaAttributes.NIGHT_VISION));
            if (applyNightVision) player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1, 0, false, false, false));
        });

        PlatformEvents.SERVER_TICK_POST.subscribe(event -> {
            PlayerCooldown.tickAll();
        });
    }

}
