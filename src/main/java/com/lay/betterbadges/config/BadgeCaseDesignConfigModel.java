package com.lay.betterbadges.config;

import io.wispforest.owo.config.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@Config(name = "badge-case-design-config", wrapperName = "BadgeCaseDesignConfig")
public class BadgeCaseDesignConfigModel {

    public List<String> base = new ArrayList<>();
    public List<String> cover = new ArrayList<>();

    public BadgeCaseDesignConfigModel(){}

}
