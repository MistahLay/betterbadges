package com.lay.betterbadges.common.config;

import io.wispforest.owo.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Config(name = "betterbadges/emblem-config", wrapperName = "EmblemConfig")
public class EmblemConfigModel{

    @Expanded
    @RestartRequired
    public List<Emblem> emblems = new ArrayList<>();

    public EmblemConfigModel(){ }

    public static class Emblem {
        public String id;
        public String item;

        @Expanded
        public List<BoostConfig> adventure;

        @Expanded
        public List<BoostConfig> spawning;

        @Expanded
        public List<BoostConfig> catching;

        public Emblem(){

        }

        public Emblem(String id, String item, List<BoostConfig> adventure, List<BoostConfig> spawning, List<BoostConfig> catching){
            this.id = id;
            this.item = item;
            this.adventure = adventure;
            this.spawning = spawning;
            this.catching = catching;
        }
    }

    public static class BoostConfig {
        public int x;
        public int y;

        public BoostConfig(){ }

        public BoostConfig(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
