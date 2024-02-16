package tfar.collectthebody.client;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import tfar.collectthebody.network.C2SButtonPacket;
import tfar.collectthebody.platform.ForgeClientHelper;
import tfar.collectthebody.platform.Services;

import java.util.List;

public class CollectTheBodyClientForge {


    public static void clientSetup() {
        CollectTheBodyClient.clientSetup();
        MinecraftForge.EVENT_BUS.addListener(CollectTheBodyClientForge::addButtonToPlayer);
        Services.PLATFORM.setClientHelper(new ForgeClientHelper());
    }

    private static final ResourceLocation ACCESSIBILITY_TEXTURE = new ResourceLocation("textures/gui/accessibility.png");

    private static void addButtonToPlayer(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        if (screen instanceof InventoryScreen inventoryScreen) {
            int leftPos = inventoryScreen.getGuiLeft();
            int topPos = inventoryScreen.getGuiTop();
            CollectTheBodyClient.bodyButton = new ImageButton(leftPos+126,topPos+61,
                    20,20,0, 0, 20,
                    ACCESSIBILITY_TEXTURE,  32, 64,
                    button1 -> C2SButtonPacket.send(),(pButton, pPoseStack, pMouseX, pMouseY) -> {
                inventoryScreen.renderComponentTooltip(pPoseStack, List.of(Component.literal("Open Body Part Menu")),pMouseX,pMouseY);
            },Component.empty());
            event.addListener(CollectTheBodyClient.bodyButton);
        }
    }
}
