package com.lay.betterbadges.common.api.attribute;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class BooleanAttribute extends Attribute {

    public static double FALSE = 0.0;
    public static double TRUE = 1.0;

    public BooleanAttribute(String string, boolean defaulted) {
        super(string, BooleanAttribute.toDouble(defaulted));
    }

    public boolean get(){
        return toBoolean(this.getDefaultValue());
    }

    @Override
    public double sanitizeValue(double d) {
        return Double.isNaN(d) ? FALSE : Mth.clamp(d, FALSE, TRUE);
    }

    public static boolean toBoolean(double value){
        return value > FALSE;
    }

    public static double toDouble(boolean value){
        return value ? TRUE : FALSE;
    }

}
