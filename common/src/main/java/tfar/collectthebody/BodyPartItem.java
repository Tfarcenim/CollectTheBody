package tfar.collectthebody;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.apache.commons.lang3.StringUtils;

public class BodyPartItem extends Item {
    public final Type type;

    public BodyPartItem(Properties $$0, Type type) {
        super($$0);
        this.type = type;
    }

    public enum Type {
        TORSO,RIGHT_ARM,LEFT_ARM,RIGHT_LEG,LEFT_LEG;
        public int slot() {
            return ordinal() + 1;
        }

        public static final Type[] VALUES = values();

    }

    @Override
    public Component getName(ItemStack $$0) {
        if ($$0.hasTag()) {
            String $$1 = null;
            CompoundTag $$2 = $$0.getTag();
            if ($$2.contains("SkullOwner", 8)) {
                $$1 = $$2.getString("SkullOwner");
            } else if ($$2.contains("SkullOwner", 10)) {
                CompoundTag $$3 = $$2.getCompound("SkullOwner");
                if ($$3.contains("Name", 8)) {
                    $$1 = $$3.getString("Name");
                }
            }

            if ($$1 != null) {
                return Component.translatable(this.getDescriptionId() + ".named", $$1);
            }
        }

        return super.getName($$0);
    }


    @Override
    public void verifyTagAfterLoad(CompoundTag $$0) {
        super.verifyTagAfterLoad($$0);
        if ($$0.contains(SkullBlockEntity.TAG_SKULL_OWNER, Tag.TAG_STRING) && !StringUtils.isBlank($$0.getString(SkullBlockEntity.TAG_SKULL_OWNER))) {
            GameProfile $$1 = new GameProfile(null, $$0.getString(SkullBlockEntity.TAG_SKULL_OWNER));
            SkullBlockEntity.updateGameprofile($$1, $$1x -> $$0.put(SkullBlockEntity.TAG_SKULL_OWNER, NbtUtils.writeGameProfile(new CompoundTag(), $$1x)));
        }
    }
}
