package com.lay.betterbadges.common.component;

import com.lay.betterbadges.common.BetterBadges;
import com.lay.betterbadges.common.api.emblem.Emblem;
import com.lay.betterbadges.common.api.emblem.EmblemTargetItem;
import com.lay.betterbadges.common.api.league.League;
import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.UnaryOperator;

public class BetterBadgesDataComponents {

    private static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(BetterBadges.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<DataComponentType<UUID>> ITEM_OWNER = register("item_owner", uuidBuilder -> uuidBuilder.persistent(UUIDUtil.CODEC));

    public static final RegistrySupplier<DataComponentType<String>> CASE_BASE = register("case_base", base -> base.persistent(Codec.STRING));
    public static final RegistrySupplier<DataComponentType<String>> CASE_COVER = register("case_cover", cover -> cover.persistent(Codec.STRING));

    public static final RegistrySupplier<DataComponentType<League>> CURRENT_LEAGUE = register("current_league", league -> league.persistent(League.CODEC));
    public static final RegistrySupplier<DataComponentType<Emblem>> CURRENT_EMBLEM = register("current_emblem", emblem -> emblem.persistent(Emblem.CODEC));

    public static final RegistrySupplier<DataComponentType<Map<League, CompoundTag>>> LEAGUE_INVENTORY_CONTENTS = register("league_inventory_contents", builder -> builder.persistent(Codec.unboundedMap(League.CODEC, CompoundTag.CODEC)));

    public static final RegistrySupplier<DataComponentType<Map<Emblem, List<EmblemTargetItem>>>> EMBLEM_INVENTORY_CONTENTS = register("emblem_inventory_contents", builder -> builder.persistent(Codec.unboundedMap(Emblem.CODEC, EmblemTargetItem.CODEC.listOf())));

    public static final RegistrySupplier<DataComponentType<Boolean>> ITEM_GUI_OPEN = register("item_gui_open", builder -> builder.persistent(Codec.BOOL));

    private static <T> RegistrySupplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderUnaryOperator){
        return COMPONENTS.register(name, () -> builderUnaryOperator.apply(DataComponentType.builder()).build());
    }

    public static void registerDataComponents(){
        COMPONENTS.register();
    }

}
