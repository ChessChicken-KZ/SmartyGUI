package kz.chesschicken.smartygui.common;

import net.fabricmc.loader.api.FabricLoader;

public class APIDetector {
    public boolean existStationAPI;
    public boolean existCursedLegacyAPI;
    public boolean existOtherAPI;

    public void checkAPI()
    {
        existStationAPI = FabricLoader.getInstance().isModLoaded("stationapi");
        existCursedLegacyAPI = FabricLoader.getInstance().isModLoaded("api");

        //TODO: Implements other API's...
        existOtherAPI = false;
    }




    private APIDetector() {
    }

    public static APIDetector INSTANCE = new APIDetector();
}
