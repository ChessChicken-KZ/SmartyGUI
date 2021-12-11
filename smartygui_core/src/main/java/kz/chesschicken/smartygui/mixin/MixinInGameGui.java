package kz.chesschicken.smartygui.mixin;

import kz.chesschicken.smartygui.SmartyGUI;
import kz.chesschicken.smartygui.client.showblock.ModuleArmorRender;
import kz.chesschicken.smartygui.client.showblock.ModuleBlockRender;
import kz.chesschicken.smartygui.client.showblock.ModuleEntityRenderer;
import kz.chesschicken.smartygui.client.showblock.ModuleToolTipRender;
import kz.chesschicken.smartygui.common.APIDetector;
import kz.chesschicken.smartygui.common.SmartyGUIConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.InGame;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.util.hit.HitType;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InGame.class)
public class MixinInGameGui {

    @Shadow
    private Minecraft minecraft;

    @Unique
    private ModuleBlockRender renderBlock;

    @Unique
    private ModuleEntityRenderer renderEntity;

    @Unique
    private ModuleArmorRender renderStatus;

    @Unique
    private ModuleToolTipRender renderToolTip;


    @Inject(method = "renderHud", at = @At("TAIL"))
    public void injectRenderModules(float f, boolean flag, int i, int j, CallbackInfo ci) {
        if(renderBlock == null) renderBlock = new ModuleBlockRender(minecraft);
        if(renderEntity == null) renderEntity = new ModuleEntityRenderer(minecraft);
        if(renderStatus == null) renderStatus = new ModuleArmorRender(minecraft);
        if(renderToolTip == null) renderToolTip = new ModuleToolTipRender(minecraft);

        /* ShowBlock Part */
        if (SmartyGUIConfig.INSTANCE.enableShowBlock && minecraft.hitResult != null && !minecraft.paused && minecraft.currentScreen == null && !Minecraft.isDebugHudEnabled() && !minecraft.options.hideHud) {
            if (minecraft.hitResult.type == HitType.TILE) {
                renderBlock.updateBlock(minecraft.hitResult.x, minecraft.hitResult.y, minecraft.hitResult.z);
                renderBlock.doBlockRendering(5, 13, SmartyGUIConfig.INSTANCE.showBlockModernStyle);
                renderBlock.clean();
            } else {
                renderEntity.updateEntity(minecraft.hitResult.field_1989);
                renderEntity.doEntityRendering(5, 13, SmartyGUIConfig.INSTANCE.showBlockModernStyle);
                renderEntity.clean();
            }
        }

        /* ArmorStatusHUD Part */
        if(SmartyGUIConfig.INSTANCE.enableArmorStatusHUD && !minecraft.paused && minecraft.currentScreen == null && !minecraft.options.debugHud && !minecraft.options.hideHud) {
            renderStatus.doArmorStatusRender();
            renderStatus.clean();
        }

        //InGame ToolTip part
        if(SmartyGUIConfig.INSTANCE.enableInGameToolTip && !this.minecraft.paused && this.minecraft.currentScreen == null) {
            renderToolTip.doTooltipRender(this.minecraft.player.getHeldItem(), f);
            renderToolTip.clean();
        }

    }



    @Inject(method = "renderHud", at = @At("TAIL"))
    public void injectRenderAdditionalDebug(float f, boolean flag, int i, int j, CallbackInfo ci) {
        //F3 Extended debug
        if (this.minecraft.options.debugHud) {
            TextRenderer fr = minecraft.textRenderer;
            String stringToSent;
            int scaledWidth = (new ScreenScaler(this.minecraft.options, this.minecraft.actualWidth, this.minecraft.actualHeight)).getScaledWidth();

            fr.drawTextWithShadow("yaw: " + this.minecraft.player.yaw, 2, 96, 14737632);
            fr.drawTextWithShadow("Biome: " + this.minecraft.player.level.getBiomeSource().getBiome((int) this.minecraft.player.x, (int) this.minecraft.player.z).biomeName, 2, 104, 14737632);
            fr.drawTextWithShadow("World Seed: " + this.minecraft.player.level.getSeed(), 2, 112, 14737632);
            fr.drawTextWithShadow("World Time: " + this.minecraft.player.level.getLevelTime(), 2, 120, 14737632);
            fr.drawTextWithShadow("World Name: " + this.minecraft.player.level.getProperties().getName(), 2, 128, 14737632);

            stringToSent = "OS: " + SmartyGUI.getFullOperatingSystemName();
            fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 26, 14737632);
            if(SmartyGUI.getProcessorInfo() != null) {
                stringToSent = "CPU: " + SmartyGUI.getProcessorInfo();
                fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 36, 14737632);
            }
            stringToSent = "GPU: " + GL11.glGetString(GL11.GL_RENDERER);
            fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 46, 14737632);
            stringToSent = GL11.glGetString(GL11.GL_VERSION) + " " + GL11.glGetString(GL11.GL_VENDOR);
            fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 56, 14737632);


            stringToSent = "Loaded Fabric Mods: " + FabricLoader.getInstance().getAllMods().size();
            fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 76, 14737632);

            int q = 0;
            if(APIDetector.INSTANCE.existStationAPI) {
                stringToSent = "StationAPI is present.";
                fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 86 + q, 14737632);
                q += 10;
            }
            if(APIDetector.INSTANCE.existCursedLegacyAPI) {
                stringToSent = "CursedLegacyAPI is present.";
                fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 86 + q, 14737632);
                q += 10;
            }

        }
    }

    @Inject(method = "addChatMessage", at = @At("TAIL"))
    public void injectLogMessage(String string, CallbackInfo ci) {
        SmartyGUI.logMessageAs(this.getClass().getSimpleName(), "[CHAT] " + string);
    }

}
