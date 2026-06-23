package com.lay.betterbadges.common.render.neoforge;

import net.minecraft.client.resources.model.ModelResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ModelRegistrationHelperImpl {

    public static List<ModelResourceLocation> toRegister = new ArrayList<>();

    public static void registerAdditionalModel(ModelResourceLocation id) {

        toRegister.add(id);

    }

}
