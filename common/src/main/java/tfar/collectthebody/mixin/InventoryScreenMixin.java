package tfar.collectthebody.mixin;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.collectthebody.client.CollectTheBodyClient;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin {

    @Inject(method = "*(Lnet/minecraft/client/gui/components/Button;)V",at = @At("RETURN"))
    private void onRecipeButtonClick(Button $$0, CallbackInfo ci) {
        CollectTheBodyClient.onRecipeBookButtonPressed($$0);
    }
}
