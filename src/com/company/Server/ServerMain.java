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

    public static void main(String[] args) {



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

    private static void handleClient()
    {
        Socket mySocket = null;

        try{
            //System.out.println("\u001B[33mHello");
            mySocket = serverSocket.accept();
            ClientHandler handler = new ClientHandler(mySocket);
            handler.start();
        }
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
        finally
        {
            try
            {
                System.out.println("\n* Closing connection… *");
                mySocket.close();
            }
            catch(IOException ioEx)
            {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    }

}
