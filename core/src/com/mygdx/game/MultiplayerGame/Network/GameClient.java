package com.mygdx.game.MultiplayerGame.Network;

import com.mygdx.game.MultiplayerGame.Entities.PlayerMP;
import com.mygdx.game.MultiplayerGame.GameScreen;
import com.mygdx.game.MultiplayerGame.Network.Packets.Packet;
import com.mygdx.game.MultiplayerGame.Network.Packets.Packet00Login;
import com.mygdx.game.MultiplayerGame.Network.Packets.Packet01Disconnect;
import com.mygdx.game.MultiplayerGame.Network.Packets.Packet02Move;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Objects;

public class GameClient extends Thread {
    private InetAddress ipAddress;
    private DatagramSocket socket;

    public GameClient(String ipAddress) {
        try {
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
                this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
        Packet packet;

        switch (type){
            default:
            case INVALID:
                break;
            case LOGIN:
                packet = new Packet00Login(data);
                if(Objects.equals(GameScreen.player.getUsername(), ((Packet00Login) packet).getUsername()))
                    return;

                System.out.println("["+ Arrays.toString(address.getAddress()) + ": " + port + "]" +
                        ((Packet00Login) packet).getUsername() + " has joined the game");

                if (!GameScreen.otherPlayers.containsKey(((Packet00Login) packet).getUsername())) {
                    PlayerMP player = new PlayerMP(((Packet00Login) packet).getUsername(),
                            ((Packet00Login) packet).getX(), ((Packet00Login) packet).getY(), address, port);
                    GameScreen.otherPlayers.put(((Packet00Login) packet).getUsername(), player);
                }
                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                        + ((Packet01Disconnect) packet).getUsername() + " has left the world...");
                GameScreen.otherPlayers.remove(((Packet01Disconnect) packet).getUsername());
                break;
            case MOVE:

//                packet = new Packet02Move(data);
//
//                if(Objects.equals(((Packet02Move) packet).getUsername(), GameScreen.player.getUsername()))
//                    return;
//
//                String username = ((Packet02Move) packet).getUsername();
//                PlayerMP player = GameScreen.otherPlayers.get(username);
//
//                float serverX = ((Packet02Move) packet).getX();
//                float serverY = ((Packet02Move) packet).getY();
//
//                player.setX(serverX);
//                player.setY(serverY);

                break;
        }
    }

    public void sendData(byte[] data){
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 2787);
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}