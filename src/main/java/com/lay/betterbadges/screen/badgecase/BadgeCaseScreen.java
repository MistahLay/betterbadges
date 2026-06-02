package com.lay.betterbadges.screen.badgecase;

import com.lay.betterbadges.emblem.Emblem;
import com.lay.betterbadges.league.League;
import com.lay.betterbadges.network.ChangeEmblemPacket;
import com.lay.betterbadges.network.ChangeLeaguePacket;
import com.lay.betterbadges.network.ModNetworkChannel;
import com.lay.betterbadges.registry.ModRegistries;
import com.lay.betterbadges.screen.ModScreenHandler;
import io.wispforest.owo.ui.base.BaseOwoHandledScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.OwoUIAdapter;
import io.wispforest.owo.ui.core.Surface;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class BadgeCaseScreen extends BaseOwoHandledScreen<FlowLayout, BadgeCaseScreenHandler> {

    public static final ResourceLocation HIGHLIGHT_TEXTURE = ModScreenHandler.getGuiTexture("slot/hover");

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
                    Emblem emblem = this.getNextEmblem();

                    if(emblem == null) return;

                    // Update Render's Handler
                    this.menu.switchEmblem(emblem);

                    // Update Server's Handler
                    ModNetworkChannel.CHANNEL.clientHandle().send(new ChangeEmblemPacket(emblem.getId().toString()));
                    System.out.println("next emblem");
                }
        ));

    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        if(this.menu.getEmblemBadgesManager().containsEmblem(this.menu.getEmblem())) this.renderEmblemSlots(context);
        if(this.menu.getHighlightedSlot() == null) return;
        Slot slot = this.menu.getHighlightedSlot();
        context.blit(
                HIGHLIGHT_TEXTURE,
                slot.x + this.leftPos - 1, slot.y + this.topPos - 1,
                0, 0,
                18, 18, 18, 18
        );
    }

    private void renderEmblemSlots(GuiGraphics context){
        for (Emblem.EmblemSlot slot : this.menu.getEmblem().getSlots()) {
            int x = slot.x() + BadgeCaseScreenHandler.EMBLEM_CONTAINER_POS.x() + this.leftPos - 1;
            int y = slot.y() + BadgeCaseScreenHandler.EMBLEM_CONTAINER_POS.y() + this.topPos - 1;
            ResourceLocation path = ModScreenHandler.getGuiTexture("slot/" + slot.category().name().toLowerCase() + "_slot");
            context.blit(
                    path,
                    x, y,
                    0, 0,
                    18, 18, 18, 18
            );
        }
    }

    private League getNextLeague(){
        // Can this thing get to O(1)?
        boolean next = false;
        League firstLeague = null;
        for (League league : ModRegistries.LEAGUE){
            // Get the first league for future reference
            if(firstLeague == null && league != League.EMPTY) {
                firstLeague = league;
            }

            // Return the found league
            if(next) return league;

            // Find the current league and state that the next will be the returned league
            if(league == this.menu.getLeague()) next = true;
        }
        // If nothing the first league will be used
        // And if it's still an empty, then that's crazy
        if(firstLeague != null) return firstLeague;
        throw new RuntimeException("Empty League Registries");
    }

    private Emblem getNextEmblem(){
        boolean next = false;
        Emblem firstEmblem = null;
        for (Emblem emblem : this.menu.getEmblemBadgesManager().getTargetItems().keySet()){
            if(firstEmblem == null && emblem != Emblem.EMPTY) {
                firstEmblem = emblem;
            }
            if(next) return emblem;

            if(emblem == this.menu.getEmblem()) next = true;
        }
        return firstEmblem;
    }
}
