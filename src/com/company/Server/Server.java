package com.company.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server
{
    
    private static ServerSocket serverSocket;
    private static final int PORT = 1234;
    
    public static Map<String,Socket> clients = new HashMap<>();
    
    public static void main(String[] args) throws IOException
    {
        try
        {
            serverSocket = new ServerSocket(PORT);
        }
        catch (IOException ioex)
        {
            System.out.println("\nUnable to set up port!");
            System.exit(1);
        }
        do {
            Socket client = serverSocket.accept();
            System.out.println("\nNew client accepted.\n");
            ClientHandler handler = new ClientHandler(client);
            handler.start();
            
        }while(true);
    }
    
   /* public static void removeClientAndUpdateClientList(String username)
    {
        Iterator<Client> clientIterator = clientArrayList.iterator();
        while (clientIterator.hasNext())
        {
            Client client = clientIterator.next();
            if (client.getName().equals(username))
            {
                client.setAmIAlive(false);
                clientIterator.remove();
            }
        }
    }*/
}
