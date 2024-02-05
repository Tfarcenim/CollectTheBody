package tfar.collectthebody;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BodyPartContainer extends SimpleContainer {

    //head (unremovable)
    //torso
    //right arm
    //left arm
    //right leg
    //left leg

    public BodyPartContainer(Player player) {
        super(6);
    }

    @Override
    public boolean canPlaceItem(int $$0, ItemStack $$1) {
        return super.canPlaceItem($$0, $$1);
    }


}
