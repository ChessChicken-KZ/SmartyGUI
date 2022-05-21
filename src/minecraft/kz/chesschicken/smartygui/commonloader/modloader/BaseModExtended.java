package kz.chesschicken.smartygui.commonloader.modloader;

import kz.chesschicken.smartygui.commonloader.IMod;
import net.minecraft.client.Minecraft;
import net.minecraft.src.BaseMod;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.ModLoader;

public abstract class BaseModExtended<T extends IMod<T>> extends BaseMod {

	protected T instance;
	
	public BaseModExtended(T t_holder) {
		this.instance = t_holder;
		this.instance.onInitializeClient();
        ModLoader.SetInGameHook(this, true, false);
        ModLoader.SetInGUIHook(this, true, false);
	}
	
    @Override
    public boolean OnTickInGame(Minecraft game) {
    	instance.onTickGame(game);
        return true;
    }

    @Override
    public boolean OnTickInGUI(Minecraft game, GuiScreen gui) {
    	instance.onTickInGUI(game, gui);
        return true;
    }
    
    @Override
    public void KeyboardEvent(KeyBinding event) {
    	instance.keyPressed(event);
    }

	@Override
	public void ModsLoaded() {
		instance.onPostInitClient();
	}

	/* Description methods. */
	
    @Override
    public String Version() {
        return instance.getVersion(instance.getClass());
    }
    
	public String Name() {
		return instance.getName(instance.getClass());
	}

	public String Description() {
		return instance.getDescription(instance.getClass());
	}

	public String Icon() {
		return instance.getIcon(instance.getClass());
	}

}
