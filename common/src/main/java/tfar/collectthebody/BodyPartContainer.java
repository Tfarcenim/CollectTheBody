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
    }

    public ItemStack getPart(BodyPartItem.Type type) {
        return getItem(type.slot());
    }

    public boolean canRemoveItem(int slot) {
        return slot != 0;
    }

    @Override
    public ItemStack removeItem(int slot, int $$1) {
        return canRemoveItem(slot) ? super.removeItem(slot, $$1) : ItemStack.EMPTY;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        dirty = true;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (slot == 0) return stack.getItem() == Items.PLAYER_HEAD;

        return stack.getItem() instanceof BodyPartItem bodyPartItem && bodyPartItem.type.slot() == slot;
    }

    protected boolean dirty = true;
    public boolean isDirty() {
        return dirty;
    }
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }


}
