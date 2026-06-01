package com.lay.betterbadges.screen.badgecase;

import com.lay.betterbadges.emblem.Emblem;
import com.lay.betterbadges.league.League;
import com.lay.betterbadges.network.ChangeEmblemPacket;
import com.lay.betterbadges.network.ChangeLeaguePacket;
import com.lay.betterbadges.network.ModNetworkChannel;
import com.lay.betterbadges.registry.ModRegistries;
import io.wispforest.owo.ui.base.BaseOwoHandledScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.OwoUIAdapter;
import io.wispforest.owo.ui.core.Surface;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class BadgeCaseScreen extends BaseOwoHandledScreen<FlowLayout, BadgeCaseScreenHandler> {
    public BadgeCaseScreen(BadgeCaseScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected @NotNull OwoUIAdapter createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent.surface(Surface.VANILLA_TRANSLUCENT);

        rootComponent.child(Components.button(
                Component.translatable("button.custom.next-league"),
                button -> {
                    League league = this.getNextLeague();
                    // Update Render's Handler
                    this.menu.switchLeague(league);

                    // Update Server's Handler
                    ModNetworkChannel.CHANNEL.clientHandle().send(new ChangeLeaguePacket(league.getId().toString()));
                }
        ));

        rootComponent.child(Components.button(
                Component.translatable("button.custom.previous-league"),
                button -> System.out.println("previous")
        ));

        rootComponent.child(Components.button(
                Component.translatable("button.custom.previous-emblem"),
                button -> System.out.println("previous emblem")
        ));

        rootComponent.child(Components.button(
                Component.translatable("button.custom.next-emblem"),
                button -> {
                    Emblem league = this.getNextEmblem();
                    // Update Render's Handler
                    this.menu.switchEmblem(league);

                    // Update Server's Handler
                    ModNetworkChannel.CHANNEL.clientHandle().send(new ChangeEmblemPacket(league.getId().toString()));
                    System.out.println("next emblem");
                }
        ));

    }

    private League getNextLeague(){
        // Can this thing get to O(1)?
        boolean next = false;
        League firstLeague = null;
        for (League league : ModRegistries.LEAGUE){
            // Get the first league for future reference
            if(firstLeague == null && league != League.EMPTY) firstLeague = league;

            // Return the found league
            if(next) return league;

            // Find the current league and state that the next will be the returned league
            if(league == this.menu.getLeague()) next = true;
            System.out.println(league.getId().toString());
        }
        // If nothing the first league will be used
        // And if it's still an empty, then that's crazy
        if(firstLeague != null) return firstLeague;
        throw new RuntimeException("Empty League Registries");
    }

    private Emblem getNextEmblem(){
        boolean next = false;
        Emblem firstEmblem = null;
        for (Emblem emblem : ModRegistries.EMBLEM){
            if(firstEmblem == null && emblem != Emblem.EMPTY) firstEmblem = emblem;

            if(next) return emblem;

            if(emblem == this.menu.getEmblem()) next = true;
            System.out.println(emblem.getId().toString());
        }
        if(firstEmblem != null) return firstEmblem;
        throw new RuntimeException("Empty Emblem Registries");
    }
}
