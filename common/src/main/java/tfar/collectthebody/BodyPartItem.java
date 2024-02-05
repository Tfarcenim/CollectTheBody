package tfar.collectthebody;

import net.minecraft.world.item.Item;

public class BodyPartItem extends Item {
    final Type type;

    public BodyPartItem(Properties $$0, Type type) {
        super($$0);
        this.type = type;
    }

    public enum Type {
        TORSO,RIGHT_ARM,LEFT_ARM,RIGHT_LEG,LEFT_LEG
    }
}
