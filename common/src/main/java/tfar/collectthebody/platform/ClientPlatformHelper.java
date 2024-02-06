package tfar.collectthebody.platform;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;

public interface ClientPlatformHelper {

    boolean fireRenderPlayerPreEvent(AbstractClientPlayer pEntity, PlayerRenderer playerRenderer, float pPartialTicks,
                                     PoseStack pMatrixStack, MultiBufferSource pBuffer,int pPackedLight);

    void fireRenderPlayerPostEvent(AbstractClientPlayer pEntity, PlayerRenderer playerRenderer, float pPartialTicks,
                                     PoseStack pMatrixStack, MultiBufferSource pBuffer,int pPackedLight);

}
