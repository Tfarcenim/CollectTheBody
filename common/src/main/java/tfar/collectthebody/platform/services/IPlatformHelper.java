package tfar.collectthebody.platform.services;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import tfar.collectthebody.BodyPartItem;
import tfar.collectthebody.network.C2SModPacket;
import tfar.collectthebody.network.S2CModPacket;
import tfar.collectthebody.platform.ClientPlatformHelper;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }

    <T extends Registry<? extends F>,F> void superRegister(Class<?> clazz, T registry, Class<F> filter);

    BodyPartItem createBodyPart(Item.Properties properties, BodyPartItem.Type type);
    void sendToServer(C2SModPacket msg, ResourceLocation channel);
    void sendToClient(S2CModPacket msg, ResourceLocation channel, ServerPlayer player);

    void sendToTracking(S2CModPacket msg,ResourceLocation channel,ServerPlayer player);

    ClientPlatformHelper getClientHelper();
    void setClientHelper(ClientPlatformHelper helper);

}