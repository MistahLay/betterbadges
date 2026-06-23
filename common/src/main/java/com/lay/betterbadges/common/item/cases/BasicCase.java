package com.lay.betterbadges.common.item.cases;

import com.google.common.base.Suppliers;
import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.emblem.Emblem;
import com.lay.betterbadges.common.inventory.EmblemBadgesManager;
import com.lay.betterbadges.common.inventory.LeagueBadgesManager;
import com.lay.betterbadges.common.item.bounded.BoundItem;
import com.lay.betterbadges.common.item.bounded.BoundItemWrapper;
import com.lay.betterbadges.common.api.league.ModLeagues;
import com.lay.betterbadges.common.registry.BetterBadgesRegistries;
import com.lay.betterbadges.common.render.screen.badgecase.BadgeCaseScreenHandler;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BasicCase extends BoundItem implements GeoItem {

    public static final String OPENING_CONTROLLER = "opening_controller";
    public static final String OPEN_CASE_ID = "open_case";
    public static final String CLOSE_CASE_ID = "close_case";

    public static final RawAnimation OPEN_CASE_ANIM = RawAnimation.begin().thenPlayAndHold("Open");
    public static final RawAnimation CLOSE_CASE_ANIM = RawAnimation.begin().thenPlayAndHold("Close");

    public static final RawAnimation OPEN_CASE_LEFT_ANIM = RawAnimation.begin().thenPlayAndHold("Open.Left");
    public static final RawAnimation OPEN_CASE_RIGHT_ANIM = RawAnimation.begin().thenPlayAndHold("Open.Right");

    public static final RawAnimation CLOSE_CASE_LEFT_ANIM = RawAnimation.begin().thenPlayAndHold("Close.Left");
    public static final RawAnimation CLOSE_CASE_RIGHT_ANIM = RawAnimation.begin().thenPlayAndHold("Close.Right");

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public BasicCase(Properties properties) {
        super(properties);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack itemStack) {
        super.verifyComponentsAfterLoad(itemStack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, InteractionHand interactionHand) {
        if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResultHolder.fail(player.getItemInHand(interactionHand));
        if(BetterBadgesRegistries.LEAGUE.getIds().size() == 1) return InteractionResultHolder.fail(player.getItemInHand(interactionHand));

        EquipmentSlot slot = switch (interactionHand) {
            case MAIN_HAND -> EquipmentSlot.MAINHAND;
            case OFF_HAND -> EquipmentSlot.OFFHAND;
        };

        ItemStack stack = player.getItemInHand(interactionHand);

        if (slot == EquipmentSlot.OFFHAND) return InteractionResultHolder.fail(stack);

        BadgeCaseWrapper badgeCase = new BadgeCaseWrapper(stack);

        if (badgeCase.isGuiOpen()) return InteractionResultHolder.fail(stack);

        BetterBadges.LOGGER.info("{}", badgeCase);

        boolean modified = false;

        if (!badgeCase.canUse(player, true)) {
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(
                    Component.translatable("actionbar.custom.not-owner", badgeCase.getItemOwnerName(serverPlayer.server)))
            );
            modified = true;
        }
        if (slot == EquipmentSlot.MAINHAND) {
            ItemStack offHand = player.getOffhandItem();
            if (!offHand.isEmpty() && this.consumeEmblem(badgeCase, offHand, player)) {
                offHand.shrink(1);
                modified = true;
            }
        }
        EmblemBadgesManager emblemBadgesManager = badgeCase.getEmblemInventoryManager();
        if (badgeCase.getCurrentEmblem() == null && emblemBadgesManager != null && !emblemBadgesManager.getEmblems().isEmpty()) {
            badgeCase.setCurrentEmblem(emblemBadgesManager.getEmblems().getFirst(), false);
            modified = true;
        }
        if (badgeCase.getLeagueInventoryManager(serverPlayer.registryAccess()) == null) {
            badgeCase.setInventoryManager(LeagueBadgesManager.createEmpty(), serverPlayer.registryAccess());
            badgeCase.setCurrentLeague(ModLeagues.KANTO.get());
            modified = true;
        }
        if (badgeCase.getCurrentLeague() == null) {
            badgeCase.setCurrentLeague(ModLeagues.KANTO.get());
            modified = true;
        }
        if (modified) return InteractionResultHolder.pass(stack);
        MenuRegistry.openExtendedMenu(serverPlayer, new ExtendedMenuProvider() {
            @Override
            public void saveExtraData(FriendlyByteBuf buf) {
                buf.writeJsonWithCodec(EquipmentSlot.CODEC, slot);
            }

            @Override
            public Component getDisplayName() {
                return stack.getDisplayName();
            }

            @Override
            public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                return new BadgeCaseScreenHandler(i, player.getInventory(), SlotAccess.forEquipmentSlot(player, slot));
            }
        });

        badgeCase.setGuiOpen(true);
        this.geoCache.getManagerForId(GeoItem.getOrAssignId(stack, (ServerLevel) player.level())).setData(DataTickets.ITEMSTACK, stack);

        return InteractionResultHolder.fail(stack);
    }

    private boolean consumeEmblem(BadgeCaseWrapper badgeCase, ItemStack emblemItem, Player player){
        Emblem emblem = Emblem.getEmblemFromItem(emblemItem.getItem());
        EmblemBadgesManager emblemBadgesManager = badgeCase.getEmblemInventoryManager();
        BoundItemWrapper boundItem = new BoundItemWrapper(emblemItem);
        if (emblemBadgesManager == null) {
            emblemBadgesManager = new EmblemBadgesManager(new HashMap<>());
        }
        if (!boundItem.canUse(player)) return false;
        if (emblemBadgesManager.containsEmblem(emblem)) return false;
        emblemBadgesManager.addEmblem(emblem);
        badgeCase.setEmblemInventoryManager(emblemBadgesManager, false);
        badgeCase.setCurrentEmblem(emblem, false);
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>(
                        this,
                        OPENING_CONTROLLER,
                        0,
                        state -> {
                            final ItemDisplayContext context = state.getData(DataTickets.ITEM_RENDER_PERSPECTIVE);

                            final BadgeCaseWrapper badgeCase = new BadgeCaseWrapper(state.getData(DataTickets.ITEMSTACK));

                            boolean isOpen = badgeCase.isGuiOpen();

                            if (context == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
                                return state.setAndContinue(isOpen ? OPEN_CASE_RIGHT_ANIM : CLOSE_CASE_RIGHT_ANIM);
                            } else if (context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND) {
                                return state.setAndContinue(isOpen ? OPEN_CASE_LEFT_ANIM : CLOSE_CASE_LEFT_ANIM);
                            } else if (context.firstPerson()){
                                return state.setAndContinue(isOpen ? OPEN_CASE_ANIM : CLOSE_CASE_ANIM);
                            }
                            return PlayState.STOP;
                        }
                )
        );
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(
            new GeoRenderProvider() {
                private final Supplier<GeoItemRenderer<BasicCase>> renderer = Suppliers.memoize(() -> new GeoItemRenderer<>(BasicCase.this));

                @Override
                public @Nullable BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                    return this.renderer.get();
                }
            }
        );
    }

    @Override
    public boolean isPerspectiveAware() {
        return true;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    public enum BasicCaseOpening implements StringRepresentable {

        OPENING(0, "opening"),
        OPENED(1, "opened"),
        CLOSING(2, "closing"),
        CLOSED(3, "closed");

        private final int id;
        private final String name;

        BasicCaseOpening(int id, String name){
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public boolean inBetween(){
            return this == OPENING || this == CLOSING;
        }

        public boolean inFinal(){
            return this == OPENED || this == CLOSED;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public BasicCaseOpening getRelated(){
            return switch (this){
                case OPENING -> OPENED;
                case OPENED -> OPENING;
                case CLOSING -> CLOSED;
                case CLOSED -> CLOSING;
            };
        }
    }
}
