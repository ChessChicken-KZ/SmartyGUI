package kz.chesschicken.smartyguistapi.mixin;

import kz.chesschicken.smartygui.common.RenderUtils;
import kz.chesschicken.smartygui.common.SmartyGuiConfig;
import kz.chesschicken.smartyguistapi.CustomPacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.InGame;
import net.minecraft.client.util.ScreenScaler;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InGame.class)
public class MixinList {
    byte tickGui = 40;
    boolean canRender;
    @Shadow
    private Minecraft minecraft;

    @Inject(method = "renderHud", at = @At("TAIL"))
    public void injectRenderPlayerList(float f, boolean flag, int i, int j, CallbackInfo ci) {
        if(SmartyGuiConfig.INSTANCE.enablePlayerList && Keyboard.isKeyDown(Keyboard.KEY_TAB) && minecraft.level.isClient)
        {
            doTicking();

            ScreenScaler screenScaler = new ScreenScaler(this.minecraft.options, this.minecraft.actualWidth, this.minecraft.actualHeight);
            String[] playerList = CustomPacketSender.staticPlayerList;
            int maxInt = CustomPacketSender.maxplayerList;
            int maxInt2 = maxInt;

            int param1;
            for (param1 = 1; maxInt2 > 20; maxInt2 = (maxInt + param1 - 1) / param1)
                ++param1;

            int param3 = 300 / param1;
            if (param3 > 150)
                param3 = 150;




            int variableWidth1 = (screenScaler.getScaledWidth() - param1 * param3) / 2;
            RenderUtils.gradientRender(variableWidth1 - 1, (byte)10 - 1, variableWidth1 + param3 * param1, (byte)10 + 9 * maxInt2, Integer.MIN_VALUE, Integer.MIN_VALUE);

            for (int i1 = 0; i1 < maxInt; ++i1) {
                int textX = variableWidth1 + i1 % param1 * param3;
                int textY = (byte)10 + i1 / param1 * 9;
                RenderUtils.gradientRender(textX, textY, textX + param3 - 1, textY + 8, 553648127, 553648127);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_ALPHA_TEST);

                if (i1 < playerList.length) {
                    minecraft.textRenderer.drawTextWithShadow(playerList[i1], textX, textY, 16777215);
                }

            }
        }
    }

    private void doTicking()
    {
        if(tickGui == 40)
        {
            CustomPacketSender.requestPlayerList();
            tickGui = 0;
        }else
            tickGui++;

        if(CustomPacketSender.staticPlayerList != null)
            canRender = true;
    }


}
