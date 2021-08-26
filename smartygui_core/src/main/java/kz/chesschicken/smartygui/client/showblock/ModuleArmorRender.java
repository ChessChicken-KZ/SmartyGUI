package kz.chesschicken.smartygui.client.showblock;

import kz.chesschicken.smartygui.client.ModuleRender;
import kz.chesschicken.smartygui.common.RenderUtils;
import kz.chesschicken.smartygui.common.SmartyGuiConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.util.ScreenScaler;

@Environment(EnvType.CLIENT)
public class ModuleArmorRender extends ModuleRender {
    private boolean leftOrRight;
    private boolean topOrBottom;

    private final ItemRenderer itemRenderer;

    public ModuleArmorRender(Minecraft minecraft) {
        super(minecraft);

        itemRenderer = new ItemRenderer();

        if(SmartyGuiConfig.INSTANCE.armorStatusHUDmode == 0 || SmartyGuiConfig.INSTANCE.armorStatusHUDmode == 2)
            leftOrRight = true;

        if(SmartyGuiConfig.INSTANCE.armorStatusHUDmode == 2 || SmartyGuiConfig.INSTANCE.armorStatusHUDmode == 3)
            topOrBottom = true;
    }

    @Override
    public void clean() {
        //nothing really
    }

    public void doArmorStatusRender()
    {
        ScreenScaler sr = new ScreenScaler(minecraft.options, minecraft.actualWidth, minecraft.actualHeight);
        int scaledWidth = leftOrRight ? sr.getScaledWidth() - 17 : 1;

        for(int q = 0; q < minecraft.player.inventory.armour.length; q++)
        {
            if(minecraft.player.inventory.armour[q] != null)
            {
                int damage = minecraft.player.inventory.armour[q].getDurability() - minecraft.player.inventory.armour[q].getDamage();
                RenderUtils.renderItem(itemRenderer, textRenderer, minecraft.textureManager, minecraft.player.inventory.armour[q], scaledWidth,
                        topOrBottom ? (15 * (3 - q)) : sr.getScaledHeight() - (minecraft.player.getHeldItem() != null ? 33 : 18) - (15 * q));

                textRenderer.drawTextWithShadow(String.valueOf(damage),
                        leftOrRight ? scaledWidth - textRenderer.getTextWidth(String.valueOf(damage)) : scaledWidth + 17,

                        topOrBottom ? (15 * q) + 5 : sr.getScaledHeight() - (minecraft.player.getHeldItem() != null ? 28 : 13) - (15 * q), 16777215);
            }
        }
        if(minecraft.player.getHeldItem() != null)
        {
            String output = minecraft.player.getHeldItem().getDurability() != 0 ? minecraft.player.getHeldItem().getDurability() - minecraft.player.getHeldItem().getDamage() + "" : "";
            RenderUtils.renderItem(itemRenderer, textRenderer, minecraft.textureManager, minecraft.player.getHeldItem(), scaledWidth,
                    topOrBottom ? 60 : sr.getScaledHeight() - 18);
            textRenderer.drawTextWithShadow(
                    output, leftOrRight ? scaledWidth - textRenderer.getTextWidth(output) : scaledWidth + 17,
                    topOrBottom ? 65 : sr.getScaledHeight() - 13, 16777215);
        }
    }
}
