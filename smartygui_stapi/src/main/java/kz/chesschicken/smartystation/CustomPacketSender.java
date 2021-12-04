package kz.chesschicken.smartystation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.server.MinecraftServer;
import net.modificationstation.stationapi.api.event.registry.MessageListenerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.packet.Message;
import net.modificationstation.stationapi.api.packet.PacketHelper;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;

import java.util.List;


public class CustomPacketSender
{
    public static String[] staticPlayerList;
    public static int maxplayerList;

    @Environment(EnvType.CLIENT)
    public static void requestPlayerList() {
        PacketHelper.send(new Message(Identifier.of("smartystation:playerlist")));
    }


    public void handleSendPlayers(PlayerBase playerBase, Message customData) {
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            Message packet = new Message(Identifier.of("smartystation:playerlistResult"));
            packet.strings = getPlayerNickList(((MinecraftServer) FabricLoader.getInstance().getGameInstance()).serverPlayerConnectionManager.players);
            packet.ints = (new int[]{
                    ((MinecraftServer) FabricLoader.getInstance().getGameInstance()).serverProperties.getInteger("max-players", 20)
            });
            PacketHelper.sendTo(playerBase, packet);
        }
    }

    public void handleSendRes(PlayerBase playerBase, Message customData) {
        staticPlayerList = customData.strings;
        maxplayerList = customData.ints[0];
    }


    public String[] getPlayerNickList(List<PlayerBase> players) {
        String[] toSend = new String[players.size()];
        for(int i = 0; i < toSend.length; i++)
        {
            toSend[i] = players.get(i).name;
        }
        return toSend;
    }

    @Entrypoint.ModID public static final ModID MOD_ID = Null.get();

    @SuppressWarnings("unused")
    @EventListener
    public void registerMessageListeners(MessageListenerRegistryEvent messageListenerRegistry) {
        messageListenerRegistry.registry.register(Identifier.of(MOD_ID, "playerlist"), this::handleSendPlayers);
        messageListenerRegistry.registry.register(Identifier.of(MOD_ID, "playerlistResult"), this::handleSendRes);
    }
}
