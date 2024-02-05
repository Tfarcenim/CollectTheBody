package tfar.collectthebody.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import tfar.collectthebody.BodyPartMenu;
import tfar.collectthebody.ducks.PlayerDuck;
import tfar.collectthebody.platform.Services;

public class C2SButtonPacket implements C2SModPacket {


    public C2SButtonPacket() {
    }

    public C2SButtonPacket(FriendlyByteBuf buf) {
    }

    public static void send() {
        Services.PLATFORM.sendToServer(new C2SButtonPacket(), null);
    }

    public void handleServer(ServerPlayer player) {
        player.openMenu(new SimpleMenuProvider((i, inventory, player1) -> {
            return new BodyPartMenu(i,inventory, ((PlayerDuck)player1).getBodyPartContainer());
        }, Component.literal("Body Parts")));
    }

    public void write(FriendlyByteBuf buf) {
    }

}
