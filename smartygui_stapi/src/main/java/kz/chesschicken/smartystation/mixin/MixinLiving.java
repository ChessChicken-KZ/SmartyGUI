package kz.chesschicken.smartystation.mixin;

import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Living.class)
public abstract class MixinLiving extends EntityBase {

    @Shadow public int health;

    public MixinLiving(Level level) { super(level); }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectInitData(Level arg, CallbackInfo ci)
    {
        dataTracker.startTracking(30, this.health);
    }

    @Inject(method = "tickHandSwing", at = @At("TAIL"))
    private void injectUpdateDataTick(CallbackInfo ci)
    {
        dataTracker.setInt(30, this.health);
    }


    @Inject(method = "applyDamage", at = @At("TAIL"))
    private void injectUpdateDataDamage(CallbackInfo ci)
    {
        dataTracker.setInt(30, this.health);
    }

    @Inject(method = "addHealth", at = @At("TAIL"))
    private void injectUpdateDataAdd(CallbackInfo ci)
    {
        dataTracker.setInt(30, this.health);
    }


}
