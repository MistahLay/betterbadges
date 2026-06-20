package com.lay.betterbadges.common.misc;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;

public class BetterBadgesKeyMappings {

    public static final KeyMapping HIGHLIGHT_POKEMON_KEY = new KeyMapping(
            "key.betterbadges.highlight_pokemon",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_P,
            "category.betterbadges.keys"
    );

    public static final KeyMapping THROW_PEARL_KEY = new KeyMapping(
            "key.betterbadges.throw_pearl",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_O,
            "category.betterbadges.keys"
    );

    public static void registerKeymaps(){
        KeyMappingRegistry.register(HIGHLIGHT_POKEMON_KEY);
        KeyMappingRegistry.register(THROW_PEARL_KEY);
    }

}
