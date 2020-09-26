package kz.chesschicken.smartygui.api;

import kz.chesschicken.smartygui.SmartyGui;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.CharacterUtils;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;

public class GuiCheatInput extends ScreenBase {
    protected String field_786 = "";
    private int field_787 = 0;
    private static final String field_788;
    //29
    public static ArrayList<String> toshow = new ArrayList<>();

    public static Integer[] termcolor = new Integer[3];

    public GuiCheatInput()
    {
        for(int i = 0; i < 3; i++)
            if(termcolor[i] == null)
                termcolor[i] = 0;
    }

    public void init() {
        Keyboard.enableRepeatEvents(true);
    }

    public void onClose() {
        Keyboard.enableRepeatEvents(false);
    }

    public void tick() {
        ++this.field_787;
    }

    protected void keyPressed(char character, int key) {
        if (key == 28) {
            String var3 = this.field_786.trim();
            if (var3.length() > 0) {
                CommandHandler.runCommand(this.minecraft, this.field_786.trim());
                this.field_786 = "";
            }

        } else if(key == 1) {
            this.minecraft.openScreen(null);
        }else
            {
                if (key == 14 && this.field_786.length() > 0) {
                    this.field_786 = this.field_786.substring(0, this.field_786.length() - 1);
                }

                if (field_788.indexOf(character) >= 0 && this.field_786.length() < 100) {
                    this.field_786 = this.field_786 + character;
                }

            }

    }

    public void render(int mouseX, int mouseY, float delta) {
        SmartyGui.gradientRender(0,0,this.width,this.height, (new Color(termcolor[0], termcolor[1], termcolor[2])).getRGB(), (new Color(termcolor[0], termcolor[1], termcolor[2])).getRGB());

        this.fill(2, this.height - 14, this.width - 2, this.height - 2, -2147483648);
        this.drawTextWithShadow(this.textManager, "# " + this.field_786 + (this.field_787 / 6 % 2 == 0 ? "_" : ""), 4, this.height - 12, 14737632);


        for(int a1 = 0; a1 < toshow.size(); a1++)
        {
            this.drawTextWithShadow(this.textManager, toshow.get(a1), 5, 5 + (8 * a1), 14737632);
        }


        super.render(mouseX, mouseY, delta);
    }

    protected void mouseClicked(int mouseX, int mouseY, int button) {
        if (button == 0) {
            if (this.minecraft.overlay.field_2541 != null) {
                if (this.field_786.length() > 0 && !this.field_786.endsWith(" ")) {
                    this.field_786 = this.field_786 + " ";
                }

                this.field_786 = this.field_786 + this.minecraft.overlay.field_2541;
                byte var4 = 100;
                if (this.field_786.length() > var4) {
                    this.field_786 = this.field_786.substring(0, var4);
                }
            } else {
                super.mouseClicked(mouseX, mouseY, button);
            }
        }

    }


    static {
        field_788 = CharacterUtils.validCharacters;
    }
}
