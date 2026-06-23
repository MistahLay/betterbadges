package com.lay.betterbadges.common.render;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class ModelRegistrationHelper {

    public static void registerAll(){}

    @ExpectPlatform
    public static void registerAdditionalModel(ModelResourceLocation id) {
        throw new AssertionError();
    }

}
