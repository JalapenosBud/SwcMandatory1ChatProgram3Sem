package com.company.Client;

import com.company.Utilities.Commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client{

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
    
    public Socket getSocket() {
        return socket;
    }
    
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    Socket socket;

    public Client()
    {
    }

    public Client(String name, Socket socket)
    {
        this.name = name;
        this.socket = socket;
        
    }

    @Override
    public String toString() {
        return name;
    }

    
}
