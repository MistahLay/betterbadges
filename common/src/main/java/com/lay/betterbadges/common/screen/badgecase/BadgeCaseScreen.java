package com.lay.betterbadges.common.screen.badgecase;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.emblem.Emblem;
import com.lay.betterbadges.common.emblem.EmblemSlot;
import com.lay.betterbadges.common.league.BadgeSlot;
import com.lay.betterbadges.common.league.League;
import com.lay.betterbadges.common.network.ChangeLeaguePacket;
import com.lay.betterbadges.common.network.ModNetworkChannel;
import com.lay.betterbadges.common.registry.ModRegistries;
import com.lay.betterbadges.common.screen.CustomHighlightSlot;
import com.lay.betterbadges.common.screen.ModScreens;
import com.lay.betterbadges.common.screen.widget.DynamicTextureWidget;
import com.lay.betterbadges.common.util.texture.LeagueTexture;
import io.wispforest.owo.ui.base.BaseUIModelHandledScreen;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.OwoUIDrawContext;
import io.wispforest.owo.ui.core.Sizing;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BadgeCaseScreen extends BaseUIModelHandledScreen<FlowLayout, BadgeCaseScreenHandler> implements CustomHighlightSlot {

    public static final ResourceLocation HIGHLIGHT_TEXTURE = ModScreens.getGuiTexture("slot/hover");
    public static final ResourceLocation BASIC_TEXTURE = ModScreens.getGuiTexture("badgecase/badge_case");
    public static final ResourceLocation BASIC_LEAGUE = ModScreens.getGuiTexture("badgecase/league/johto/tag");

    public static final ResourceLocation INVENTORY_TEXTURE = ModScreens.getGuiTexture("badgecase/player_inventory");

    private static final String ASSET_PATH = "badge_case_screen";

    private static List<League> ORDERED_LEAGUES = new ArrayList<>();

    private static void finalizeRegistries() {
        for (League league : ModRegistries.LEAGUE) {
            if (league != League.EMPTY.get()) {
                ORDERED_LEAGUES.add(league);
            }
        }
    }

    public BadgeCaseScreen(BadgeCaseScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title, FlowLayout.class, BaseUIModelScreen.DataSource.asset(BetterBadges.of(ASSET_PATH)));
        if (ORDERED_LEAGUES.isEmpty()) finalizeRegistries();
    }

    @Override
    protected boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
        return super.isHovering(x, y, width, height, mouseX, mouseY);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        League currentLeague = this.menu.getLeague();

        var previousLeagueButton = rootComponent.childById(ButtonComponent.class, "button.previous.league@badgecase");
        previousLeagueButton.onPress(b -> {
            if (b.active) b.renderer(ButtonComponent.Renderer.texture(BASIC_TEXTURE, 192, 0, 256, 256));
            else b.renderer(ButtonComponent.Renderer.texture(BASIC_TEXTURE, 192, 11, 256, 256));
            League league = this.getPreviousLeague();
            // Update Render's Handler
            this.menu.switchLeague(league);

            // Update Server's Handler
            ModNetworkChannel.CHANNEL.clientHandle().send(new ChangeLeaguePacket(league.getId().toString()));
        });
        previousLeagueButton.renderer(ButtonComponent.Renderer.texture(BASIC_TEXTURE, 192, 0, 256, 256));

        var nextLeagueButton = rootComponent.childById(ButtonComponent.class, "button.next.league@badgecase");
        nextLeagueButton.onPress(a -> {
            if (a.active) a.renderer(ButtonComponent.Renderer.texture(BASIC_TEXTURE, 199, 0, 256, 256));
            else a.renderer(ButtonComponent.Renderer.texture(BASIC_TEXTURE, 199, 11, 256, 256));
            League league = this.getNextLeague();
            // Update Render's Handler
            this.menu.switchLeague(league);

            // Update Server's Handler
            ModNetworkChannel.CHANNEL.clientHandle().send(new ChangeLeaguePacket(league.getId().toString()));
        });

        nextLeagueButton.renderer(ButtonComponent.Renderer.texture(BASIC_TEXTURE, 199, 0, 256, 256));

        rootComponent.childById(FlowLayout.class, "league.tag@badgecase").child(
                DynamicTextureWidget.create(BASIC_LEAGUE, 0, 0, 90, 12, 90, 12)
                        .dynamicTexture(() -> LeagueTexture.of(this.menu.getLeague()).tag())
        );

//        rootComponent.surface(Surface.VANILLA_TRANSLUCENT)
//                .horizontalAlignment(HorizontalAlignment.CENTER)
//                .verticalAlignment(VerticalAlignment.CENTER);
//
//        rootComponent.child(
//            Containers.verticalFlow(Sizing.content(), Sizing.content())
//                .child(Components.texture(
//                        BASIC_TEXTURE,
//                        0, 0,
//                        256, 256,
//                        256, 256
//                ).margins(Insets.top(92).withLeft(64)))
//                .child(Components.label(Component.translatable("container.inventory").withStyle(style -> style.withColor(TextColor.fromRgb(0x6d4511)))))
//        );

