package kz.chesschicken.smartygui.mixin;

import kz.chesschicken.smartygui.SmartyGui;
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
    @Shadow private TileEntityFurnace entity;
    @Inject(method = "renderForeground", at = @At("TAIL"))
    private void addtext(CallbackInfo callbackInfo)
    {
        if(SmartyGui.options_guifurnaceextended) {
            this.textManager.drawText("Burn: " + Math.round(entity.burnTime > 0 ? ((entity.burnTime * 100) / entity.fuelTime) : 0) + "%", 5, 6, 4210752);
            this.textManager.drawText("Cook: " + Math.round(entity.cookTime > 0 ? ((entity.cookTime * 100) / 200) : 0) + "%", 5, 14, 4210752);
            this.textManager.drawText("Fuel: " + ((entity.fuelTime > 0) ? (entity.fuelTime / 200) : 0) + " B", 5, 22, 4210752);
        }
    }
}
