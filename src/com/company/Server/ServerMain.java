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

    //public  static List<Client> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {



        try{
            serverSocket = new ServerSocket(port);
        }catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("\nunable to set up port");
            System.exit(1);
        }

        do {
            handleClient();

        }while(true);
    }

    private static void handleClient() throws IOException {
        do {
            Socket mySocket = serverSocket.accept();
            System.out.println("\nNew client accepted.\n");
            ClientHandler handler = new ClientHandler(mySocket);
            handler.start();
        }while (true);
    }

}
