package tfar.collectthebody.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.entity.player.Player;
import tfar.collectthebody.init.ModMenuTypes;

public class CollectTheBodyClient {

    public static Button bodyButton;

    public static void clientSetup() {
        MenuScreens.register(ModMenuTypes.BODY_PART,BodyPartScreen::new);
    }

    public static Player getLocalPlayer() {
        return Minecraft.getInstance().player;
    }

    public static void onRecipeBookButtonPressed(Button button) {
        bodyButton.x = button.x + 22;
    }
}
