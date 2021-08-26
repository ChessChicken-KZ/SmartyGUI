package kz.chesschicken.smartygui.mixin;

import kz.chesschicken.smartygui.common.SmartyGuiConfig;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.gui.screen.container.Furnace;
import net.minecraft.tileentity.TileEntityFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Furnace.class)
public class MixinGuiFurnace extends ScreenBase
{
    @Shadow private TileEntityFurnace furnace;

    @Inject(method = "renderForeground", at = @At("TAIL"))
    private void addtext(CallbackInfo callbackInfo)
    {
        if(SmartyGuiConfig.INSTANCE.enableExtendedFurnaceInfo) {
            this.textManager.drawText("Burn: " + Math.round(furnace.burnTime > 0 ? ((furnace.burnTime * 100) / furnace.fuelTime) : 0) + "%", 5, 6, 4210752);
            this.textManager.drawText("Cook: " + Math.round(furnace.cookTime > 0 ? ((furnace.cookTime * 100) / 200) : 0) + "%", 5, 14, 4210752);
            this.textManager.drawText("Fuel: " + ((furnace.fuelTime > 0) ? (furnace.fuelTime / 200) : 0) + " B", 5, 22, 4210752);
        }
    }
}
