package tfar.collectthebody.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.collectthebody.BodyPartContainer;
import tfar.collectthebody.CollectTheBody;
import tfar.collectthebody.ducks.PlayerDuck;

import java.util.Map;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerDuck  {

    @Shadow public abstract void remove(RemovalReason pReason);

    @Shadow @Final private static Map<Pose, EntityDimensions> POSES;
    private BodyPartContainer bodyPartContainer = new BodyPartContainer((Player)(Object)this);

    protected PlayerMixin(EntityType<? extends LivingEntity> $$0, Level $$1) {
        super($$0, $$1);
    }

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

    @Inject(method = "getDimensions",at = @At("HEAD"),cancellable = true)
    private void onGetPoseEvent(Pose $$0, CallbackInfoReturnable<EntityDimensions> cir) {
        EntityDimensions newDims = CollectTheBody.getDimensions((Player) (Object)this,$$0,POSES);
        if (newDims != null) cir.setReturnValue(newDims);
    }
}
