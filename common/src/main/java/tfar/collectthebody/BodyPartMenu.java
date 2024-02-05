package tfar.collectthebody;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import tfar.collectthebody.init.ModMenuTypes;

public class BodyPartMenu extends AbstractContainerMenu {

    public BodyPartMenu(int id,Inventory inventory) {
        this(id,inventory,new BodyPartContainer(inventory.player));
    }

    public BodyPartMenu(int id, Inventory inventory,BodyPartContainer bodyPartContainer) {
        super(ModMenuTypes.BODY_PART, id);

        //index x y
        int w = 3;
        int h = 2;
        for (int y = 0; y < h;y++) {
            for (int x = 0; x < w;x++) {
                addSlot(new Slot(bodyPartContainer,x + h * y,98 + x * 18,18 + y * 18));
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

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
