package com.company.Server;

import com.company.Client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.regex.Pattern;

public class ServerMain {
    
    private static ServerSocket serverSocket;
    private static  final int PORT = 1234;
    
    public static List<Client> clients = new ArrayList<>();
    
    public static void main(String[] args) throws IOException
    {
        try{
            //create new server on this port
            serverSocket = new ServerSocket(PORT);
            
        }catch (IOException ioex)
        {
            System.out.println("\nUnable to set up port!");
            System.exit(1);
        }
        do {
            //wait for client to connect
            //create a client object that gets set to the object that the server socket creates when it accepts the incoming connection
            Socket client = serverSocket.accept();
            
            System.out.println("\nNew client accepted.\n");
            
            //create a separate thread to handle communication with this client
            //and give it the socket, so that when it creates a connection, it has created a socket which it can communicate on and from
            ClientHandler handler = new ClientHandler(client);
            handler.start();
            
        }while(true);
    }
    
    public static void removeAndUpdateList(String username)
    {
        Iterator<Client> clientIterator = clients.iterator();
        while(clientIterator.hasNext())
        {
            Client client = clientIterator.next();
            if(client.getName().equals(username))
            {
                clientIterator.remove();
            }
        }
    }

}
