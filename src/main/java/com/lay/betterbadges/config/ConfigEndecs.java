package com.lay.betterbadges.config;

import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.ReflectiveEndecBuilder;
import io.wispforest.endec.impl.StructEndecBuilder;

public class ConfigEndecs {

    public static final Endec<LeaguesConfigModel.Badge> BADGE_ENDEC = StructEndecBuilder.of(
            Endec.STRING.fieldOf("id", LeaguesConfigModel.Badge::id),
            Endec.INT.fieldOf("x", LeaguesConfigModel.Badge::x),
            Endec.INT.fieldOf("y", LeaguesConfigModel.Badge::y),
            LeaguesConfigModel.Badge::new
    );

    public static final Endec<LeaguesConfigModel.League> LEAGUE_ENDEC = StructEndecBuilder.of(
            Endec.STRING.fieldOf("id", LeaguesConfigModel.League::id),
            BADGE_ENDEC.listOf().fieldOf("badges", LeaguesConfigModel.League::badges),
            LeaguesConfigModel.League::new
    );

    public static final Endec<EmblemConfigModel.SlotPosition> SLOT_POSITION_ENDEC = StructEndecBuilder.of(
            Endec.INT.fieldOf("x", EmblemConfigModel.SlotPosition::x),
            Endec.INT.fieldOf("y", EmblemConfigModel.SlotPosition::y),
            EmblemConfigModel.SlotPosition::new
    );

    public static final Endec<EmblemConfigModel.Emblem> EMBLEM_ENDEC = StructEndecBuilder.of(
            Endec.STRING.fieldOf("id", EmblemConfigModel.Emblem::id),
            SLOT_POSITION_ENDEC.listOf().fieldOf("player", EmblemConfigModel.Emblem::player),
            SLOT_POSITION_ENDEC.listOf().fieldOf("spawning", EmblemConfigModel.Emblem::spawning),
            SLOT_POSITION_ENDEC.listOf().fieldOf("catching", EmblemConfigModel.Emblem::catching),
            EmblemConfigModel.Emblem::new
    );

    public static void registerEndecs(){}

}
