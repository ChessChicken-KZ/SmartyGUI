package kz.chesschicken.smartygui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.packet.AbstractPacket;
import net.minecraft.server.MinecraftServer;
import net.modificationstation.stationapi.api.common.event.packet.MessageListenerRegister;
import net.modificationstation.stationapi.api.common.factory.GeneralFactory;
import net.modificationstation.stationapi.api.common.packet.Message;
import net.modificationstation.stationapi.api.common.packet.MessageListenerRegistry;
import net.modificationstation.stationapi.api.common.packet.PacketHelper;
import net.modificationstation.stationapi.api.common.registry.Identifier;
import net.modificationstation.stationapi.api.common.registry.ModID;

import java.util.List;

public class CustomPackerSender implements MessageListenerRegister {
    public static String[] staticPlayerList;
    public static int maxplayerList;

    @Environment(EnvType.CLIENT)
    public static void queue_PacketGetList()
    {
        Message packet = GeneralFactory.INSTANCE.newInst(Message.class, "smartygui:playerlist");
        PacketHelper.INSTANCE.send((AbstractPacket) packet);
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
            PacketHelper.INSTANCE.sendTo(playerBase, (AbstractPacket) packet);
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


    @Override
    public void registerMessageListeners(MessageListenerRegistry messageListenerRegistry, ModID modID) {
        messageListenerRegistry.registerValue(Identifier.of(modID, "playerlist"), this::handleSendPlayers);
        messageListenerRegistry.registerValue(Identifier.of(modID, "playerlistResult"), this::handleSendRes);
    }
}
