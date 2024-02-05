package tfar.collectthebody.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import tfar.collectthebody.BodyPartMenu;

public class BodyPartScreen extends AbstractContainerScreen<BodyPartMenu> {

    public BodyPartScreen(BodyPartMenu $$0, Inventory $$1, Component $$2) {
        super($$0, $$1, $$2);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float v, int i, int i1) {

    }
}