//        rootComponent.child(Components.button(
//                Component.translatable("button.custom.next-league"),
//                button -> {
//                    League league = this.getNextLeague();
//                    // Update Render's Handler
//                    this.menu.switchLeague(league);
//
//                    // Update Server's Handler
//                    ModNetworkChannel.CHANNEL.clientHandle().send(new ChangeLeaguePacket(league.getId().toString()));
//                }
//        ), 1, 1);
//
//        rootComponent.child(Components.button(
//                Component.translatable("button.custom.previous-league"),
//                button -> System.out.println("previous")
//        ), 3, 3);
//
//        rootComponent.child(Components.button(
//                Component.translatable("button.custom.previous-emblem"),
//                button -> System.out.println("previous emblem")
//        ), 3, 2);
//
//        rootComponent.child(Components.button(
//                Component.translatable("button.custom.next-emblem"),
//                button -> {
//                    Emblem emblem = this.getNextEmblem();
//
//                    if(emblem == null) return;
//
//                    // Update Render's Handler
//                    this.menu.switchEmblem(emblem);
//
//                    // Update Server's Handler
//                    ModNetworkChannel.CHANNEL.clientHandle().send(new ChangeEmblemPacket(emblem.getId().toString()));
//                    System.out.println("next emblem");
//                }
//        ), 3, 1);

    }

    private FlowLayout renderInventory(FlowLayout root){
        return root;
    }

    private FlowLayout renderBadgeCase(FlowLayout root){
        return root;
    }

    private FlowLayout renderEmblem(FlowLayout root){
        return root;
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        for (int i = 0; i < 8; i++) {
            Slot slot = this.menu.slots.get(i);
            context.blit(
                    LeagueTexture.of(this.menu.getLeague()).badge(i),
                    slot.x + this.leftPos, slot.y + this.topPos, 3,
                    0, 0,
                    16, 16, 16, 16
            );
        }

        if(this.menu.getHighlightedSlot() == null) return;
        Slot slot = this.menu.getHighlightedSlot();
        context.blit(
                HIGHLIGHT_TEXTURE,
                slot.x + this.leftPos - 1, slot.y + this.topPos - 1,
                0, 0,
                18, 18, 18, 18
        );
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        if(this.menu.getEmblemBadgesManager().containsEmblem(this.menu.getEmblem())) this.renderEmblemSlots(guiGraphics);
    }

    @Override
    public @Nullable ResourceLocation highlightSlotTexture(Slot slot) {
        Container container = slot.container;
        if (container instanceof Inventory) return INVENTORY_TEXTURE;
        return null;
    }

    private void renderEmblemSlots(GuiGraphics context){
        for (EmblemSlot slot : this.menu.getEmblem().getSlots()) {
            int x = slot.x() + BadgeCaseScreenHandler.EMBLEM_CONTAINER_POS.x() + this.leftPos - 1;
            int y = slot.y() + BadgeCaseScreenHandler.EMBLEM_CONTAINER_POS.y() + this.topPos - 1;
            ResourceLocation path = ModScreens.getGuiTexture("slot/" + slot.category().name().toLowerCase() + "_slot");
            context.blit(
                    path,
                    x, y,
                    0, 0,
                    18, 18, 18, 18
            );
        }
    }

    private League getPreviousLeague(){
        return this.getNextLeague(true);
    }

    private League getNextLeague(){
        return this.getNextLeague(false);
    }

    private League getNextLeague(boolean reverse){
        List<League> leagues = ORDERED_LEAGUES;
        if (leagues.isEmpty()) {
            throw new RuntimeException("Empty League Registries");
        }

        // Find where the current menu league is
        int currentIndex = leagues.indexOf(this.menu.getLeague());

        // If the current league isn't found for some reason, default to 0
        if (currentIndex == -1) currentIndex = 0;

        // Get the next index, wrapping back to 0 if we hit the end
        int nextIndex = (currentIndex + (reverse ? -1 : 1)) % leagues.size();
        if (nextIndex <= -1) nextIndex = leagues.size() - 1;

        return leagues.get(nextIndex);
    }

    private Emblem getNextEmblem(){
        boolean next = false;
        Emblem firstEmblem = null;
        for (Emblem emblem : this.menu.getEmblemBadgesManager().getTargetItems().keySet()){
            if(firstEmblem == null && emblem != Emblem.EMPTY) {
                firstEmblem = emblem;
            }
            if(next) return emblem;
            next = emblem == this.menu.getEmblem();
        }
        return firstEmblem;
    }

}
