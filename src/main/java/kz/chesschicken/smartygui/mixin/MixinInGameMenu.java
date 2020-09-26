package kz.chesschicken.smartygui.mixin;


import kz.chesschicken.smartygui.api.GuiCheatInput;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.gui.screen.ingame.Pause;
import net.minecraft.client.gui.widgets.Button;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Pause.class)
public class MixinInGameMenu extends ScreenBase {

    @Inject(method = "init", at = @At("TAIL"))
    private void reg(CallbackInfo ci)
    {
        this.buttons.add(new Button(8, 5, 5, 20, 20, "CP"));
    }

    @Inject(method = "buttonClicked", at = @At("TAIL"))
    private void handlekey(Button button, CallbackInfo ci)
    {
        if(button.id == 8)
        {
            minecraft.openScreen(new GuiCheatInput());
        }
    }

}
