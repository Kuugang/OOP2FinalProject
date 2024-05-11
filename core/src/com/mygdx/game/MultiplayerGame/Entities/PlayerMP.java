package com.mygdx.game.MultiplayerGame.Entities;

import java.net.InetAddress;

public class PlayerMP extends Player {
    public InetAddress ipAddress;
    public int port;

    public PlayerMP(String username, float x, float y) {
        super(username, x, y);
    }

    public PlayerMP(String username, float x, float y, InetAddress ipAddress, int port) {
        this(username, x, y);
        this.x = x;
        this.y = y;
        this.ipAddress = ipAddress;
        this.port = port;
    }

}
