package tfar.collectthebody.client;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.apache.commons.lang3.StringUtils;
import tfar.collectthebody.BodyPartContainer;
import tfar.collectthebody.BodyPartItem;
import tfar.collectthebody.ducks.PlayerDuck;

import javax.annotation.Nullable;

public class CompositePlayerRenderer extends PlayerRenderer {

    protected PlayerModel<AbstractClientPlayer> torsoModel;
    protected PlayerModel<AbstractClientPlayer> rightArmModel;
    protected PlayerModel<AbstractClientPlayer> leftArmModel;
    protected PlayerModel<AbstractClientPlayer> rightLegModel;
    protected PlayerModel<AbstractClientPlayer> leftLegModel;

    public CompositePlayerRenderer(EntityRendererProvider.Context context, boolean slim) {
        super(context, slim);
        torsoModel = new PlayerModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), slim);
        rightArmModel = new PlayerModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), slim);
        leftArmModel = new PlayerModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), slim);
        rightLegModel = new PlayerModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), slim);
        leftLegModel = new PlayerModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), slim);

        torsoModel.setAllVisible(false);
        torsoModel.body.visible = true;
    }

    @Override
    public void render(AbstractClientPlayer pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        this.setModelProperties(pEntity);
      //  if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderPlayerEvent.Pre(pEntity, this, pPartialTicks, pMatrixStack, pBuffer, pPackedLight))) return;
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
      //  net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderPlayerEvent.Post(pEntity, this, pPartialTicks, pMatrixStack, pBuffer, pPackedLight));

        BodyPartContainer bodyPartContainer = ((PlayerDuck)pEntity).getBodyPartContainer();

        ItemStack torsoStack = bodyPartContainer.getItem(1);

        if (!torsoStack.isEmpty() && !itemMatchesPlayerName(pEntity,torsoStack)) {

            pMatrixStack.pushPose();
            this.torsoModel.attackTime = this.getAttackAnim(pEntity, pPartialTicks);

            boolean shouldSit = pEntity.isPassenger() && (pEntity.getVehicle() != null && pEntity.getVehicle() instanceof LivingEntity);//todo forge patch
            this.torsoModel.riding = shouldSit;
            this.torsoModel.young = pEntity.isBaby();
            float f = Mth.rotLerp(pPartialTicks, pEntity.yBodyRotO, pEntity.yBodyRot);
            float f1 = Mth.rotLerp(pPartialTicks, pEntity.yHeadRotO, pEntity.yHeadRot);
            float f2 = f1 - f;
            if (shouldSit && pEntity.getVehicle() instanceof LivingEntity livingentity) {
                f = Mth.rotLerp(pPartialTicks, livingentity.yBodyRotO, livingentity.yBodyRot);
                f2 = f1 - f;
                float f3 = Mth.wrapDegrees(f2);
                if (f3 < -85.0F) {
                    f3 = -85.0F;
                }

                if (f3 >= 85.0F) {
                    f3 = 85.0F;
                }

                f = f1 - f3;
                if (f3 * f3 > 2500.0F) {
                    f += f3 * 0.2F;
                }

                f2 = f1 - f;
            }

            float f6 = Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot());
            if (isEntityUpsideDown(pEntity)) {
                f6 *= -1.0F;
                f2 *= -1.0F;
            }

            if (pEntity.hasPose(Pose.SLEEPING)) {
                Direction direction = pEntity.getBedOrientation();
                if (direction != null) {
                    float f4 = pEntity.getEyeHeight(Pose.STANDING) - 0.1F;
                    pMatrixStack.translate((float) (-direction.getStepX()) * f4, 0.0D, (float) (-direction.getStepZ()) * f4);
                }
            }

            float f7 = this.getBob(pEntity, pPartialTicks);
            this.setupRotations(pEntity, pMatrixStack, f7, f, pPartialTicks);
            pMatrixStack.scale(-1.0F, -1.0F, 1.0F);
            this.scale(pEntity, pMatrixStack, pPartialTicks);
            pMatrixStack.translate(0.0D, -1.501F, 0.0D);
            float f8 = 0.0F;
            float f5 = 0.0F;
            if (!shouldSit && pEntity.isAlive()) {
                f8 = Mth.lerp(pPartialTicks, pEntity.animationSpeedOld, pEntity.animationSpeed);
                f5 = pEntity.animationPosition - pEntity.animationSpeed * (1.0F - pPartialTicks);
                if (pEntity.isBaby()) {
                    f5 *= 3.0F;
                }

                if (f8 > 1.0F) {
                    f8 = 1.0F;
                }
            }

            this.torsoModel.prepareMobModel(pEntity, f5, f8, pPartialTicks);
            this.torsoModel.setupAnim(pEntity, f5, f8, f7, f2, f6);
            Minecraft minecraft = Minecraft.getInstance();
            boolean flag = this.isBodyVisible(pEntity);
            boolean flag1 = !flag && !pEntity.isInvisibleTo(minecraft.player);
            boolean flag2 = minecraft.shouldEntityAppearGlowing(pEntity);
            RenderType rendertype = this.getPartRenderType(pEntity, flag, flag1, flag2, torsoStack);
            if (rendertype != null) {
                VertexConsumer vertexconsumer = pBuffer.getBuffer(rendertype);
                int i = getOverlayCoords(pEntity, this.getWhiteOverlayProgress(pEntity, pPartialTicks));
                this.torsoModel.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
            }
            pMatrixStack.popPose();
        }
    }

    @Nullable
    @Override
    protected RenderType getRenderType(AbstractClientPlayer $$0, boolean $$1, boolean $$2, boolean $$3) {
        ResourceLocation $$4 = this.getTextureLocation($$0);
        if ($$2) {
            return RenderType.itemEntityTranslucentCull($$4);
        } else if ($$1) {
            return this.model.renderType($$4);
        } else {
            return $$3 ? RenderType.outline($$4) : null;
        }
    }

    @Nullable
    protected RenderType getPartRenderType(AbstractClientPlayer $$0, boolean $$1, boolean $$2, boolean $$3, ItemStack stack) {
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

        return SkullBlockRenderer.getRenderType(SkullBlock.Types.PLAYER, gameprofile);

      /*  ResourceLocation $$4 = this.getPartTextureLocation($$0);
        if ($$2) {
            return RenderType.itemEntityTranslucentCull($$4);
        } else if ($$1) {
            return this.model.renderType($$4);
        } else {
            return $$3 ? RenderType.outline($$4) : null;
        }*/
    }

    public ResourceLocation getPartTextureLocation(AbstractClientPlayer $$0) {
        return $$0.getSkinTextureLocation();
    }

    @Override
    public void setModelProperties(AbstractClientPlayer abstractClientPlayer) {
        super.setModelProperties(abstractClientPlayer);
        PlayerModel<AbstractClientPlayer> originalModel = getModel();
        BodyPartContainer bodyPartContainer = ((PlayerDuck)abstractClientPlayer).getBodyPartContainer();

        ItemStack torso = bodyPartContainer.getItem(1);

        if (!itemMatchesPlayerName(abstractClientPlayer,torso)) {
            originalModel.body.visible = false;
            if (!torso.isEmpty()) {

            }
        }

        if (!itemMatchesPlayerName(abstractClientPlayer,bodyPartContainer.getItem(2))) {
            originalModel.rightArm.visible = false;
        }

        if (!itemMatchesPlayerName(abstractClientPlayer,bodyPartContainer.getItem(3))) {
            originalModel.leftArm.visible = false;
        }

        if (!itemMatchesPlayerName(abstractClientPlayer,bodyPartContainer.getItem(4))) {
            originalModel.rightLeg.visible = false;
        }
        if (!itemMatchesPlayerName(abstractClientPlayer,bodyPartContainer.getItem(5))) {
            originalModel.leftLeg.visible = false;
        }



    }

    protected boolean itemMatchesPlayerName(AbstractClientPlayer abstractClientPlayer,ItemStack stack) {
        if (!stack.hasTag())return false;

        CompoundTag compoundTag = stack.getTag().getCompound(SkullBlockEntity.TAG_SKULL_OWNER);
        return abstractClientPlayer.getGameProfile().getName().equals(compoundTag.getString("Name"));
    }

}
