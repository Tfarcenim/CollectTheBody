package tfar.collectthebody.client;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.item.ItemStack;
import tfar.collectthebody.BodyPartContainer;
import tfar.collectthebody.ducks.PlayerDuck;

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
    public void setModelProperties(AbstractClientPlayer abstractClientPlayer) {
        super.setModelProperties(abstractClientPlayer);
        PlayerModel<AbstractClientPlayer> originalModel = getModel();
        BodyPartContainer bodyPartContainer = ((PlayerDuck)abstractClientPlayer).getBodyPartContainer();
        if (!itemMatchesPlayerName(abstractClientPlayer,bodyPartContainer.getItem(1))) {
            originalModel.body.visible = false;
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
        if (stack.isEmpty())return false;
        return true;
    }

}
