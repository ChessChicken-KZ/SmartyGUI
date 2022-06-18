package kz.chesschicken.smartygui.client;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawableHelper;

public class RenderableLabel extends DrawableHelper {
    public int sizeX;
    public int sizeY;
    public int x;
    public int y;
    public boolean canRender;

    public RenderableLabel(int x, int y, int sizeX, int sizeY) {
        this.sizeX = 200;
        this.sizeY = 20;
        this.canRender = true;
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void render(Minecraft minecraft, int mouseX, int mouseY, String s) {
        if (this.canRender) {
            if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
                this.fill(this.x, this.y, this.x + this.sizeX, this.y + this.sizeY, 0xFFFFFF);
            }

            if(isMouseOver(mouseX, mouseY)) {
                this.fillGradient(mouseX + 9, mouseY - 15, mouseX + 15 + minecraft.textRenderer.getTextWidth(s), mouseY - 1, -1073741824, -1073741824);
                minecraft.textRenderer.drawTextWithShadow(s, mouseX + 12, mouseY - 12, -1);
            }
        }
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return this.canRender && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.sizeX && mouseY < this.y + this.sizeY;
    }


}
