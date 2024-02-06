package tfar.collectthebody.platform;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class ForgeClientHelper implements ClientPlatformHelper {


    @Override
    public boolean fireRenderPlayerPreEvent(AbstractClientPlayer pEntity, PlayerRenderer playerRenderer, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        return net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(
                new net.minecraftforge.client.event.RenderPlayerEvent.Pre(pEntity, playerRenderer, pPartialTicks, pMatrixStack, pBuffer, pPackedLight));
    }

    @Override
    public void fireRenderPlayerPostEvent(AbstractClientPlayer pEntity, PlayerRenderer playerRenderer, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(
                new RenderPlayerEvent.Post(pEntity, playerRenderer, pPartialTicks, pMatrixStack, pBuffer, pPackedLight));
    }
}
