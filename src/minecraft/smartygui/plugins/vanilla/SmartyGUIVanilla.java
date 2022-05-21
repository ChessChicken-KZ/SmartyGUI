package smartygui.plugins.vanilla;

import kz.chesschicken.smartygui.commonloader.IMod;
import kz.chesschicken.smartygui.commonloader.ModDescription;
import net.minecraft.src.mod_SmartyGUI;

@ModDescription(
		name = "SmartyGUI - Vanilla Plugin Pack",
		description = "A set of plugins for vanilla to enchance the gameplay with SmartyGUI.",
		version = "0.1v - for SmartyGUI ML-2.2",
		icon = "/smartygui/icon.png"
		)
public class SmartyGUIVanilla implements IMod<SmartyGUIVanilla> {

	@Override
	public void onInitializeClient() {
	}

	@Override
	public void onPostInitClient() {
		mod_SmartyGUI.addPlugin(new PluginShowBreaking());
		mod_SmartyGUI.addPlugin(new PluginVanilla());
	}

}
