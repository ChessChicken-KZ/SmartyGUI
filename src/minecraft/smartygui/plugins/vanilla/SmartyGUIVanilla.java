package smartygui.plugins.vanilla;

import kz.chesschicken.smartygui.commonloader.IMod;
import kz.chesschicken.smartygui.commonloader.ModDescription;
import net.minecraft.src.mod_SmartyGUI;
import net.minecraft.src.mod_SmartyGUI_PfV;

@ModDescription(
		name = "SmartyGUI - Vanilla Plugin Pack",
		description = "A set of plugins for vanilla to enchance the gameplay with SmartyGUI.",
		version = "0.1v - for SmartyGUI ML-2.2",
		icon = "/smartygui/icon.png"
		)
public class SmartyGUIVanilla implements IMod<mod_SmartyGUI_PfV> {

	@Override
	public void onInitializeClient(mod_SmartyGUI_PfV t) {
	}

	@Override
	public void onPostInitClient() {
		mod_SmartyGUI.addPlugin(new PluginShowBreaking());
		mod_SmartyGUI.addPlugin(new PluginVanilla());
	}

}
