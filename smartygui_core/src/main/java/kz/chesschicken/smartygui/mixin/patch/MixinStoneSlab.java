package kz.chesschicken.smartygui.mixin.patch;

import net.minecraft.item.ItemInstance;
import net.minecraft.item.StoneSlab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StoneSlab.class)
public class MixinStoneSlab {

    @Inject(method = "getTranslationKey", at = @At("HEAD"), cancellable = true)
    private void patchFixCrash1(ItemInstance item, CallbackInfoReturnable<String> cir) {
        if(item.getDamage() < 0 || item.getDamage() > net.minecraft.block.StoneSlab.field_2323.length) {
            cir.setReturnValue("null");
            cir.cancel();
        }
    }

}
