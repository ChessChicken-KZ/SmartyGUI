package kz.chesschicken.smartygui.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextRenderer;

@Environment(EnvType.CLIENT)
public abstract class ModuleRender {
    protected Minecraft minecraft;
    protected TextRenderer textRenderer;

    public ModuleRender(Minecraft minecraft)
    {
        this.minecraft = minecraft;
        this.textRenderer = minecraft.textRenderer;
    }

    public abstract void clean();

}
