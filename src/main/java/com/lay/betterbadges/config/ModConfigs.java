package com.lay.betterbadges.config;


import javax.naming.ConfigurationException;

public class ModConfigs {

    public static final com.lay.betterbadges.config.LeagueConfig LEAGUE_CONFIG = com.lay.betterbadges.config.LeagueConfig.createAndLoad();
    public static final com.lay.betterbadges.config.EmblemConfig EMBLEM_CONFIG = com.lay.betterbadges.config.EmblemConfig.createAndLoad();

    public static final com.lay.betterbadges.config.BadgeCaseDesignConfig DESIGN_CONFIG = com.lay.betterbadges.config.BadgeCaseDesignConfig.createAndLoad();

    public static void initializeConfigs(){

    }

    // Idk if this is the right to do it, but it works :P
    public static void throwException(String message){
        try {
            throw new ConfigurationException(message);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
