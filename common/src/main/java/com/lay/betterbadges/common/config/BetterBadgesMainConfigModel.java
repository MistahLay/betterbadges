package com.lay.betterbadges.common.config;

import io.wispforest.owo.config.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@Config(name = "betterbadges", wrapperName = "BetterBadgesMainConfig")
public class BetterBadgesMainConfigModel {

    public List<String> blacklistedPokaballs = new ArrayList<>();

}
