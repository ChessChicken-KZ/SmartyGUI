package kz.chesschicken.smartygui.commonloader.fabric.mixin;

import kz.chesschicken.smartygui.commonloader.fabric.InjectHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ScreenBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenBase.class)
public class MixinScreenBase {
    @Shadow protected Minecraft minecraft;

    @Inject(method = "tick", at = @At("TAIL"))
    private void injectTickGUI(CallbackInfo ci) {
        if(minecraft.currentScreen == ScreenBase.class.cast(this)) {
            InjectHelper.forEach(iMod -> iMod.onTickInGUI(minecraft, ScreenBase.class.cast(this)));
        }
    }
}
