package com.lay.betterbadges.common;

import com.lay.betterbadges.common.event.ClientEventsListener;
import com.lay.betterbadges.common.misc.BetterBadgesKeyMappings;
import com.lay.betterbadges.common.render.ModelRegistrationHelper;

public final class BetterBadgesClient {

    public static void init() {
        ModelRegistrationHelper.registerAll();

        BetterBadgesKeyMappings.registerKeymaps();
        ClientEventsListener.listen();
    }

}
