package com.lay.betterbadges.fabric.mixin;

import com.google.gson.JsonElement;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

// Why doesn't itemModeGenerators make these stuff public
@Mixin(ItemModelGenerators.class)
public interface ItemModelGeneratorsWidener {

    @Accessor("output")
    BiConsumer<ResourceLocation, Supplier<JsonElement>> betterbadges$output();

    @Invoker("generateFlatItem")
    void betterbadges$generateFlatItem(Item item, ModelTemplate modelTemplate);

}
