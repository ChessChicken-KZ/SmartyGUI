package smartygui;

import kz.chesschicken.smartygui.common.SmartyGUI;
import kz.chesschicken.smartygui.common.plugins.AbstractSmartyPlugin;
import kz.chesschicken.smartygui.commonloader.fabric.ClientInitImpl;
import org.jetbrains.annotations.ApiStatus;

public class SmartyGUIFabric extends ClientInitImpl<SmartyGUI> {

    public SmartyGUIFabric() {
        super(new SmartyGUI());
    }

    /**
     * Temporary way to include plugins in fabric.
     * @param plugin Plugin instance.
     */
    @ApiStatus.Experimental()
    public static void addPlugin(AbstractSmartyPlugin plugin) {
        SmartyGUI.PLUGINS.registerPlugin(plugin);
    }

}
