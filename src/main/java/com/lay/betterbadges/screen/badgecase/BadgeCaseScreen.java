package com.lay.betterbadges.screen.badgecase;

import io.wispforest.owo.ui.base.BaseOwoHandledScreen;
import io.wispforest.owo.ui.core.OwoUIAdapter;
import io.wispforest.owo.ui.core.ParentComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class BadgeCaseScreen extends BaseOwoHandledScreen {
    protected BadgeCaseScreen(BadgeCaseScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected @NotNull OwoUIAdapter createAdapter() {
        return null;
    }

    @Override
    protected void build(ParentComponent rootComponent) {

    }
}
