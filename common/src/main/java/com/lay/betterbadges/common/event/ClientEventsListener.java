package com.lay.betterbadges.common.event;

import com.lay.betterbadges.common.api.attribute.cobblemon.MiscCobblemonAttributes;
import com.lay.betterbadges.common.misc.BetterBadgesKeyMappings;
import com.lay.betterbadges.common.util.cobblemon.HighlightPokemonAbilityHelper;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class ClientEventsListener {

    public static void listen(){
        ClientTickEvent.CLIENT_POST.register(instance -> {
            if (instance.level != null){
                HighlightPokemonAbilityHelper.tick();
                while (BetterBadgesKeyMappings.HIGHLIGHT_POKEMON_KEY.consumeClick()){
                    LocalPlayer player = instance.player;
                    if(player != null && player.getAttributeValue(MiscCobblemonAttributes.SCAN_POKEMON) > 0) {
                        HighlightPokemonAbilityHelper.Result result = HighlightPokemonAbilityHelper.activateHighlight(instance);
                        if (result.id() == HighlightPokemonAbilityHelper.Result.SUCCESS) {
                            instance.gui.setOverlayMessage(Component.translatable("ability.betterbadges.pokemon_scan.success", result.total()).withColor(ChatFormatting.GREEN.getColor()), false);
                        } else {
                            instance.gui.setOverlayMessage(Component.translatable("ability.betterbadges.pokemon_scan.failed", result.total()).withColor(ChatFormatting.RED.getColor()), false);
                        }
                    }
                }
            }
        });
    }

}
