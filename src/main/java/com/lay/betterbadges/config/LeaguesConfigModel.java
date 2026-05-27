package com.lay.betterbadges.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Expanded;
import io.wispforest.owo.config.annotation.RestartRequired;
import io.wispforest.owo.config.annotation.Sync;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Config(name = "league-config", wrapperName = "LeagueConfig")
public class LeaguesConfigModel {

    @Expanded
    @RestartRequired
    public List<League> leagues = new ArrayList<>();

    public LeaguesConfigModel(){

    }

    public static class League {
        public String id;
        public List<Badge> badges;

        public League(){

        }

        public League(String id, List<Badge> badges) {
            this.id = id;
            this.badges = badges;
        }

        public String id(){
            return this.id;
        }

        public List<Badge> badges(){
            return this.badges;
        }

    }

    public static class Badge {

        public Badge(){

        }

        public Badge(String id, int x, int y) {
            this.id = id;
            this.x  = x;
            this.y = y;
        }

        public String id;
        public int x;
        public int y;

        public String id(){
            return this.id;
        }

        public int x(){
            return this.x;
        }

        public int y(){
            return this.y;
        }
    }

}
