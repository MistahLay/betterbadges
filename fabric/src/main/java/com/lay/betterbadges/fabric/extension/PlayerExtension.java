package com.lay.betterbadges.fabric.extension;

public interface PlayerExtension {

    default boolean canFly(){
        throw new AssertionError();
    }

}