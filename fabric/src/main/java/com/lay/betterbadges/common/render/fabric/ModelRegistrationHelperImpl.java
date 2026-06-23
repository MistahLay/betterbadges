package com.lay.betterbadges.common.render.fabric;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class ModelRegistrationHelperImpl {

    public static void registerAdditionalModel(ModelResourceLocation id) {
        ModelLoadingPlugin.register(pluginContext -> {
            pluginContext.addModels(id.id());
        });
    }

}
