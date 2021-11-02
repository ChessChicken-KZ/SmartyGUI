package kz.chesschicken.smartygui.common;

import net.fabricmc.loader.api.FabricLoader;

public class APIDetector {
    public boolean existStationAPI;
    public boolean existCursedLegacyAPI;

    public void checkAPI() {
        existStationAPI = FabricLoader.getInstance().isModLoaded("stationapi");
        existCursedLegacyAPI = FabricLoader.getInstance().isModLoaded("api");
    }

    private APIDetector() {
    }

    public static APIDetector INSTANCE = new APIDetector();
}
