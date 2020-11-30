package kz.chesschicken.smartygui.mixin;


import kz.chesschicken.smartygui.SmartyGui;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.hit.HitType;
import net.modificationstation.stationloader.impl.common.StationLoader;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.InGame;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(value = InGame.class)
public class MixinInGameGui {

    @Shadow
    private Minecraft minecraft;


    @Inject(method = "renderHud", at = @At("TAIL"))
    public void renderMain(float f, boolean flag, int i, int j, CallbackInfo ci) {

        //Waila Part
        if (SmartyGui.options_showwaila && this.minecraft.hitResult != null && !this.minecraft.paused && this.minecraft.currentScreen == null && !this.minecraft.options.debugHud) {
            TextRenderer fr = minecraft.textRenderer;
            ItemRenderer ir = new ItemRenderer();
            if (this.minecraft.hitResult.type == HitType.TILE) {
                /*
                 * Part with Block
                 */
                int ix = this.minecraft.hitResult.x;
                int iy = this.minecraft.hitResult.y;
                int iz = this.minecraft.hitResult.z;
                String motd2;
                String motd = "X: " + ix + " Y: " + iy + " Z: " + iz;

                if (BlockBase.BY_ID[this.minecraft.level.getTileId(ix, iy, iz)] != null) {
                    motd2 = TranslationStorage.getInstance().method_995(new ItemInstance(BlockBase.BY_ID[this.minecraft.level.getTileId(ix, iy, iz)], 1, this.minecraft.level.getTileMeta(ix,iy,iz)).getTranslationKey()).trim() + " " + this.minecraft.level.getTileId(ix, iy, iz) + ":" + this.minecraft.level.getTileMeta(ix, iy, iz);
                } else {
                    motd2 = null;
                }

                if (this.minecraft.textRenderer.getTextWidth(motd) > this.minecraft.textRenderer.getTextWidth(motd2))
                {
                    SmartyGui.gradientRender(5, 13, this.minecraft.textRenderer.getTextWidth(motd) + 36, 40, new Color(SmartyGui.waila_rgbvalues[0], SmartyGui.waila_rgbvalues[1], SmartyGui.waila_rgbvalues[2]).getRGB(), new Color(SmartyGui.waila_rgbvalues[3], SmartyGui.waila_rgbvalues[4], SmartyGui.waila_rgbvalues[5]).getRGB());
                } else {
                    SmartyGui.gradientRender(5, 13, this.minecraft.textRenderer.getTextWidth(motd2) + 36, 40, new Color(SmartyGui.waila_rgbvalues[0], SmartyGui.waila_rgbvalues[1], SmartyGui.waila_rgbvalues[2]).getRGB(), new Color(SmartyGui.waila_rgbvalues[3], SmartyGui.waila_rgbvalues[4], SmartyGui.waila_rgbvalues[5]).getRGB());
                }

                if(motd2 != null)
                    SmartyGui.renderItem(ir,fr,this.minecraft.textureManager, new ItemInstance(BlockBase.BY_ID[this.minecraft.level.getTileId(ix, iy, iz)], 1, this.minecraft.level.getTileMeta(ix,iy,iz)), 10, 18);
                else
                    motd2 = "undefined";

                fr.drawText(motd, 30, 18, 16777215);
                fr.drawText(motd2, 30, 28, 16777215);

            } else {
                /*
                 * Part with Entity
                 */
                double ix = this.minecraft.hitResult.field_1989.x;
                double iy = this.minecraft.hitResult.field_1989.y;
                double iz = this.minecraft.hitResult.field_1989.z;
                String motd = "X: " + (int) ix + " Y: " + (int) iy + " Z: " + (int) iz;
                String motd2 = "Entity: " + this.minecraft.hitResult.field_1989.getClass().getSimpleName();
                if (this.minecraft.textRenderer.getTextWidth(motd) > this.minecraft.textRenderer.getTextWidth(motd2)) {
                    SmartyGui.gradientRender(5, 13, this.minecraft.textRenderer.getTextWidth(motd) + 16, 50, new Color(SmartyGui.waila_rgbvalues[0], SmartyGui.waila_rgbvalues[1], SmartyGui.waila_rgbvalues[2]).getRGB(), new Color(SmartyGui.waila_rgbvalues[3], SmartyGui.waila_rgbvalues[4], SmartyGui.waila_rgbvalues[5]).getRGB());
                } else {
                    SmartyGui.gradientRender(5, 13, this.minecraft.textRenderer.getTextWidth(motd2) + 16, 50, new Color(SmartyGui.waila_rgbvalues[0], SmartyGui.waila_rgbvalues[1], SmartyGui.waila_rgbvalues[2]).getRGB(), new Color(SmartyGui.waila_rgbvalues[3], SmartyGui.waila_rgbvalues[4], SmartyGui.waila_rgbvalues[5]).getRGB());
                }
                fr.drawText(motd, 10, 18, 16777215);

                fr.drawText(motd2, 10, 28, 16777215);
                fr.drawText("ID: " + this.minecraft.hitResult.field_1989.entityId, 10, 38, 16777215);
            }
        }

        //ArmorStatus HUD part
        if(SmartyGui.options_armorstatus && !this.minecraft.paused && this.minecraft.currentScreen == null && !this.minecraft.options.debugHud)
        {
            TextRenderer fr = minecraft.textRenderer;
            ItemRenderer ir = new ItemRenderer();
            int scaledHeight = (new ScreenScaler(this.minecraft.options, this.minecraft.actualWidth, this.minecraft.actualHeight)).getScaledHeight();
            for(int q = 0; q < this.minecraft.player.inventory.armour.length; q++)
            {
                if(this.minecraft.player.inventory.armour[q] != null)
                {
                    SmartyGui.renderItem(ir, fr, this.minecraft.textureManager, this.minecraft.player.inventory.armour[q], 1, scaledHeight - 18 - (15 * q));
                    fr.drawTextWithShadow(this.minecraft.player.inventory.armour[q].method_723() - this.minecraft.player.inventory.armour[q].getDamage() + "", 17, scaledHeight - 13 - (15 * q), 16777215);
                }

            }
        }

        //InGame ToolTip part
        if(SmartyGui.options_ingametooltip && this.minecraft.player.getHeldItem() != null && !this.minecraft.paused && this.minecraft.currentScreen == null)
        {
            TextRenderer fr = minecraft.textRenderer;
            ScreenScaler screenScaler = (new ScreenScaler(this.minecraft.options, this.minecraft.actualWidth, this.minecraft.actualHeight));

            String ver23;
            if(this.minecraft.player.getHeldItem().method_723() < 1)
            {
                ver23 = TranslationStorage.getInstance().method_995(this.minecraft.player.getHeldItem().getTranslationKey());

            }else
            {
                ver23 = TranslationStorage.getInstance().method_995(this.minecraft.player.getHeldItem().getTranslationKey()) + " | " + (this.minecraft.player.getHeldItem().method_723() - this.minecraft.player.getHeldItem().getDamage()) + "/" + this.minecraft.player.getHeldItem().method_723();
            }

            if(this.minecraft.player.getHeldItem().itemId == ItemBase.clock.id)
            {
                ver23 = TranslationStorage.getInstance().method_995(this.minecraft.player.getHeldItem().getTranslationKey()) + " | " + (minecraft.level.isDaylight() ? "Day" : "Night");
            }
            fr.drawTextWithShadow(ver23, (int) screenScaler.scaledWidth / 2 - (fr.getTextWidth(ver23) / 2), (int) screenScaler.scaledHeight - 50, 16777215);
        }

    }


    @Inject(method = "renderHud", at = @At("TAIL"))
    public void renderDebug(float f, boolean flag, int i, int j, CallbackInfo ci) {
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

            stringToSent = "OS: " + SmartyGui.getOSNAME();
            fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 26, 14737632);
            if(SmartyGui.getCPUINFO() != null)
            {
                stringToSent = "CPU: " + SmartyGui.getCPUINFO();
                fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 36, 14737632);
            }
            stringToSent = "GPU: " + GL11.glGetString(GL11.GL_RENDERER);
            fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 46, 14737632);
            stringToSent = GL11.glGetString(GL11.GL_VERSION) + " " + GL11.glGetString(GL11.GL_VENDOR);
            fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 56, 14737632);


            stringToSent = "Loaded Fabric Mods: " + FabricLoader.getInstance().getAllMods().size();
            fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 76, 14737632);
            stringToSent = "Loaded SL Mods: " + StationLoader.INSTANCE.getAllStationMods().size();
            fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 86, 14737632);


        }
    }

    @Inject(method = "addChatMessage", at = @At("TAIL"))
    public void logMessage(String string, CallbackInfo ci)
    {
        SmartyGui.logMessageAs(this.getClass().getSimpleName(), "[CHAT] " + string);
    }






}
