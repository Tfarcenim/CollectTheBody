package tfar.collectthebody;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BodyPartContainer extends SimpleContainer {

    //head (unremovable)
    //torso
    //right arm
    //left arm
    //right leg
    //left leg

    public BodyPartContainer(Player player) {
        super(6);
        if (!player.level.isClientSide) {
            setItem(0,new ItemStack(Items.PLAYER_HEAD));
        }
    }

    public boolean canRemoveItem(int slot) {
        return slot != 0;
    }

    @Override
    public ItemStack removeItem(int slot, int $$1) {
        return canRemoveItem(slot) ? super.removeItem(slot, $$1) : ItemStack.EMPTY;
    }

    @Override
    public boolean canPlaceItem(int $$0, ItemStack $$1) {
        return super.canPlaceItem($$0, $$1);
    }


}
