package com.company.Server;

import com.company.Client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.regex.Pattern;

public class ServerMain {

    static ServerSocket serverSocket;
    private static final int port = 1234;

    public static void main(String[] args) throws IOException {

        try{
            serverSocket = new ServerSocket(port);
        }catch(IOException e)
        {
            System.out.println("\nunable to set up port");
            System.exit(1);
        }
        
        do {
            System.out.println("wait for client...");
            Socket mySocket = serverSocket.accept();
            
            System.out.println("\nNew client accepted.\n");
            ClientHandler handler = new ClientHandler(mySocket);
            handler.start();
            
            System.out.println("thread: " + handler.getName() + " is now started");
            
            //TODO: bad place to test but make this into a byte array
            /*String joined = "peter has joined";
            char[] chars = joined.toCharArray();
            
            for (int i = 0; i < ClientListManager.getInstance().getSize(); i++) {
                ClientListManager.getInstance().getClient(i).getSocket().getOutputStream().write();
            }*/
            
            //ClientListManager.getInstance().getClient().getSocket();
        }while (true);
    }

}
