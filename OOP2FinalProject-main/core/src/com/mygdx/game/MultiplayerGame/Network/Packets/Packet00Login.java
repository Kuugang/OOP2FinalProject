package com.mygdx.game.MultiplayerGame.Network.Packets;

import com.mygdx.game.MultiplayerGame.Network.GameClient;
import com.mygdx.game.MultiplayerGame.Network.GameServer;

public class Packet00Login extends Packet{
    private String username;
    private float x, y;

    public Packet00Login(byte[] data){
        super(00);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Float.parseFloat(dataArray[1]);
        this.y = Float.parseFloat(dataArray[2]);
    }

    public Packet00Login(String username, float x, float y){
        super(00);
        this.username = username;
        this.x = x;
        this.y = y;
    }

    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return ("00" + this.username + "," + getX() + "," + getY()).getBytes();
    }

    public String getUsername(){
        return username;
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
}