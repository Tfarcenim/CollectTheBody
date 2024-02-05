package tfar.collectthebody.mixin;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import tfar.collectthebody.BodyPartContainer;
import tfar.collectthebody.ducks.PlayerDuck;

@Mixin(Player.class)
public class PlayerMixin implements PlayerDuck {

    private BodyPartContainer bodyPartContainer = new BodyPartContainer((Player) (Object) this);

    @Override
    public BodyPartContainer getBodyPartContainer() {
        return bodyPartContainer;
    }
}
