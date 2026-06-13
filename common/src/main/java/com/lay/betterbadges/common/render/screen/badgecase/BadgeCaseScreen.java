package com.lay.betterbadges.common.render.screen.badgecase;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.emblem.Boost;
import com.lay.betterbadges.common.emblem.Emblem;
import com.lay.betterbadges.common.emblem.EmblemSlot;
import com.lay.betterbadges.common.item.badges.BadgeItem;
import com.lay.betterbadges.common.league.League;
import com.lay.betterbadges.common.league.attributes.BadgeAttributesManager;
import com.lay.betterbadges.common.network.ChangeEmblemPacket;
import com.lay.betterbadges.common.network.ChangeLeaguePacket;
import com.lay.betterbadges.common.network.ModNetworkChannel;
import com.lay.betterbadges.common.render.screen.CustomHighlightSlot;
import com.lay.betterbadges.common.render.screen.ModScreens;
import com.lay.betterbadges.common.render.screen.widget.DynamicTextureWidget;
import com.lay.betterbadges.common.util.Utils;
import com.lay.betterbadges.common.util.texture.EmblemTexture;
import com.lay.betterbadges.common.util.texture.LeagueTexture;
import io.wispforest.owo.ui.base.BaseUIModelHandledScreen;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.Positioning;
import io.wispforest.owo.ui.core.Sizing;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BadgeCaseScreen extends BaseUIModelHandledScreen<FlowLayout, BadgeCaseScreenHandler> implements CustomHighlightSlot {

    public static final ResourceLocation HIGHLIGHT_TEXTURE = ModScreens.getGuiTexture("slot/hover");
    public static final ResourceLocation BASIC_TEXTURE = ModScreens.getGuiTexture("badgecase/badge_case");
    public static final ResourceLocation BASIC_LEAGUE = ModScreens.getGuiTexture("badgecase/league/johto/tag");

    public static final ResourceLocation INVENTORY_TEXTURE = ModScreens.getGuiTexture("badgecase/player_inventory");

    private static final String ASSET_PATH = "badge_case_screen";

    private List<League> ORDERED_LEAGUES = new ArrayList<>();
    private List<Emblem> ORDERED_EMBLEM = new ArrayList<>();

    public BadgeCaseScreen(BadgeCaseScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title, FlowLayout.class, BaseUIModelScreen.DataSource.asset(BetterBadges.of(ASSET_PATH)));
        ORDERED_LEAGUES.addAll(handler.getLeagueBadgesManager().getLeagues());
        ORDERED_EMBLEM.addAll(handler.getEmblemBadgesManager().getEmblems());
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        setupLeagueButtons(rootComponent);
        setupEmblemHud(rootComponent.childById(FlowLayout.class, "emblem@badgecase"));
        rootComponent.childById(FlowLayout.class, "league.tag@badgecase").child(
                DynamicTextureWidget.create(BASIC_LEAGUE, 0, 0, 90, 12, 90, 12)
                        .dynamicTexture(() -> LeagueTexture.of(this.menu.getLeague()).tag())
        );
    }

    private void setupLeagueButtons(FlowLayout root){
        var previousLeagueButton = root.childById(ButtonComponent.class, "button.previous.league@badgecase");
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

        var nextLeagueButton = root.childById(ButtonComponent.class, "button.next.league@badgecase");
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
    }

    private void setupEmblemHud(FlowLayout root){
        if (this.ORDERED_EMBLEM == null || this.ORDERED_EMBLEM.isEmpty()) return;
        var previousLeagueButton = Components.button(Component.empty(), b -> {
            if (b.active) b.renderer(ButtonComponent.Renderer.texture(BASIC_TEXTURE, 192, 0, 256, 256));
            else b.renderer(ButtonComponent.Renderer.texture(BASIC_TEXTURE, 192, 11, 256, 256));
            Emblem emblem = this.getPreviousEmblem();
            this.menu.switchEmblem(emblem);

            ModNetworkChannel.CHANNEL.clientHandle().send(new ChangeEmblemPacket(emblem.getId().toString()));
        });
        previousLeagueButton.sizing(Sizing.fixed(6), Sizing.fixed(11));
        previousLeagueButton.renderer(ButtonComponent.Renderer.texture(BASIC_TEXTURE, 192, 0, 256, 256));

        var nextLeagueButton = Components.button(Component.empty(), a -> {
            if (a.active) a.renderer(ButtonComponent.Renderer.texture(BASIC_TEXTURE, 199, 0, 256, 256));
            else a.renderer(ButtonComponent.Renderer.texture(BASIC_TEXTURE, 199, 11, 256, 256));
            Emblem emblem = this.getNextEmblem();
            this.menu.switchEmblem(emblem);

            ModNetworkChannel.CHANNEL.clientHandle().send(new ChangeEmblemPacket(emblem.getId().toString()));
        });
        nextLeagueButton.sizing(Sizing.fixed(6), Sizing.fixed(11));
        nextLeagueButton.renderer(ButtonComponent.Renderer.texture(BASIC_TEXTURE, 199, 0, 256, 256));

        root.child(Components.texture(
                BASIC_TEXTURE,
                0, 96,
                108, 117,
                256, 256
        ));

        root.child(DynamicTextureWidget.create(
                BASIC_TEXTURE,
                0, 0,
                96, 96,
                96, 96
        ).dynamicTexture(() -> EmblemTexture.of(this.menu.getEmblem()).emblemTexture())
                .positioning(Positioning.relative(50, 70)));

        FlowLayout navigation = Containers.horizontalFlow(Sizing.content(), Sizing.content());

        navigation.child(previousLeagueButton);
        navigation.child(nextLeagueButton);

        navigation.gap(5);
        navigation.positioning(Positioning.relative(5, 3));

        root.child(navigation);
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

        League league = this.menu.getLeague();
        Emblem emblem = this.menu.getEmblem();
        BadgeCaseScreenHandler.ScreenEmblemSlot highlightedSlot = this.menu.getHighlightedSlot();

        Boost boost = null;
        SimpleContainer container = this.menu.getLeagueBadgesManager().getBadgeContainer(league);

        if (highlightedSlot != null) {
            context.blit(
                    HIGHLIGHT_TEXTURE,
                    highlightedSlot.x + this.leftPos - 1, highlightedSlot.y + this.topPos - 1, 4,
                    0, 0,
                    18, 18, 18, 18
            );
            boost = highlightedSlot.getEmblem().getSlot(highlightedSlot.index - this.menu.getEmblemIndex()).category();
        } else if (this.hoveredSlot instanceof BadgeCaseScreenHandler.ScreenEmblemSlot screenEmblemSlot) {
            boost = screenEmblemSlot.getEmblem().getSlot(screenEmblemSlot.index - this.menu.getEmblemIndex()).category();
        }

        for (int i = 0; i < 8; i++) {
            Slot slot = this.menu.slots.get(i);
            if (boost != null) {
                ItemStack item = container.getItem(i);
                if (!item.isEmpty() && league.getBadge(i).containsBoost(boost)) {
                    if (BadgeAttributesManager.getAttributes((BadgeItem) item.getItem()).get(boost) != null) {
                        context.setColor(boost.r, boost.g, boost.b, 1.0f);
                    }
                }
            }
            context.blit(
                    LeagueTexture.of(league).badge(i),
                    slot.x + this.leftPos - 1, slot.y + this.topPos - 1, 3,
                    0, 0,
                    18, 18, 18, 18
            );
            context.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }

        if (emblem != null && emblem != Emblem.EMPTY) for (int i = 0; i < emblem.getTotalSlots(); i++) {
            int emblemIndex = this.menu.getEmblemIndex();
            Slot slot = this.menu.slots.get(i + emblemIndex);
            if (slot instanceof BadgeCaseScreenHandler.ScreenEmblemSlot) {
                context.blit(
                        EmblemTexture.boostTexture(emblem.getSlot(i).category()),
                        slot.x + this.leftPos, slot.y + this.topPos, 4,
                        0, 0,
                        16, 16, 16, 16
                );
            }
        }
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
        return Utils.getNextOfList(this.ORDERED_LEAGUES, this.menu.getLeague(), true);
    }

    private League getNextLeague(){
        return Utils.getNextOfList(this.ORDERED_LEAGUES, this.menu.getLeague(), false);
    }

    private Emblem getPreviousEmblem(){
        return Utils.getNextOfList(this.ORDERED_EMBLEM, this.menu.getEmblem(), true);
    }

    private Emblem getNextEmblem(){
        return Utils.getNextOfList(this.ORDERED_EMBLEM, this.menu.getEmblem(), false);
    }
}
