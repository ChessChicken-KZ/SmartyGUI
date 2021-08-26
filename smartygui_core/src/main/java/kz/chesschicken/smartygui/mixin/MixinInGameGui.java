package kz.chesschicken.smartygui.mixin;


import kz.chesschicken.smartygui.SmartyGui;
import kz.chesschicken.smartygui.common.RenderUtils;
import kz.chesschicken.smartygui.common.SmartyGuiConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.InGame;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.entity.Living;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.hit.HitType;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
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
    public void injectRenderModules(float f, boolean flag, int i, int j, CallbackInfo ci) {

        /* ShowBlock Part */
        if (SmartyGuiConfig.INSTANCE.enableShowBlock && minecraft.hitResult != null && !minecraft.paused && minecraft.currentScreen == null && !Minecraft.isDebugHudEnabled() && !minecraft.options.hideHud) {
            TextRenderer fr = minecraft.textRenderer;
            ItemRenderer  ir = new ItemRenderer();
            if (minecraft.hitResult.type == HitType.TILE) {
                int ix = minecraft.hitResult.x;
                int iy = minecraft.hitResult.y;
                int iz = minecraft.hitResult.z;
                String motd2;
                String motd = "X: " + ix + " Y: " + iy + " Z: " + iz;

                BlockBase currentBlock = BlockBase.BY_ID[minecraft.level.getTileId(ix, iy, iz)];
                float h = currentBlock.getHardness();

                if (currentBlock != null) {
                    motd2 = TranslationStorage.getInstance().method_995(new ItemInstance(
                            currentBlock,
                            1,
                            minecraft.level.getTileMeta(ix,iy,iz)).getTranslationKey()).trim() + " " +
                            currentBlock.id + ":" + minecraft.level.getTileMeta(ix,iy,iz) +
                            " " + RenderUtils.getColorByHardness(h) + "H: " + h;
                } else motd2 = null;

                if(SmartyGuiConfig.INSTANCE.showBlockModernStyle)
                {
                    int udp = fr.getTextWidth(fr.getTextWidth(motd) > fr.getTextWidth(motd2) ? motd : motd2) + 16;
                    RenderUtils.gradientModern(25, 50, udp, 28 , 23 , 3, udp + 6);
                }
                else

                    RenderUtils.gradientRender(5, 13,
                            fr.getTextWidth(fr.getTextWidth(motd) > fr.getTextWidth(motd2) ? motd : motd2) + 36,
                            40,
                            new Color(
                                    SmartyGuiConfig.INSTANCE.showBlockRGB[0],
                                    SmartyGuiConfig.INSTANCE.showBlockRGB[1],
                                    SmartyGuiConfig.INSTANCE.showBlockRGB[2]).getRGB(),
                            new Color(
                                    SmartyGuiConfig.INSTANCE.showBlockRGB[3],
                                    SmartyGuiConfig.INSTANCE.showBlockRGB[4],
                                    SmartyGuiConfig.INSTANCE.showBlockRGB[5]).getRGB());
                RenderUtils.renderItem(ir,fr,minecraft.textureManager,
                        new ItemInstance(
                                currentBlock.id,
                                1,
                                minecraft.level.getTileMeta(ix,iy,iz)), 10, 18);

                fr.drawText(motd, 30, 18, 16777215);
                fr.drawText(motd2, 30, 28, 16777215);


            } else {
                double ix = minecraft.hitResult.field_1989.x;
                double iy = minecraft.hitResult.field_1989.y;
                double iz = minecraft.hitResult.field_1989.z;
                String motd = "X: " + (int) ix + " Y: " + (int) iy + " Z: " + (int) iz;
                String motd2 = "Entity: " + minecraft.hitResult.field_1989.getClass().getSimpleName();


                if(SmartyGuiConfig.INSTANCE.showBlockModernStyle)
                    RenderUtils.gradientModern(30, 50, fr.getTextWidth(fr.getTextWidth(motd) > fr.getTextWidth(motd2) ? motd : motd2) + 16, 35 , 22 , 5, 0);
                else
                    RenderUtils.gradientRender(5, 13,
                            fr.getTextWidth(fr.getTextWidth(motd) > fr.getTextWidth(motd2) ? motd : motd2) + 16,
                            60,
                            new Color(
                                    SmartyGuiConfig.INSTANCE.showBlockRGB[0],
                                    SmartyGuiConfig.INSTANCE.showBlockRGB[1],
                                    SmartyGuiConfig.INSTANCE.showBlockRGB[2]).getRGB(),
                            new Color(
                                    SmartyGuiConfig.INSTANCE.showBlockRGB[3],
                                    SmartyGuiConfig.INSTANCE.showBlockRGB[4],
                                    SmartyGuiConfig.INSTANCE.showBlockRGB[5]).getRGB());


                fr.drawText(motd, 10, 18, 16777215);
                fr.drawText(motd2, 10, 28, 16777215);
                fr.drawText("ID: " + minecraft.hitResult.field_1989.entityId, 10, 38, 16777215);
                fr.drawText(
                        this.minecraft.hitResult.field_1989 instanceof Living
                                ? "Health: " + this.minecraft.hitResult.field_1989.getDataTracker().getInt(30) : "Health: Unknown"
                        , 10, 48, 16777215);

            }
        }

        /* ArmorStatusHUD Part */
        if(SmartyGuiConfig.INSTANCE.enableArmorStatusHUD && !minecraft.paused && minecraft.currentScreen == null && !minecraft.options.debugHud && !minecraft.options.hideHud)
        {
            TextRenderer fr = minecraft.textRenderer;
            ItemRenderer ir = new ItemRenderer();
            ScreenScaler sr = new ScreenScaler(minecraft.options, minecraft.actualWidth, minecraft.actualHeight);

            int scaledWidth = 0;
            boolean leftOrRight = false;
            boolean topOrBottom = false;


            if(SmartyGuiConfig.INSTANCE.armorStatusHUDmode == 0 || SmartyGuiConfig.INSTANCE.armorStatusHUDmode == 2) {
                scaledWidth = sr.getScaledWidth() - 17;
                leftOrRight = true;
            }
            else if(SmartyGuiConfig.INSTANCE.armorStatusHUDmode == 1 || SmartyGuiConfig.INSTANCE.armorStatusHUDmode == 3)
                scaledWidth = 1;


            if(SmartyGuiConfig.INSTANCE.armorStatusHUDmode == 2 || SmartyGuiConfig.INSTANCE.armorStatusHUDmode == 3)
                topOrBottom = true;


            for(int q = 0; q < minecraft.player.inventory.armour.length; q++)
            {
                if(minecraft.player.inventory.armour[q] != null)
                {
                    String motd = minecraft.player.inventory.armour[q].getDurability() - minecraft.player.inventory.armour[q].getDamage() + "";
                    RenderUtils.renderItem(ir, fr, minecraft.textureManager, minecraft.player.inventory.armour[q], scaledWidth,
                            topOrBottom ? (15 * (3 - q)) : sr.getScaledHeight() - (minecraft.player.getHeldItem() != null ? 33 : 18) - (15 * q));

                    fr.drawTextWithShadow(motd,
                            leftOrRight ? scaledWidth - fr.getTextWidth(motd) : scaledWidth + 17,

                            topOrBottom ? (15 * q) + 5 : sr.getScaledHeight() - (minecraft.player.getHeldItem() != null ? 28 : 13) - (15 * q), 16777215);
                }
            }
            if(minecraft.player.getHeldItem() != null)
            {
                String motd = minecraft.player.getHeldItem().getDurability() != 0 ? minecraft.player.getHeldItem().getDurability() - minecraft.player.getHeldItem().getDamage() + "" : "";
                RenderUtils.renderItem(ir, fr, minecraft.textureManager, minecraft.player.getHeldItem(), scaledWidth,
                        topOrBottom ? 60 : sr.getScaledHeight() - 18);
                fr.drawTextWithShadow(
                        motd, leftOrRight ? scaledWidth - fr.getTextWidth(motd) : scaledWidth + 17,
                        topOrBottom ? 65 : sr.getScaledHeight() - 13

                        , 16777215);

            }

        }

        //InGame ToolTip part
        if(SmartyGuiConfig.INSTANCE.enableInGameToolTip && this.minecraft.player.getHeldItem() != null && !this.minecraft.paused && this.minecraft.currentScreen == null)
        {
            TextRenderer fr = minecraft.textRenderer;
            ScreenScaler screenScaler = (new ScreenScaler(this.minecraft.options, this.minecraft.actualWidth, this.minecraft.actualHeight));

            String ver23;
            if(this.minecraft.player.getHeldItem().getDurability() < 1)
            {
                ver23 = TranslationStorage.getInstance().method_995(this.minecraft.player.getHeldItem().getTranslationKey());

            }else
            {
                ver23 = TranslationStorage.getInstance().method_995(this.minecraft.player.getHeldItem().getTranslationKey()) + " | " + (this.minecraft.player.getHeldItem().getDurability() - this.minecraft.player.getHeldItem().getDamage()) + "/" + this.minecraft.player.getHeldItem().getDurability();
            }

            if(this.minecraft.player.getHeldItem().itemId == ItemBase.clock.id)
            {
                ver23 = TranslationStorage.getInstance().method_995(this.minecraft.player.getHeldItem().getTranslationKey()) + " | " + (minecraft.level.isDaylight() ? "Day" : "Night");
            }
            fr.drawTextWithShadow(ver23, (int) screenScaler.scaledWidth / 2 - (fr.getTextWidth(ver23) / 2), (int) screenScaler.scaledHeight - 50, 16777215);
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

        }
    }

    @Inject(method = "addChatMessage", at = @At("TAIL"))
    public void injectLogMessage(String string, CallbackInfo ci)
    {
        SmartyGui.logMessageAs(this.getClass().getSimpleName(), "[CHAT] " + string);
    }






}
