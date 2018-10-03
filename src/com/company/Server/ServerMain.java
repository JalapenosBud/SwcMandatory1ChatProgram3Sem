package com.company.Server;

import com.company.Client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ServerMain {

    static ServerSocket serverSocket;
    private static final int port = 1234;

    private List<Client> clients = new ArrayList<>();

    public static void main(String[] args) {

        Socket mySocket = null;

        try{
            serverSocket = new ServerSocket(port);
        }catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("\nunable to set up port");
            System.exit(1);
        }

        do {

            try{
                mySocket = serverSocket.accept();
            }
            catch (IOException e)
            {
                System.out.println("couldnt connect");
                System.exit(1);
            }
            Server handler = new Server(mySocket);
            handler.start();

        }while(true);
    }

}
