package com.lay.betterbadges.common.render.screen.badgecase;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.emblem.Boost;
import com.lay.betterbadges.common.api.emblem.Emblem;
import com.lay.betterbadges.common.api.emblem.EmblemSlot;
import com.lay.betterbadges.common.item.badges.BadgeItem;
import com.lay.betterbadges.common.api.league.League;
import com.lay.betterbadges.common.api.league.attributes.BadgeAttributesManager;
import com.lay.betterbadges.common.network.ChangeEmblemPacket;
import com.lay.betterbadges.common.network.ChangeLeaguePacket;
import com.lay.betterbadges.common.network.ModNetworkChannel;
import com.lay.betterbadges.common.render.screen.CustomHighlightSlot;
import com.lay.betterbadges.common.render.screen.ModScreens;
import com.lay.betterbadges.common.render.screen.widget.DynamicTextureWidget;
import com.lay.betterbadges.common.util.Utils;
import com.lay.betterbadges.common.util.texture.EmblemTexture;
import com.lay.betterbadges.common.util.texture.LeagueTexture;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.wispforest.owo.ui.base.BaseUIModelHandledScreen;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.Positioning;
import io.wispforest.owo.ui.core.Sizing;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
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
import java.util.function.Supplier;

public class BadgeCaseScreen extends BaseUIModelHandledScreen<FlowLayout, BadgeCaseScreenHandler> implements CustomHighlightSlot {

    public static final ResourceLocation HIGHLIGHT_TEXTURE = ModScreens.getGuiTexture("slot/hover");
    public static final ResourceLocation BASIC_TEXTURE = ModScreens.getGuiTexture("badgecase/badge_case");
    public static final ResourceLocation BASIC_LEAGUE = ModScreens.getGuiTexture("badgecase/league/johto/tag");

    public static final ResourceLocation INVENTORY_TEXTURE = ModScreens.getGuiTexture("badgecase/player_inventory");

    private int ticks = 0;
    private Slot lastHovered = null;

    private static final String ASSET_PATH = "badge_case_screen";

    private final List<League> orderedLeagues = new ArrayList<>();
    private final List<Emblem> orderedEmblems = new ArrayList<>();

    private final BadgeShineAnimation shineAnimation = new BadgeShineAnimation();

