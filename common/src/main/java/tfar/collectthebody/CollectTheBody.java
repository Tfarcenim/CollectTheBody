package tfar.collectthebody;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfar.collectthebody.ducks.PlayerDuck;
import tfar.collectthebody.init.ModItems;
import tfar.collectthebody.init.ModMenuTypes;
import tfar.collectthebody.platform.Services;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class CollectTheBody {

    public static final String MOD_ID = "collectthebody";
    public static final String MOD_NAME = "Collect The Body";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {

      //  LOG.info("Hello from Common init on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
       // LOG.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND));

        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this example
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.
     //   if (Services.PLATFORM.isModLoaded("examplemod")) {

      //      LOG.info("Hello to examplemod");
      //  }
        Services.PLATFORM.superRegister(ModMenuTypes.class, Registry.MENU, MenuType.class);
        Services.PLATFORM.superRegister(ModItems.class, Registry.ITEM, Item.class);
    }

    public static void serverLogin(Player player) {
        ExistingPlayers existingPlayers = ExistingPlayers.getData(player.getServer());
        if (existingPlayers.isFirstJoin((ServerPlayer) player)) {
            handleFirstJoin((ServerPlayer) player);
            existingPlayers.addPlayer((ServerPlayer) player);
        }
    }

    public static void handleFirstJoin(ServerPlayer player) {
        BodyPartContainer bodyPartContainer = ((PlayerDuck)player).getBodyPartContainer();
        CompoundTag nameTag = new CompoundTag();
        nameTag.putString(SkullBlockEntity.TAG_SKULL_OWNER,player.getGameProfile().getName());

        ItemStack torso = new ItemStack(ModItems.PLAYER_TORSO);
        torso.setTag(nameTag);
        bodyPartContainer.setItem(1,torso);

        ItemStack rightArm = new ItemStack(ModItems.PLAYER_RIGHT_ARM);
        rightArm.setTag(nameTag);
        bodyPartContainer.setItem(2,rightArm);

        ItemStack leftArm = new ItemStack(ModItems.PLAYER_LEFT_ARM);
        leftArm.setTag(nameTag);
        bodyPartContainer.setItem(3,leftArm);

        ItemStack rightLeg = new ItemStack(ModItems.PLAYER_RIGHT_LEG);
        rightLeg.setTag(nameTag);
        bodyPartContainer.setItem(4,rightLeg);

        ItemStack leftLeg = new ItemStack(ModItems.PLAYER_LEFT_LEG);
        leftLeg.setTag(nameTag);
        bodyPartContainer.setItem(5,leftLeg);
    }
}