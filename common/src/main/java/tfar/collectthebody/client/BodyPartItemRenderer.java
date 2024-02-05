package tfar.collectthebody.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import tfar.collectthebody.BodyPartItem;

public class BodyPartItemRenderer<T extends LivingEntity> extends BlockEntityWithoutLevelRenderer {

    private final PlayerModel<T> playerModel;
    private final BodyPartItem.Type type;

    public BodyPartItemRenderer(BlockEntityRenderDispatcher $$0, EntityModelSet entityModelSet, BodyPartItem.Type type) {
        super($$0, entityModelSet);
        playerModel = new PlayerModel<>(entityModelSet.bakeLayer(ModelLayers.PLAYER),false);
        this.type = type;
        playerModel.setAllVisible(false);
        setOnePartVisible();
    }


    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType $$1, PoseStack poseStack, MultiBufferSource multiBufferSource, int pPackedLight, int pPackedOverlay) {
            ResourceLocation texture = DefaultPlayerSkin.getDefaultSkin(Minecraft.getInstance().player.getUUID());
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(playerModel.renderType(texture));
            playerModel.renderToBuffer(poseStack, vertexConsumer, pPackedLight, pPackedOverlay, 1, 1, 1, 1);
    }



    public void setOnePartVisible() {
        switch (type) {
            case TORSO -> playerModel.body.visible = true;
            case RIGHT_ARM -> playerModel.rightArm.visible = true;
            case LEFT_ARM -> playerModel.leftArm.visible = true;
            case RIGHT_LEG -> playerModel.rightLeg.visible = true;
            case LEFT_LEG -> playerModel.leftLeg.visible = true;
        }
    }
}
