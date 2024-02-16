package tfar.collectthebody;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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

    @Override
    public void fromTag(ListTag $$0) {
        int $$2;
        for($$2 = 0; $$2 < this.getContainerSize(); ++$$2) {
            this.setItem($$2, ItemStack.EMPTY);
        }

        for($$2 = 0; $$2 < $$0.size(); ++$$2) {
            CompoundTag $$3 = $$0.getCompound($$2);
            int $$4 = $$3.getByte("Slot") & 255;
            if ($$4 >= 0 && $$4 < this.getContainerSize()) {
                this.setItem($$4, ItemStack.of($$3));
            }
        }

    }

    @Override
    public ListTag createTag() {
        ListTag $$0 = new ListTag();

        for(int $$1 = 0; $$1 < this.getContainerSize(); ++$$1) {
            ItemStack $$2 = this.getItem($$1);
            if (!$$2.isEmpty()) {
                CompoundTag $$3 = new CompoundTag();
                $$3.putByte("Slot", (byte)$$1);
                $$2.save($$3);
                $$0.add($$3);
            }
        }

        return $$0;
    }

    protected boolean dirty = true;
    public boolean isDirty() {
        return dirty;
    }
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }


}
