package tfar.collectthebody.client;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;

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
    }

    @Override
    protected void setModelProperties(AbstractClientPlayer $$0) {
        super.setModelProperties($$0);
    }
}
