package kz.chesschicken.smartygui.client.showblock;

import kz.chesschicken.smartygui.client.ModuleRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import org.lwjgl.opengl.GL11;

public class ModuleToolTipRender extends ModuleRender {
    private short tickRender;
    private int currentID;
    private int currentMeta;
    private String output;

    public ModuleToolTipRender(Minecraft minecraft) {
        super(minecraft);
    }

    @Override
    public void clean() {

    }

    public void doTooltipRender(ItemInstance item, float f)
    {
        if(item == null)
            return;


        if (item.itemId == currentID && item.getDamage() == currentMeta) {
            tickRender--;
        } else {
            tickRender = 80;
            currentID = item.itemId;
            currentMeta = item.getDamage();

            if(item.getDurability() < 1)
                output = TranslationStorage.getInstance().method_995(item.getTranslationKey());
            else
                output = TranslationStorage.getInstance().method_995(item.getTranslationKey()) + " | " + (item.getDurability() - item.getDamage()) + "/" + item.getDurability();

            if(item.itemId == ItemBase.clock.id)
                output = TranslationStorage.getInstance().method_995(item.getTranslationKey()) + " | " + (minecraft.level.isDaylight() ? "Day" : "Night");
        }

        if(tickRender > 0)
        {
            ScreenScaler screenScaler = (new ScreenScaler(this.minecraft.options, this.minecraft.actualWidth, this.minecraft.actualHeight));

            int q = (int) (((tickRender - f) * 256F) / 20F);
            if(q > 255) q = 255;
            if(q < 1) q = 1;

            GL11.glEnable(3042 /* GL_BLEND */);
            textRenderer.drawTextWithShadow(output, (int) screenScaler.scaledWidth / 2 - (textRenderer.getTextWidth(output) / 2), (int) screenScaler.scaledHeight - 50, 0xFFFFFF + (q << 24));
            GL11.glDisable(3042 /* GL_BLEND */);
        }
    }
}
