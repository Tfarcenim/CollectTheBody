package tfar.collectthebody;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class BodyPartContainer extends SimpleContainer {

    //head (unremovable)
    //torso
    //right arm
    //left arm
    //right leg
    //left leg

    public BodyPartContainer() {
        super(6);
    }

    @Override
    public void setChanged() {
        super.setChanged();
    }

    public boolean canRemoveItem(int slot) {
        return slot != 0;
    }

    @Override
    public ItemStack removeItem(int slot, int $$1) {
        return canRemoveItem(slot) ? super.removeItem(slot, $$1) : ItemStack.EMPTY;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (stack.getItem() instanceof BodyPartItem bodyPartItem) {
            switch (bodyPartItem.type) {
                case TORSO -> {
                    return slot == 1;
                }
                case RIGHT_ARM -> {
                    return slot ==2;
                }
                case LEFT_ARM -> {
                    return slot ==3;
                }
                case RIGHT_LEG -> {
                    return slot ==4;
                }
                case LEFT_LEG -> {
                    return slot ==5;
                }
            }
        }
        return false;
    }


}