    public BadgeCaseScreen(BadgeCaseScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title, FlowLayout.class, BaseUIModelScreen.DataSource.asset(BetterBadges.of(ASSET_PATH)));
        this.orderedLeagues.addAll(handler.getLeagueBadgesManager().getLeagues());
        this.orderedEmblems.addAll(handler.getEmblemBadgesManager().getEmblems());
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        this.setupLeague(rootComponent);
        this.setupEmblem(rootComponent);
    }

    private void setupLeague(FlowLayout root){
        FlowLayout navigation = root.childById(FlowLayout.class, "league.navigation@badgecase");
        navigation.child(this.createArrowButton(true, BASIC_TEXTURE, () -> {
            League league = this.getNextLeague();
            if (this.menu.getLeague() == league) return null;
            this.menu.switchLeague(league);
            this.shineAnimation.reset();
            this.shineAll();
            return new ChangeLeaguePacket(league.getId().toString());
        }));
        navigation.child(DynamicTextureWidget.create(BASIC_LEAGUE, 0, 0, 90, 12, 90, 12)
                .dynamicTexture(() -> LeagueTexture.of(this.menu.getLeague()).tag()));
        navigation.child(this.createArrowButton(false, BASIC_TEXTURE, () -> {
            League league = this.getPreviousLeague();
            if (this.menu.getLeague() == league) return null;
            this.menu.switchLeague(league);
            this.shineAnimation.reset();
            this.shineAll();
            return new ChangeLeaguePacket(league.getId().toString());
        }));
    }

    private void setupEmblem(FlowLayout root){
        if (this.orderedEmblems == null || this.orderedEmblems.isEmpty()) return;
        FlowLayout emblemLayout = root.childById(FlowLayout.class, "emblem@badgecase");
        var nextLeagueButton = Components.button(Component.empty(), a -> {
            if (a.active) a.renderer(ButtonComponent.Renderer.texture(BASIC_TEXTURE, 199, 0, 256, 256));
            else a.renderer(ButtonComponent.Renderer.texture(BASIC_TEXTURE, 199, 11, 256, 256));
            Emblem emblem = this.getNextEmblem();
            this.menu.switchEmblem(emblem);

            ModNetworkChannel.CHANNEL.clientHandle().send(new ChangeEmblemPacket(emblem.getId().toString()));
        });
        nextLeagueButton.sizing(Sizing.fixed(6), Sizing.fixed(11));
        nextLeagueButton.renderer(ButtonComponent.Renderer.texture(BASIC_TEXTURE, 199, 0, 256, 256));

        emblemLayout.child(Components.texture(
                BASIC_TEXTURE,
                0, 96,
                108, 117,
                256, 256
        ));

        emblemLayout.child(DynamicTextureWidget.create(
                BASIC_TEXTURE,
                0, 0,
                96, 96,
                96, 96
        ).dynamicTexture(() -> EmblemTexture.of(this.menu.getEmblem()).emblemTexture())
                .positioning(Positioning.relative(50, 70)));

        FlowLayout navigation = Containers.horizontalFlow(Sizing.content(), Sizing.content());

        navigation.child(this.createArrowButton(true, BASIC_TEXTURE, () -> {
            Emblem emblem = this.getPreviousEmblem();
            this.menu.switchEmblem(emblem);
            this.shineAnimation.reset();
            return new ChangeEmblemPacket(emblem.getId().toString());
        }));

        navigation.child(this.createArrowButton(false, BASIC_TEXTURE, () -> {
            League league = this.getPreviousLeague();
            if (this.menu.getLeague() == league) return null;
            this.menu.switchLeague(league);
            this.shineAnimation.reset();
            return new ChangeLeaguePacket(league.getId().toString());
        }));

        navigation.gap(5);
        navigation.positioning(Positioning.relative(5, 3));

        emblemLayout.child(navigation);
    }

    private ButtonComponent createArrowButton(boolean right, ResourceLocation texture, Supplier<Record> packet){
        int u = right ? 192 : 199;
        ButtonComponent arrowButton = Components.button(Component.empty(), button -> {
            if (button.active) button.renderer(ButtonComponent.Renderer.texture(texture, u, 0, 256, 256));
            else button.renderer(ButtonComponent.Renderer.texture(texture, u, 11, 256, 256));
            Record record = packet.get();
            if (record != null) ModNetworkChannel.CHANNEL.clientHandle().send(record);
        });
        arrowButton.sizing(Sizing.fixed(6), Sizing.fixed(11));
        arrowButton.renderer(ButtonComponent.Renderer.texture(BASIC_TEXTURE, u, 0, 256, 256));
        return arrowButton;
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        int k = this.leftPos;
        int l = this.topPos;
        context.pose().translate((float)k, (float)l, 0.0F);
        League league = this.menu.getLeague();
        Emblem emblem = this.menu.getEmblem();
        BadgeCaseScreenHandler.ScreenEmblemSlot highlightedSlot = this.menu.getHighlightedSlot();

        Boost boost = null;
        SimpleContainer container = this.menu.getLeagueBadgesManager().getBadgeContainer(league);

        if (highlightedSlot != null) {
            context.blit(
                    HIGHLIGHT_TEXTURE,
                    highlightedSlot.x - 1, highlightedSlot.y - 1, 4,
                    0, 0,
                    18, 18, 18, 18
            );
            boost = highlightedSlot.getEmblem().getSlot(highlightedSlot.index - this.menu.getEmblemIndex()).category();
        } else if (this.hoveredSlot instanceof BadgeCaseScreenHandler.ScreenEmblemSlot screenEmblemSlot) {
            boost = screenEmblemSlot.getEmblem().getSlot(screenEmblemSlot.index - this.menu.getEmblemIndex()).category();
        }

        for (int i = 0; i < 8; i++) {
            if (boost != null) {
                ItemStack item = container.getItem(i);
                if (!item.isEmpty() && league.getBadge(i).containsBoost(boost)) {
                    if (BadgeAttributesManager.getAttributes((BadgeItem) item.getItem()).get(boost) != null) {
                        context.setColor(boost.r, boost.g, boost.b, 1.0f);
                    }
                }
            }
            Slot slot = this.menu.getSlot(i);
            context.blit(
                    LeagueTexture.of(league).badge(i),
                    slot.x - 1, slot.y - 1, 3,
                    0, 0,
                    18, 18, 18, 18
            );
            context.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }

        if (emblem != null && emblem != Emblem.EMPTY) for (int i = 0; i < emblem.getTotalSlots(); i++) {
            int emblemIndex = this.menu.getEmblemIndex();
            Slot slot = this.menu.getSlot(i + emblemIndex);
            if (slot instanceof BadgeCaseScreenHandler.ScreenEmblemSlot) {
                context.blit(
                        EmblemTexture.boostTexture(emblem.getSlot(i).category()),
                        slot.x, slot.y, 4,
                        0, 0,
                        16, 16, 16, 16
                );
            }
        }

        this.shineAnimation.render(context);
    }

    public BadgeShineAnimation getShineAnimation(){
        return this.shineAnimation;
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        if (++ticks > 1) {
            this.shineAnimation.addFrame();
            ticks = 0;
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

    public void shineHoveredSlot(){
        if (this.hoveredSlot != null && this.lastHovered != this.hoveredSlot) {
            this.lastHovered = this.hoveredSlot;
            this.shineAnimation.addToRender(this.hoveredSlot);
        }
    }

    private void shineAll(){
        this.shineAnimation.reset();
        for (int i = 0; i < 8; i++) {
            Slot slot = this.menu.getSlot(i);
            this.shineAnimation.addToRender(slot);
        }
        for (int i = this.menu.getEmblemIndex(); i < this.menu.getEmblem().getTotalSlots(); i++) {
            Slot slot = this.menu.getSlot(i);
            this.shineAnimation.addToRender(slot);
        }
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
        return Utils.getNextOfList(this.orderedLeagues, this.menu.getLeague(), true);
    }

    private League getNextLeague(){
        return Utils.getNextOfList(this.orderedLeagues, this.menu.getLeague(), false);
    }

    private Emblem getPreviousEmblem(){
        return Utils.getNextOfList(this.orderedEmblems, this.menu.getEmblem(), true);
    }

    private Emblem getNextEmblem(){
        return Utils.getNextOfList(this.orderedEmblems, this.menu.getEmblem(), false);
    }

    /**
     * Returns if it did not override
     */
    public boolean renderHighlightedSlotOverride(GuiGraphics arg, int i, int j, int k){
        if (this.hoveredSlot == null) return false;
        ResourceLocation texture = this.highlightSlotTexture(this.hoveredSlot);
        if (texture != null) {
            arg.blit(
                    texture,
                    this.hoveredSlot.x - 1, this.hoveredSlot.y - 1, 4,
                    191, 0,
                    18, 18,
                    256, 256
            );
            return true;
        }
        if (this.hoveredSlot instanceof BadgeCaseScreenHandler.ScreenBadgeSlot || this.hoveredSlot instanceof BadgeCaseScreenHandler.ScreenEmblemSlot) {
            this.shineHoveredSlot();
            return true;
        }
        return false;
    }
}
