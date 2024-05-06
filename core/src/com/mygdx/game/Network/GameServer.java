package com.mygdx.game.Network;

import com.mygdx.game.Entities.PlayerMP;
import com.mygdx.game.Network.Packets.Packet;
import com.mygdx.game.Network.Packets.Packet00Login;
import com.mygdx.game.Network.Packets.Packet01Disconnect;
import com.mygdx.game.Network.Packets.Packet02Move;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread {
    private DatagramSocket socket;
    private List<PlayerMP> connectedPlayers = new ArrayList<>();

    public GameServer() {
        try {
            this.socket = new DatagramSocket(2787);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        System.out.println("Server listening for packets");
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
                System.out.println("["+ address.getAddress() + ": " + port + "]" +
                        ((Packet00Login) packet).getUsername() + " has connected");
                PlayerMP player = new PlayerMP(((Packet00Login) packet).getUsername(), (
                        (Packet00Login) packet).getX(), ((Packet00Login) packet).getY(),  address, port);

                this.addConnection(player, (Packet00Login) packet);
                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                        + ((Packet01Disconnect) packet).getUsername() + " has left...");
                this.removeConnection((Packet01Disconnect) packet);
                break;
            case MOVE:
                packet = new Packet02Move(data);
                this.handleMove(((Packet02Move) packet));
                break;
        }
    }


    public void addConnection(PlayerMP player, Packet00Login packet){
        boolean alreadyConnected = false;

        for(PlayerMP p : this.connectedPlayers){
            if(player.username.equalsIgnoreCase(p.username)){
                if(p.ipAddress == null) {
                    p.ipAddress = player.ipAddress;
                }
                if(p.port == -1){
                    p.port = player.port;
                }
                alreadyConnected = true;
            }else{
                //send data to client that another player has joined
                Packet00Login packetUser = new Packet00Login(p.username, p.getX(), p.getY());
                sendData(packet.getData(), p.ipAddress, p.port);
//                packet = new Packet00Login(p.getUsername(), p.x, p.y, packet.getTime());
                sendData(packetUser.getData(), player.ipAddress, player.port);
            }
        }

        if(!alreadyConnected){
            this.connectedPlayers.add(player);
            packet.writeData(this);
        }
    }

    public void removeConnection(Packet01Disconnect packet) {
        this.connectedPlayers.remove(getPlayerMPIndex(packet.getUsername()));
        packet.writeData(this);
    }

    public int getPlayerMPIndex(String username) {
        int index = 0;
        for (PlayerMP player : this.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                break;
            }
            index++;
        }
        return index;
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port){
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void sendDataToAllClients(byte[] data) {
        for(PlayerMP p : connectedPlayers){
            sendData(data, p.ipAddress, p.port);
        }
    }

    public PlayerMP getPlayerMP(String username) {
        for (PlayerMP player : this.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }


    private void handleMove(Packet02Move packet) {
        if (getPlayerMP(packet.getUsername()) != null) {
            int index = getPlayerMPIndex(packet.getUsername());
            PlayerMP player = this.connectedPlayers.get(index);
            player.setX(packet.getX());
            player.setY(packet.getY());
            packet.writeData(this);
        }
    }


    public static void main(String[] args) {
        GameServer socketServer = new GameServer();
        socketServer.start();
    }
}