package tfar.collectthebody.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.collectthebody.BodyPartContainer;
import tfar.collectthebody.ducks.PlayerDuck;

@Mixin(Player.class)
public class PlayerMixin implements PlayerDuck {

    private BodyPartContainer bodyPartContainer = new BodyPartContainer((Player)(Object)this);
    @Override
    public BodyPartContainer getBodyPartContainer() {
        return bodyPartContainer;
    }

    private static final String BP_ITEMS = "BodyParts";

    @Inject(method = "addAdditionalSaveData",at = @At("RETURN"))
    private void addData(CompoundTag tag, CallbackInfo ci) {
        tag.put(BP_ITEMS, bodyPartContainer.createTag());
    }

    @Inject(method = "readAdditionalSaveData",at = @At("RETURN"))
    private void readData(CompoundTag tag, CallbackInfo ci) {
        bodyPartContainer.fromTag(tag.getList(BP_ITEMS, Tag.TAG_COMPOUND));
    }
}
