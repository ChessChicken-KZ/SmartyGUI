package kz.chesschicken.smartygui.mixin;

import kz.chesschicken.smartygui.common.SmartyGUIConfig;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.gui.screen.menu.MainMenu;
import net.minecraft.client.util.ScreenScaler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MainMenu.class)
public class MixinMainMenu extends ScreenBase {


    @Inject(method = "render", at = @At("TAIL"))
    public void injectRenderMainMenuDebug(int mouseX, int mouseY, float delta, CallbackInfo ci) {
        String stringToSent;
        int scaledWidth = (new ScreenScaler(this.minecraft.options, this.minecraft.actualWidth, this.minecraft.actualHeight)).getScaledWidth();
        if(SmartyGUIConfig.INSTANCE.enableMainMenuDebug) {
            long var24 = Runtime.getRuntime().maxMemory();
            long var29 = Runtime.getRuntime().totalMemory();
            long var30 = Runtime.getRuntime().freeMemory();
            long var21 = var29 - var30;
            stringToSent = "Used memory: " + var21 * 100L / var24 + "% (" + var21 / 1024L / 1024L + "MB) of " + var24 / 1024L / 1024L + "MB";
            this.drawTextWithShadow(this.textManager, stringToSent, scaledWidth - this.textManager.getTextWidth(stringToSent) - 2, 2, 5263440);
            stringToSent = "Allocated memory: " + var29 * 100L / var24 + "% (" + var29 / 1024L / 1024L + "MB)";
            this.drawTextWithShadow(this.textManager, stringToSent, scaledWidth - this.textManager.getTextWidth(stringToSent) - 2, 12, 5263440);
            stringToSent = "Using " + Mixin.class.getCanonicalName() + " as Injector";
            this.drawTextWithShadow(this.textManager, stringToSent, scaledWidth - this.textManager.getTextWidth(stringToSent) - 2, 22, 5263440);
        }
    }
}
