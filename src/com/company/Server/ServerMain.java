package com.company.Server;

import com.company.Client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerMain
{
    
    private static ServerSocket serverSocket;
    private static final int PORT = 1234;
    
    public static ArrayList<Client> clientArrayList = new ArrayList<>();
    
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
        do
        {
            Socket client = serverSocket.accept();
            System.out.println("\nNew client accepted.\n");
            
            ClientHandler handler = new ClientHandler(client);
            handler.start();
            if (handler.isAlive())
            {
                Thread checkusers = new Thread(new CheckOnlineUsers());
                checkusers.start();
            }
            
        } while (true);
        
        
    }
    
    public static void removeClientAndUpdateClientList(String username)
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
    }
}
