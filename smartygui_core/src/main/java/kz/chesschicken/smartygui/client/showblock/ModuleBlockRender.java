package kz.chesschicken.smartygui.client.showblock;

import kz.chesschicken.smartygui.client.ModuleRender;
import kz.chesschicken.smartygui.common.RenderUtils;
import kz.chesschicken.smartygui.common.SmartyGuiConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.item.ItemInstance;

@Environment(EnvType.CLIENT)
public class ModuleBlockRender extends ModuleRender {
    private int currentBlockID;
    private int currentBlockMeta;
    private String stringBlockCoordinates;
    private String stringBlockProperties;

    private final ItemRenderer itemRenderer;

    public ModuleBlockRender(Minecraft minecraft) {
        super(minecraft);
        this.itemRenderer = new ItemRenderer();
    }

    public void updateBlock(int blockX, int blockY, int blockZ)
    {
        this.currentBlockID = this.minecraft.level.getTileId(blockX, blockY, blockZ);
        this.currentBlockMeta = this.minecraft.level.getTileMeta(blockX, blockY, blockZ);


        this.stringBlockCoordinates = "X: " + blockX + " Y: " + blockY + " Z: " + blockZ;

        this.stringBlockProperties = TranslationStorage.getInstance().method_995(
                new ItemInstance(currentBlockID, 1, minecraft.level.getTileMeta(blockX, blockY, blockZ)).getTranslationKey()).trim() +
                " " + currentBlockID + ":" + currentBlockMeta + " §" +
                RenderUtils.getColorByHardness(BlockBase.BY_ID[currentBlockID].getHardness()) + "H: " +
                BlockBase.BY_ID[currentBlockID].getHardness();

    }

    public void clean()
    {
        this.currentBlockID = 0;
        this.currentBlockMeta = 0;
        this.stringBlockCoordinates = "";
        this.stringBlockProperties = "";
    }

    public void doBlockRendering(int x, int y, boolean modern)
    {
        if(currentBlockID == 0 || BlockBase.BY_ID[currentBlockID] == null)
            return;

        if(modern)
        {
            int size = Math.max(textRenderer.getTextWidth(stringBlockCoordinates), textRenderer.getTextWidth(stringBlockProperties)) + 16;
            RenderUtils.gradientModern(25, 50, size, 28 , 23 , 3, size + 6);
        }
        else
            RenderUtils.gradientRender(x, y,
                    Math.max(textRenderer.getTextWidth(stringBlockCoordinates), textRenderer.getTextWidth(stringBlockProperties)) + 36,
                    40,
                    RenderUtils.getColour(SmartyGuiConfig.INSTANCE.showBlockRGB[0],
                            SmartyGuiConfig.INSTANCE.showBlockRGB[1],
                            SmartyGuiConfig.INSTANCE.showBlockRGB[2]),
                    RenderUtils.getColour(SmartyGuiConfig.INSTANCE.showBlockRGB[3],
                            SmartyGuiConfig.INSTANCE.showBlockRGB[4],
                            SmartyGuiConfig.INSTANCE.showBlockRGB[5]));

        RenderUtils.renderItem(itemRenderer,textRenderer, minecraft.textureManager,
                new ItemInstance(
                        this.currentBlockID,
                        1,
                        this.currentBlockMeta), x + 5, y + 5);

        textRenderer.drawText(stringBlockCoordinates, x + 25, y + 5, 16777215);
        textRenderer.drawText(stringBlockProperties, x + 25, y + 15, 16777215);
    }
}
