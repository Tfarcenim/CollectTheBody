package tfar.collectthebody.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import tfar.collectthebody.BodyPartMenu;
import tfar.collectthebody.CollectTheBody;

public class BodyPartScreen extends AbstractContainerScreen<BodyPartMenu> {
    public static final ResourceLocation INVENTORY_LOCATION = new ResourceLocation(CollectTheBody.MOD_ID,"textures/gui/body_parts.png");


    /** The old x position of the mouse pointer */
    private float xMouse;
    /** The old y position of the mouse pointer */
    private float yMouse;

    public BodyPartScreen(BodyPartMenu $$0, Inventory $$1, Component $$2) {
        super($$0, $$1, $$2);
        titleLabelX +=90;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        renderTooltip(poseStack,mouseX,mouseY);
        this.xMouse = mouseX;
        this.yMouse = mouseY;
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pX, int pY) {
        this.font.draw(pPoseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, INVENTORY_LOCATION);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(poseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        InventoryScreen.renderEntityInInventory(i + 51, j + 75, 30, (float)(i + 51) - this.xMouse, (float)(j + 75 - 50) - this.yMouse, this.minecraft.player);
    }
}
