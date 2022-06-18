package kz.chesschicken.smartygui.commonloader.fabric.mixin;

import kz.chesschicken.smartygui.commonloader.fabric.InjectHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.InGame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGame.class)
public class MixinOverlay {
    @Shadow private Minecraft minecraft;

    @Inject(method = "renderHud", at = @At("TAIL"))
    public void injectTickGame(float f, boolean flag, int i, int j, CallbackInfo ci) {
        InjectHelper.forEach(iMod -> iMod.onTickGame(this.minecraft));
    }
}
