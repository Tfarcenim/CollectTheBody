package tfar.collectthebody;

import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import tfar.collectthebody.init.ModMenuTypes;

public class BodyPartMenu extends AbstractContainerMenu {

    public BodyPartMenu(int id,Inventory inventory) {
        this(id,inventory,new BodyPartContainer());
    }

    public BodyPartMenu(int id, Inventory inventory,BodyPartContainer bodyPartContainer) {
        super(ModMenuTypes.BODY_PART, id);

        //index x y
        int w = 3;
        int h = 2;
        for (int y = 0; y < h;y++) {
            for (int x = 0; x < w;x++) {
                addSlot(new UpdateSlot(bodyPartContainer,x + w * y,98 + x * 18,18 + y * 18));
            }
        }

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(inventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inventory, l, 8 + l * 18, 142));
        }
    }

    public static class UpdateSlot extends Slot {

        public UpdateSlot(Container $$0, int index, int $$2, int $$3) {
            super($$0, index, $$2, $$3);
        }

        @Override
        public boolean mayPlace(ItemStack $$0) {
            return container.canPlaceItem(this.getContainerSlot(),$$0);
        }
    }

    //note: slots 0 - 5 are the body container
    //6 - 32 are player slots

    static final int BODY_CONTAINER_START = 0;
    static final int PLAYER_START = 6;

    static final int HOTBAR_START = PLAYER_START + 27;

    static final int HOTBAR_END = HOTBAR_START + 9;

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        if (pIndex == 0) return ItemStack.EMPTY;
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(itemstack);

            if (itemstack.getItem() instanceof BodyPartItem bodyPartItem && !slots.get(bodyPartItem.type.slot()).hasItem()) {
                int s = bodyPartItem.type.slot();
                if (!this.moveItemStackTo(itemstack1, s, s + 1, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (pIndex >= BODY_CONTAINER_START && pIndex < PLAYER_START) {
                if (!this.moveItemStackTo(itemstack1, PLAYER_START, HOTBAR_END, false)) {
                    return ItemStack.EMPTY;
                }
            } /*else if (equipmentslot.getType() == EquipmentSlot.Type.ARMOR && !this.slots.get(8 - equipmentslot.getIndex()).hasItem()) {
                int i = 8 - equipmentslot.getIndex();
                if (!this.moveItemStackTo(itemstack1, i, i + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentslot == EquipmentSlot.OFFHAND && !this.slots.get(HOTBAR_END).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, HOTBAR_END, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } */else if (pIndex >= PLAYER_START && pIndex < HOTBAR_START) {
                if (!this.moveItemStackTo(itemstack1, HOTBAR_START, HOTBAR_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex >= HOTBAR_START && pIndex < HOTBAR_END) {
                if (!this.moveItemStackTo(itemstack1, PLAYER_START, HOTBAR_START, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, PLAYER_START, HOTBAR_END, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
            if (pIndex == 0) {
                pPlayer.drop(itemstack1, false);
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
