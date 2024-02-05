package tfar.collectthebody.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import tfar.collectthebody.BodyPartItem;
import tfar.collectthebody.platform.Services;

public class ModItems {

    public static final Item PLAYER_TORSO = Services.PLATFORM.createBodyPart(new Item.Properties().tab(CreativeModeTab.TAB_MISC), BodyPartItem.Type.TORSO);
    public static final Item PLAYER_RIGHT_ARM = Services.PLATFORM.createBodyPart(new Item.Properties().tab(CreativeModeTab.TAB_MISC), BodyPartItem.Type.RIGHT_ARM);
    public static final Item PLAYER_LEFT_ARM = Services.PLATFORM.createBodyPart(new Item.Properties().tab(CreativeModeTab.TAB_MISC), BodyPartItem.Type.LEFT_ARM);
    public static final Item PLAYER_RIGHT_LEG = Services.PLATFORM.createBodyPart(new Item.Properties().tab(CreativeModeTab.TAB_MISC), BodyPartItem.Type.RIGHT_LEG);
    public static final Item PLAYER_LEFT_LEG = Services.PLATFORM.createBodyPart(new Item.Properties().tab(CreativeModeTab.TAB_MISC), BodyPartItem.Type.LEFT_LEG);
}
