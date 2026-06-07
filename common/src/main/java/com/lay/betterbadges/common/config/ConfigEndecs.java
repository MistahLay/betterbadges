package com.lay.betterbadges.common.config;

import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;

public class ConfigEndecs {

    // League

    public static final Endec<LeaguesConfigModel.BadgeAttributeConfig> BADGE_ATTRIBUTE_ENDEC = StructEndecBuilder.of(
            Endec.STRING.fieldOf("id", a -> a.id),
            Endec.STRING.fieldOf("operation", a -> a.operation),
            Endec.DOUBLE.fieldOf("value", a -> a.value),
            LeaguesConfigModel.BadgeAttributeConfig::new
    );

    public static final Endec<LeaguesConfigModel.BadgeConfig> BADGE_ENDEC = StructEndecBuilder.of(
            Endec.STRING.fieldOf("id", a -> a.id),
            Endec.INT.fieldOf("x", a -> a.x),
            Endec.INT.fieldOf("y", a -> a.y),
            BADGE_ATTRIBUTE_ENDEC.fieldOf("adventure", a -> a.adventure),
            BADGE_ATTRIBUTE_ENDEC.fieldOf("catching", a -> a.catching),
            BADGE_ATTRIBUTE_ENDEC.fieldOf("spawning", a -> a.spawning),
            LeaguesConfigModel.BadgeConfig::new
    );

    public static final Endec<LeaguesConfigModel.LeagueConfig> LEAGUE_ENDEC = StructEndecBuilder.of(
            Endec.STRING.fieldOf("id", a -> a.id),
            BADGE_ENDEC.listOf().fieldOf("badges", a -> a.badges),
            LeaguesConfigModel.LeagueConfig::new
    );

    // Emblems

    public static final Endec<EmblemConfigModel.BoostConfig> BOOST_ENDEC = StructEndecBuilder.of(
            Endec.INT.fieldOf("x", a -> a.x),
            Endec.INT.fieldOf("y", a -> a.y),
            EmblemConfigModel.BoostConfig::new
    );

    public static final Endec<EmblemConfigModel.Emblem> EMBLEM_ENDEC = StructEndecBuilder.of(
            Endec.STRING.fieldOf("id", a -> a.id),
            Endec.STRING.fieldOf("item", a -> a.item),
            BOOST_ENDEC.listOf().fieldOf("adventure", a -> a.adventure),
            BOOST_ENDEC.listOf().fieldOf("spawning", a -> a.spawning),
            BOOST_ENDEC.listOf().fieldOf("catching", a -> a.catching),
            EmblemConfigModel.Emblem::new
    );

    public static void registerEndecs(){}

}
