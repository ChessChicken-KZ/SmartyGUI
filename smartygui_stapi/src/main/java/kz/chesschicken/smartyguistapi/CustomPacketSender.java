package kz.chesschicken.smartyguistapi;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.server.MinecraftServer;
import net.modificationstation.stationapi.api.common.StationAPI;
import net.modificationstation.stationapi.api.common.event.EventListener;
import net.modificationstation.stationapi.api.common.event.packet.MessageListenerRegister;
import net.modificationstation.stationapi.api.common.factory.GeneralFactory;
import net.modificationstation.stationapi.api.common.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.common.packet.Message;
import net.modificationstation.stationapi.api.common.packet.PacketHelper;
import net.modificationstation.stationapi.api.common.registry.Identifier;
import net.modificationstation.stationapi.api.common.registry.ModID;
import net.modificationstation.stationapi.api.common.util.Null;

import java.util.List;


public class CustomPacketSender
{
    public static int getModCount()
    {
        return StationAPI.INSTANCE.getModsToVerifyOnClient().size();
    }
    public static String[] staticPlayerList;
    public static int maxplayerList;

    @Environment(EnvType.CLIENT)
    public static void queue_PacketGetList()
    {
        Message packet = GeneralFactory.INSTANCE.newInst(Message.class, "smartygui:playerlist");
        PacketHelper.send(packet);
    }





    public void handleSendPlayers(PlayerBase playerBase, Message customData)
    {
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER)
        {
            Message packet = GeneralFactory.INSTANCE.newInst(Message.class, "smartygui:playerlistResult");
            packet.put(getPlayerNickList(((MinecraftServer) FabricLoader.getInstance().getGameInstance()).serverPlayerConnectionManager.players));
            packet.put(new int[]{
                    ((MinecraftServer) FabricLoader.getInstance().getGameInstance()).serverProperties.getInteger("max-players", 20)
            });
            PacketHelper.sendTo(playerBase, packet);
        }
    }

    public void handleSendRes(PlayerBase playerBase, Message customData)
    {
        staticPlayerList = customData.strings();
        maxplayerList = customData.ints()[0];
    }


    public String[] getPlayerNickList(List players)
    {
        String[] toSend = new String[players.size()];
        for(int i = 0; i < toSend.length; i++)
        {
            toSend[i] = ((PlayerBase)players.get(i)).name;
        }
        return toSend;
    }

    @Entrypoint.ModID public static final ModID MOD_ID = Null.get();

    /*
     * Look here!
     * EventBUS!
     */
    @EventListener
    public void registerMessageListeners(MessageListenerRegister messageListenerRegistry) {
        messageListenerRegistry.registry.registerValue(Identifier.of(MOD_ID, "playerlist"), this::handleSendPlayers);
        messageListenerRegistry.registry.registerValue(Identifier.of(MOD_ID, "playerlistResult"), this::handleSendRes);

    }
}
