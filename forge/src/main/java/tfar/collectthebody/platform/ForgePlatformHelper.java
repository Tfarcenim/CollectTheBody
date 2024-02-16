package tfar.collectthebody.platform;

import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.apache.commons.lang3.tuple.Pair;
import tfar.collectthebody.*;
import tfar.collectthebody.network.C2SModPacket;
import tfar.collectthebody.network.PacketHandlerForge;
import tfar.collectthebody.network.S2CModPacket;
import tfar.collectthebody.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public <T extends Registry<? extends F>,F> void superRegister(Class<?> clazz, T registry, Class<F> filter) {
        List<Pair<ResourceLocation, Supplier<?>>> list = CollectTheBodyForge.registerLater.computeIfAbsent(registry, k -> new ArrayList<>());
        for (Field field : clazz.getFields()) {
            MappedRegistry<? extends F> forgeRegistry = (MappedRegistry<? extends F>) registry;
            forgeRegistry.unfreeze();
            try {
                Object o = field.get(null);
                if (filter.isInstance(o)) {
                    list.add(Pair.of(new ResourceLocation(CollectTheBody.MOD_ID,field.getName().toLowerCase(Locale.ROOT)),() -> o));
                }
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
    }

    @Override
    public BodyPartItem createBodyPart(Item.Properties properties, BodyPartItem.Type type) {
        return new BodyPartItemForge(properties,type);
    }

    @Override
    public void sendToServer(C2SModPacket msg, ResourceLocation channel) {
        PacketHandlerForge.sendToServer(msg);
    }

    @Override
    public void sendToClient(S2CModPacket msg, ResourceLocation channel, ServerPlayer player) {
        PacketHandlerForge.sendToClient(msg,player);
    }

    @Override
    public void sendToTracking(S2CModPacket msg, ResourceLocation channel, ServerPlayer player) {
        PacketHandlerForge.sendToTracking(msg,player);
    }

    private ClientPlatformHelper clientPlatformHelper;

    @Override
    public ClientPlatformHelper getClientHelper() {
        return clientPlatformHelper;
    }

    @Override
    public void setClientHelper(ClientPlatformHelper helper) {
        clientPlatformHelper = helper;
    }

    @Override
    public boolean hasIdentity(Player player) {
        return isModLoaded("identity") && IdentityHelper.hasAnyIdentity(player);
    }
}