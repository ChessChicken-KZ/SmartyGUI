package kz.chesschicken.smartygui.mixin;


import kz.chesschicken.smartygui.CustomPackerSender;
import kz.chesschicken.smartygui.SmartyGui;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.entity.Living;
import net.minecraft.entity.Painting;
import net.minecraft.entity.animal.Sheep;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.hit.HitType;
import net.modificationstation.stationloader.impl.common.StationLoader;
import org.lwjgl.input.Keyboard;
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

    int tickGui = 40;
    boolean canRender;

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
                    SmartyGui.gradientRender(5, 13, this.minecraft.textRenderer.getTextWidth(motd) + 16, 60, new Color(SmartyGui.waila_rgbvalues[0], SmartyGui.waila_rgbvalues[1], SmartyGui.waila_rgbvalues[2]).getRGB(), new Color(SmartyGui.waila_rgbvalues[3], SmartyGui.waila_rgbvalues[4], SmartyGui.waila_rgbvalues[5]).getRGB());
                } else {
                    SmartyGui.gradientRender(5, 13, this.minecraft.textRenderer.getTextWidth(motd2) + 16, 60, new Color(SmartyGui.waila_rgbvalues[0], SmartyGui.waila_rgbvalues[1], SmartyGui.waila_rgbvalues[2]).getRGB(), new Color(SmartyGui.waila_rgbvalues[3], SmartyGui.waila_rgbvalues[4], SmartyGui.waila_rgbvalues[5]).getRGB());
                }
                fr.drawText(motd, 10, 18, 16777215);

                fr.drawText(motd2, 10, 28, 16777215);
                fr.drawText("ID: " + this.minecraft.hitResult.field_1989.entityId, 10, 38, 16777215);

                fr.drawText(
                        this.minecraft.hitResult.field_1989 instanceof Living
                                ?
                                "Health: " + (this.minecraft.level.isClient ? this.minecraft.hitResult.field_1989.getDataTracker().getInt(30) : String.valueOf(((Living)this.minecraft.hitResult.field_1989).health))
                                :
                                "entitybase mob, not living"
                        , 10, 48, 16777215);

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
                    SmartyGui.renderItem(ir, fr, this.minecraft.textureManager, this.minecraft.player.inventory.armour[q], 1, scaledHeight - (minecraft.player.getHeldItem() != null ? 33 : 18) - (15 * q));
                    fr.drawTextWithShadow(this.minecraft.player.inventory.armour[q].method_723() - this.minecraft.player.inventory.armour[q].getDamage() + "", 17, scaledHeight - (minecraft.player.getHeldItem() != null ? 28 : 13) - (15 * q), 16777215);
                }
            }
            if(minecraft.player.getHeldItem() != null)
            {
                SmartyGui.renderItem(ir, fr, this.minecraft.textureManager, minecraft.player.getHeldItem(), 1, scaledHeight - 18);
                fr.drawTextWithShadow(
                        minecraft.player.getHeldItem().method_723() != 0 ? minecraft.player.getHeldItem().method_723() - minecraft.player.getHeldItem().getDamage() + "" : "", 17, scaledHeight - 13, 16777215);

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

        if(SmartyGui.options_playerlist && Keyboard.isKeyDown(Keyboard.KEY_TAB) && minecraft.level.isClient)
        {
            plTick();

            ScreenScaler screenScaler = new ScreenScaler(this.minecraft.options, this.minecraft.actualWidth, this.minecraft.actualHeight);
            String[] playerList = CustomPackerSender.staticPlayerList;
            int maxInt = CustomPackerSender.maxplayerList;
            int maxInt2 = maxInt;

            int param1;
            for (param1 = 1; maxInt2 > 20; maxInt2 = (maxInt + param1 - 1) / param1)
                ++param1;

            int param3 = 300 / param1;
            if (param3 > 150)
                param3 = 150;




            int variableWidth1 = (screenScaler.getScaledWidth() - param1 * param3) / 2;
            SmartyGui.gradientRender(variableWidth1 - 1, (byte)10 - 1, variableWidth1 + param3 * param1, (byte)10 + 9 * maxInt2, Integer.MIN_VALUE, Integer.MIN_VALUE);

            for (int i1 = 0; i1 < maxInt; ++i1) {
                int textX = variableWidth1 + i1 % param1 * param3;
                int textY = (byte)10 + i1 / param1 * 9;
                SmartyGui.gradientRender(textX, textY, textX + param3 - 1, textY + 8, 553648127, 553648127);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_ALPHA_TEST);

                if (i1 < playerList.length) {
                    minecraft.textRenderer.drawTextWithShadow(playerList[i1], textX, textY, 16777215);
                }

            }
        }

    }

    private void plTick()
    {
        if(tickGui == 40)
        {
            CustomPackerSender.queue_PacketGetList();
            tickGui = 0;
        }else
            tickGui++;

        if(CustomPackerSender.staticPlayerList != null)
            canRender = true;
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
