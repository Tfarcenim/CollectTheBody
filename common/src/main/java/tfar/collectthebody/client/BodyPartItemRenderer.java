package tfar.collectthebody.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemStack;
import tfar.collectthebody.BodyPartItem;

public class BodyPartItemRenderer extends BlockEntityWithoutLevelRenderer {
    public BodyPartItemRenderer(BlockEntityRenderDispatcher $$0, EntityModelSet $$1, BodyPartItem.Type type) {
        super($$0, $$1);
    }

    @Override
    public void renderByItem(ItemStack $$0, ItemTransforms.TransformType $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {

    }
}
