package com.lay.betterbadges.common.util.cobblemon;

import com.cobblemon.mod.common.api.events.pokeball.PokemonCatchRateEvent;
import com.cobblemon.mod.common.api.pokeball.catching.CatchRateModifier;
import com.cobblemon.mod.common.api.pokeball.catching.modifiers.MultiplierModifier;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.pokeball.PokeBall;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.util.PlayerExtensionsKt;
import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.attribute.cobblemon.CatchingAttributes;
import com.lay.betterbadges.common.util.PlayerAttributeHelper;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class CatchModifierHelper extends PlayerAttributeHelper {

    private float value;
    private final Pokemon pokemon;
    private final PokeBall pokeball;

    public static float modify(PokemonCatchRateEvent event){
        if (!(event.getThrower() instanceof ServerPlayer player)) return event.getCatchRate();
        CatchModifierHelper helper = new CatchModifierHelper(event.getCatchRate(), player, event.getPokemonEntity().getPokemon(), event.getPokeBallEntity().getPokeBall());
        float result = helper
                .applyByStatus()
                .applyByTypes()
                .applyFlatBoosts()
                .applyOfBoostedPokeball()
                .result();
        BetterBadges.LOGGER.info("original: {}\nfinal: {}", event.getCatchRate(), result);
        return result;
    }

    public CatchModifierHelper(Float original, ServerPlayer thrower, Pokemon pokemon, PokeBall pokeball){
        super(thrower);
        this.value = original;
        this.pokemon = pokemon;
        this.pokeball = pokeball;
    }

    public CatchModifierHelper applyFlatBoosts(){
        this.multiplyByAttribute(CatchingAttributes.FLAT_CATCHING);

        if (PlayerExtensionsKt.isInBattle(this.player)) this.multiplyByAttribute(CatchingAttributes.IN_BATTLE_CATCHING);
        else this.multiplyByAttribute(CatchingAttributes.OUTSIDE_BATTLE_CATCHING);

        if (this.pokemon.getStatus() != null) this.multiplyByAttribute(CatchingAttributes.FLAT_STATUS_CATCHING);

        return this;
    }

    /**
     *  Everything but a flat MultiplierModifier pokeballs (e.g. Net Balls not Pokeballs/Ultra Balls)
     *  TODO: Add Blacklist
     */
    public CatchModifierHelper applyOfBoostedPokeball(){
        if (this.pokeball.getCatchRateModifier() instanceof MultiplierModifier) return this;

        if (PlayerExtensionsKt.isInBattle(this.player)) this.multiplyByAttribute(CatchingAttributes.SPECIAL_IN_BATTLE_CATCHING);
        else this.multiplyByAttribute(CatchingAttributes.SPECIAL_OUTSIDE_BATTLE_CATCHING);

        return this;
    }

    public CatchModifierHelper applyByStatus(){
        if (this.pokemon.getStatus() != null) {
            this.multiplyByAttribute(CatchingAttributes.getByStatus(this.pokemon.getStatus().getStatus()));
        }
        return this;
    }

    public CatchModifierHelper applyByTypes() {
        for (ElementalType type : this.pokemon.getTypes()){ // Which means that if there's like dual type pokemon, 1.5x1.5 TODO: Configurable to primary type
            this.multiplyByAttribute(CatchingAttributes.getByElementalType(type));
        }
        return this;
    }

    private void multiplyByAttribute(Holder<Attribute> attribute){
        this.multiply(this.attributeValue(attribute));
    }

    private void multiply(float modifier){
        this.value = CatchRateModifier.Behavior.MULTIPLY.getMutator().invoke(modifier, this.value);
    }

    public float result() {
        return this.value;
    }
}
