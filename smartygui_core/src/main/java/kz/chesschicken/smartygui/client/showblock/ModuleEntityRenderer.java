package kz.chesschicken.smartygui.client.showblock;

import kz.chesschicken.smartygui.SmartyGui;
import kz.chesschicken.smartygui.client.ModuleRender;
import kz.chesschicken.smartygui.common.RenderUtils;
import kz.chesschicken.smartygui.common.SmartyGuiConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.entity.Living;

@Environment(EnvType.CLIENT)
public class ModuleEntityRenderer extends ModuleRender
{
    private String stringEntityCoordinates;
    private String stringEntityName;
    private String stringEntityID;

    public ModuleEntityRenderer(Minecraft minecraft) {
        super(minecraft);
    }

    public void updateEntity(EntityBase entityBase)
    {
        this.stringEntityCoordinates = "X: " + (int) entityBase.x +
            " Y: " + (int) entityBase.y +
            " Z: " + (int) entityBase.z;

        this.stringEntityName = "Entity: " + EntityRegistry.getStringId(entityBase);
        this.stringEntityID = "ID: " + entityBase.entityId;
    }

    @Override
    public void clean() {
        this.stringEntityCoordinates = "";
        this.stringEntityName = "";
        this.stringEntityID = "";

    }

    public void doEntityRendering(int x, int y, boolean modern)
    {
        if(modern)
            RenderUtils.gradientModern(30, 50, Math.max(textRenderer.getTextWidth(stringEntityCoordinates), textRenderer.getTextWidth(stringEntityName)) + 16, 35 , 22 , 5, 0);
        else
            RenderUtils.gradientRender(x, y,
                    Math.max(textRenderer.getTextWidth(stringEntityCoordinates), textRenderer.getTextWidth(stringEntityName)) + 16,
                    65,
                    RenderUtils.convertRGBToInt(SmartyGuiConfig.INSTANCE.showBlockRGB[0],
                            SmartyGuiConfig.INSTANCE.showBlockRGB[1],
                            SmartyGuiConfig.INSTANCE.showBlockRGB[2]),
                    RenderUtils.convertRGBToInt(SmartyGuiConfig.INSTANCE.showBlockRGB[3],
                            SmartyGuiConfig.INSTANCE.showBlockRGB[4],
                            SmartyGuiConfig.INSTANCE.showBlockRGB[5]));

        textRenderer.drawText(stringEntityCoordinates, x + 5, y + 5, 16777215);
        textRenderer.drawText(stringEntityName, x + 5, y + 15, 16777215);
        textRenderer.drawText(stringEntityID, x + 5, y + 25, 16777215);
        textRenderer.drawText(getEntityBaseHealth(this.minecraft.hitResult.field_1989), x + 5, y + 35, 16777215);
    }

    private String getEntityBaseHealth(EntityBase entityBase)
    {
        if(entityBase instanceof Living)
        {
            if(SmartyGui.moduleStAPI)
                return "Health: " + (entityBase.getDataTracker().getInt(30));

            if(!minecraft.level.isClient)
                return "Health: " + ((Living)entityBase).health;
        }

        return "Health: unknown";
    }
}
