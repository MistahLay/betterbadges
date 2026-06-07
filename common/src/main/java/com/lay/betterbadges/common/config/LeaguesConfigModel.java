package com.lay.betterbadges.common.config;

import io.wispforest.owo.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Config(name = "betterbadges/league-config", wrapperName = "LeagueConfig")
public class LeaguesConfigModel {

    @Expanded
    @RestartRequired
    public List<LeagueConfig> leagues = new ArrayList<>();

    public LeaguesConfigModel(){ }

    public static class LeagueConfig {
        public String id;
        public List<BadgeConfig> badges;

        // Empty thingy BS
        public LeagueConfig(){ }

        public LeagueConfig(String id, List<BadgeConfig> badges) {
            this.id = id;
            this.badges = badges;
        }

    }

    public static class BadgeConfig {

        public String id;
        public int x;
        public int y;

        public BadgeAttributeConfig adventure;
        public BadgeAttributeConfig catching;
        public BadgeAttributeConfig spawning;

        public BadgeConfig(){ }

        public BadgeConfig(
            String id,
            int x,
            int y,
            BadgeAttributeConfig adventure,
            BadgeAttributeConfig catching,
            BadgeAttributeConfig spawning
        ) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.adventure = adventure;
            this.catching = catching;
            this.spawning = spawning;
        }
    }

    public static class BadgeAttributeConfig {
        public String id;

        @RegexConstraint("^(add_value|add_multiplied_base|add_multiplied_total)$")
        public String operation = "add_value";

        public double value = 0;

        public BadgeAttributeConfig () { }

        public BadgeAttributeConfig(String id, String operation, double value) {
            this.id = id;
            this.operation = operation;
            this.value = value;
        }
    }

}
