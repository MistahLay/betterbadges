package com.lay.betterbadges.common.api.attribute;

import net.minecraft.world.entity.ai.attributes.Attribute;

public class BooleanAttribute extends Attribute {

    public static double DISABLED = 0.0;
    public static double ENABLED = 1.0;

    public BooleanAttribute(String string, boolean result) {
        super(string, BooleanAttribute.toDouble(result));
    }

    public boolean getDefaultBoolean(){
        return toBoolean(this.getDefaultValue());
    }

    public static boolean toBoolean(double value){
        return value > DISABLED;
    }

    public static double toDouble(boolean value){
        return value ? ENABLED : DISABLED;
    }

}
