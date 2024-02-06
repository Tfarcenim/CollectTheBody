package tfar.collectthebody.network;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import tfar.collectthebody.BodyPartContainer;
import tfar.collectthebody.client.CollectTheBodyClient;
import tfar.collectthebody.ducks.PlayerDuck;
import tfar.collectthebody.platform.Services;

import java.util.List;
import java.util.UUID;


public class S2CBodyPartsPacket implements S2CModPacket {

    private final NonNullList<ItemStack> stacks;
    private final UUID otherPlayer;

    public S2CBodyPartsPacket(NonNullList<ItemStack> stacks, UUID otherPlayer) {
        this.stacks = stacks;
        this.otherPlayer = otherPlayer;
    }

    public static void send(ServerPlayer other) {

        BodyPartContainer bodyPartContainer = ((PlayerDuck)other).getBodyPartContainer();
        if (bodyPartContainer.isDirty()) {
            NonNullList<ItemStack> stacks = ((PlayerDuck) other).getBodyPartContainer().items;
            List<ServerPlayer> allPlayers = other.server.getPlayerList().getPlayers();
            for (ServerPlayer player : allPlayers) {
                Services.PLATFORM.sendToClient(new S2CBodyPartsPacket(stacks, other.getUUID()), null, player);
            }
            other.refreshDimensions();
            bodyPartContainer.setDirty(false);
        }
    }

    public S2CBodyPartsPacket(FriendlyByteBuf buf) {
        int i = buf.readShort();
        stacks = NonNullList.withSize(i, ItemStack.EMPTY);
        for(int j = 0; j < i; ++j) {
            stacks.set(j, buf.readItem());
        }
        otherPlayer = buf.readUUID();
    }

    @Override
    public void handleClient() {
        Player localPlayer = CollectTheBodyClient.getLocalPlayer();
        if (localPlayer != null) {
            Player otherPlayer = localPlayer.level.getPlayerByUUID(this.otherPlayer);
            if (otherPlayer != null) {
                BodyPartContainer bodyPartContainer = ((PlayerDuck) otherPlayer).getBodyPartContainer();
                for (int i = 0; i < bodyPartContainer.items.size(); i++) {
                    bodyPartContainer.setItem(i, stacks.get(i));
                }
                otherPlayer.refreshDimensions();
            }
        }
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeShort(stacks.size());
        for (ItemStack stack : stacks) {
            buf.writeItem(stack);
        }
        buf.writeUUID(otherPlayer);
    }
}