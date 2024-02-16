package tfar.collectthebody;

import draylar.identity.api.PlayerIdentity;
import net.minecraft.world.entity.player.Player;

public class IdentityHelper {

    public static boolean hasAnyIdentity(Player player) {
        return PlayerIdentity.getIdentity(player)!=null;
    }
}
