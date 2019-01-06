package com.company.NewImplOfMan.Server;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Server
{
    private static ServerSocket serverSocket;
    private static final int PORT = 1234;
    
    public static Map<String,Socket> clients = new HashMap<>();
    
    public static void main(String[] args)
            throws IOException
    {
        try
        {
            serverSocket = new ServerSocket(PORT);
        }
        catch (IOException ioEx)
        {
            System.out.println("\nUnable to set up port!");
            System.exit(1);
        }
        do
        {
            Socket client = serverSocket.accept();
            System.out.println("\nNew client accepted.\n");
            ClientHandler handler = new ClientHandler(client);
            handler.start();
        }while (true);
    }
}
