package com.lay.betterbadges.common.config;

import io.wispforest.owo.config.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@Config(name = "betterbadges", wrapperName = "BetterBadgesConfig")
public class BetterBadgesConfigModel {

    public List<String> blacklistedPokaballs = new ArrayList<>();

}
