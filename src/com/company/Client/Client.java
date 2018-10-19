package com.company.Client;

import com.company.Utilities.Commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client{
    
    private String name;
    private Socket socket;
    
    private boolean amIAlive = false;
    
    public Client(){}
    
    public Client(String name, Socket socket, boolean amIAlive)
    {
        this.name = name;
        this.socket = socket;
        this.amIAlive = amIAlive;
    }
    
    public boolean isAmIAlive() {
        return amIAlive;
    }
    
    public void setAmIAlive(boolean amIAlive) {
        this.amIAlive = amIAlive;
    }
    
    public String getName() {
        return name;
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    @Override
    public String toString() {
        return name;
    }

    
}
