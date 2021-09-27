package kz.chesschicken.smartygui.mixin;

import kz.chesschicken.smartygui.client.showblock.ModuleBlockRender;
import kz.chesschicken.smartygui.common.RenderUtils;
import net.minecraft.block.BlockBase;
import net.minecraft.client.render.block.BlockRenderer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockRenderer.class)
public class MixinBlockRenderer {
    @Inject(method = "method_48", at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/opengl/GL11;glColor4f(FFFF)V",
            shift = At.Shift.AFTER
    ))
    private void injectLoadColour(BlockBase arg, int i, float f, CallbackInfo ci) {
        if(ModuleBlockRender.amIBeingCaused == 1) {
            float[] co = RenderUtils.convertIntToFloatRGB(ModuleBlockRender.currentBlockColour);
            GL11.glColor4f(co[0] * f, co[1] * f, co[2] * f, 1.0F);
        }
    }
}
