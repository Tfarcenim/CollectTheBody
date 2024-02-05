package tfar.collectthebody;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.apache.commons.lang3.StringUtils;

public class BodyPartItem extends Item {
    final Type type;

    public BodyPartItem(Properties $$0, Type type) {
        super($$0);
        this.type = type;
    }

    public enum Type {
        TORSO,RIGHT_ARM,LEFT_ARM,RIGHT_LEG,LEFT_LEG
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
