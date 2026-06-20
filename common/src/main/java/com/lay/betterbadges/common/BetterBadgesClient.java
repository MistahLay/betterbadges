package com.lay.betterbadges.common;

import com.lay.betterbadges.common.event.ClientEventsListener;
import com.lay.betterbadges.common.misc.BetterBadgesKeyMappings;

public final class BetterBadgesClient {

    public static void init() {
        BetterBadgesKeyMappings.registerKeymaps();
        ClientEventsListener.listen();
    }

}
