package tfar.collectthebody.init;

import net.minecraft.world.inventory.MenuType;
import tfar.collectthebody.BodyPartMenu;

public class ModMenuTypes {

    public static final MenuType<BodyPartMenu> BODY_PART = new MenuType<>(BodyPartMenu::new);

}
