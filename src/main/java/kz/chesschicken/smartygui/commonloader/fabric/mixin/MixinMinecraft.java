package kz.chesschicken.smartygui.commonloader.fabric.mixin;

import kz.chesschicken.smartygui.commonloader.IMod;
import kz.chesschicken.smartygui.commonloader.fabric.InjectHelper;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ScreenBase;onKeyboardEvent()V", shift = At.Shift.AFTER))
    private void injectKeyActionGUI(CallbackInfo ci) {
        InjectHelper.forEach(iMod -> iMod.keyPressed(Keyboard.getEventKey()));
    }

    @Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;hasLevel()Z", shift = At.Shift.BEFORE, ordinal = 0))
    private void injectKeyActionGAME(CallbackInfo ci) {
        InjectHelper.forEach(iMod -> iMod.keyPressed(Keyboard.getEventKey()));
    }

    @Redirect(method = "init", at = @At(
            target = "Lnet/minecraft/client/Minecraft;printOpenGLError(Ljava/lang/String;)V",
            value = "INVOKE"
    ))
    private void injectPostInit(Minecraft instance, String s) {
        if(s.equals("Post startup")) {
            InjectHelper.forEach(IMod::onPostInitClient);
        }
    }

}
