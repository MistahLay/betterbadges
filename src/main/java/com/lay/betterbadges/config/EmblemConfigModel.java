package com.lay.betterbadges.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Expanded;
import io.wispforest.owo.config.annotation.RestartRequired;
import io.wispforest.owo.config.annotation.Sync;

import java.util.ArrayList;
import java.util.List;

@Config(name = "emblem-config", wrapperName = "EmblemConfig")
public class EmblemConfigModel{

    @Expanded
    @RestartRequired
    public List<Emblem> emblems = new ArrayList<>();

    public EmblemConfigModel(){

    }

    public static class Emblem {
        public String id;

        @Expanded
        public List<SlotPosition> player;

        @Expanded
        public List<SlotPosition> spawning;

        @Expanded
        public List<SlotPosition> catching;

        public Emblem(){

        }

        public Emblem(String id, List<SlotPosition> player, List<SlotPosition> spawning, List<SlotPosition> catching){
            this.id = id;
            this.player = player;
            this.spawning = spawning;
            this.catching = catching;
        }

        public List<SlotPosition> player(){
            return player;
        }

        public List<SlotPosition> spawning(){
            return spawning;
        }

        public List<SlotPosition> catching(){
            return catching;
        }

        public String id(){
            return this.id;
        }
    }

    public static class SlotPosition {
        public int x;
        public int y;

        public SlotPosition(){

        }

        public SlotPosition(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int x(){
            return x;
        }

        public int y(){
            return y;
        }
    }

}
