package tfar.collectthebody.client;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import tfar.collectthebody.network.C2SButtonPacket;

import java.util.List;

public class CollectTheBodyClientForge {


    public static void clientSetup() {
        CollectTheBodyClient.clientSetup();
        MinecraftForge.EVENT_BUS.addListener(CollectTheBodyClientForge::addButtonToPlayer);
    }

    private static void addButtonToPlayer(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        if (screen instanceof InventoryScreen inventoryScreen) {
            int leftPos = inventoryScreen.getGuiLeft();
            int topPos = inventoryScreen.getGuiTop();
            Button button = new Button(leftPos+76,topPos+4,20,18, Component.empty(), button1 -> C2SButtonPacket.send(),(pButton, pPoseStack, pMouseX, pMouseY) -> {
                inventoryScreen.renderComponentTooltip(pPoseStack, List.of(Component.literal("Open Body Part Menu")),pMouseX,pMouseY);
            });
            event.addListener(button);
        }
    }
}
