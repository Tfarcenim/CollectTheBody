package tfar.collectthebody.client;

import net.minecraft.client.gui.screens.MenuScreens;
import tfar.collectthebody.init.ModMenuTypes;

public class CollectTheBodyClient {

    public static void clientSetup() {
        MenuScreens.register(ModMenuTypes.BODY_PART,BodyPartScreen::new);
    }
}
