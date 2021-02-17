package kz.chesschicken.smartygui;

import net.modificationstation.stationapi.api.common.event.packet.MessageListenerRegister;
import net.modificationstation.stationapi.api.common.mod.StationMod;
import net.modificationstation.stationapi.api.common.registry.ModID;

public class SmartyGuiServer implements StationMod {
    @Override
    public void init(ModID modID) {
        MessageListenerRegister.EVENT.register(new CustomPackerSender(), modID);
    }
}
