package net.jayugg.cardinalclasses.mixin;

import net.jayugg.cardinalclasses.gui.ChargeHudOverlay;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At(value = "HEAD"))
    public void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        ChargeHudOverlay.onHudRender(matrices);
    }
}
