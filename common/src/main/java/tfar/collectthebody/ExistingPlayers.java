package tfar.collectthebody;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExistingPlayers extends SavedData {

    List<UUID> existingPlayers = new ArrayList<>();

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag listTag = new ListTag();
        existingPlayers.forEach(uuid -> listTag.add(StringTag.valueOf(uuid.toString())));
        tag.put("ExistingPlayers",listTag);
        return tag;
    }

    public boolean addPlayer(ServerPlayer player) {
        existingPlayers.add(player.getUUID());
        setDirty();
        return true;
    }

    public boolean isFirstJoin(ServerPlayer player) {
        return !existingPlayers.contains(player.getUUID());
    }

    protected void load(CompoundTag compoundTag) {
        existingPlayers.clear();
        ListTag listTag = compoundTag.getList("ExistingPlayers", Tag.TAG_STRING);
        for (Tag tag : listTag) {
            StringTag stringTag = (StringTag) tag;
            existingPlayers.add(UUID.fromString(stringTag.getAsString()));
        }
    }

    public static ExistingPlayers loadStatic(CompoundTag compoundTag, ServerLevel level) {
        ExistingPlayers existingPlayers = new ExistingPlayers();
        existingPlayers.load(compoundTag);
        return existingPlayers;
    }

    public static ExistingPlayers getData(MinecraftServer server) {
        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
        return overworld.getDataStorage()
                .computeIfAbsent(compoundTag -> ExistingPlayers.loadStatic(compoundTag,overworld), ExistingPlayers::new,
                        CollectTheBody.MOD_ID);
    }

}
