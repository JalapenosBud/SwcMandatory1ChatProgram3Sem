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

    public static Map<Client,Socket> clientMap = new HashMap<>();

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
                /*
                ha et set eller liste der adder ny client
                gem denne socket i en liste
                brug socket liste i parameter
                smid liste ned
                tag sidste addede socket og tilføj i liste
                 */

            }
            catch (IOException e)
            {
                System.out.println("couldnt connect");
                System.exit(1);
            }
            System.out.println("ny onnection " + mySocket);
            ThreadHandler handler = new ThreadHandler(mySocket,clientMap);
            handler.start();

        }while(true);
    }

}
