package com.lay.betterbadges.common.util.cobblemon;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.lay.betterbadges.common.api.attribute.cobblemon.MiscCobblemonAttributes;
import com.lay.betterbadges.common.render.entity.HighlightEntityManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HighlightPokemonAbilityHelper {

    private static final HighlightEntityManager highlightManager = new HighlightEntityManager();

    public static int DELAY_TICKS = 20 * 60;
    public static int GLOWING_TIME = 20 * 10;

    public static int delay = 0;
    public static int glowingTime = 0;

    public static void tick(){
        if (glowingTime <= 0) {
            highlightManager.clearAll();
        } else {
            glowingTime--;
        }
        if (delay > 0) {
            delay--;
        }
    }

    public static Result activateHighlight(Minecraft instance){
        if (delay > 0 || instance.player == null) return new Result(Result.FAILED, delay / 20);
        int value = (int) instance.player.getAttributeValue(MiscCobblemonAttributes.SCAN_POKEMON);
        if (value <= 0) return new Result(Result.FAILED, 0);
        delay = DELAY_TICKS;
        glowingTime = GLOWING_TIME;
        return new Result(Result.SUCCESS, highlightAllPokemon(instance, value));
    }

    private static int highlightAllPokemon(Minecraft instance, int cube){
        LocalPlayer player = instance.player;
        if (player == null) return 0;
        Vec3 center = player.position();
        return player.clientLevel.getEntitiesOfClass(PokemonEntity.class, new AABB(center.add(cube, cube, cube), center.add(-cube, -cube, -cube)), entity -> {
            highlightManager.highlight(entity,
                    entity.getPokemon().getShiny() ? 0xFFD700 : 0xFFFFFF
            );
            return true;
        }).size();
    }

    public record Result(int id, int total) {
        public static int SUCCESS = 0;
        public static int FAILED = 1;
    }
}
