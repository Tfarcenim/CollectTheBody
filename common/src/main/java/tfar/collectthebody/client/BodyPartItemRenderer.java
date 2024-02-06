package tfar.collectthebody.client;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.apache.commons.lang3.StringUtils;
import tfar.collectthebody.BodyPartItem;

public class BodyPartItemRenderer<T extends LivingEntity> extends BlockEntityWithoutLevelRenderer {

    private final PlayerModel<T> playerModel;
    private final BodyPartItem.Type type;

    public BodyPartItemRenderer(BlockEntityRenderDispatcher $$0, EntityModelSet entityModelSet, BodyPartItem.Type type) {
        super($$0, entityModelSet);
        playerModel = new PlayerModel<>(entityModelSet.bakeLayer(ModelLayers.PLAYER), false);
        this.type = type;
        playerModel.setAllVisible(false);
        setOnePartVisible();
    }


    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType $$1, PoseStack poseStack, MultiBufferSource multiBufferSource, int pPackedLight, int pPackedOverlay) {

        CompoundTag tag = stack.getTag();
        GameProfile gameprofile = null;
        if (tag != null) {
            if (tag.contains("SkullOwner", 10)) {
                gameprofile = NbtUtils.readGameProfile(tag.getCompound("SkullOwner"));
            } else if (tag.contains("SkullOwner", 8) && !StringUtils.isBlank(tag.getString("SkullOwner"))) {
                gameprofile = new GameProfile(null, tag.getString("SkullOwner"));
                tag.remove("SkullOwner");
                SkullBlockEntity.updateGameprofile(gameprofile, (p_172560_) -> {
                    tag.put("SkullOwner", NbtUtils.writeGameProfile(new CompoundTag(), p_172560_));
                });
            }
        }

        RenderType renderType = SkullBlockRenderer.getRenderType(SkullBlock.Types.PLAYER, gameprofile);

        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(renderType);
        poseStack.translate(1,-1.25,0);

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
