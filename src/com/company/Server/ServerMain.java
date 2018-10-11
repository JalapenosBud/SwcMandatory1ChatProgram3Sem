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
                //System.out.println("\u001B[33mHello");
                mySocket = serverSocket.accept();
                /*
                ha et set eller liste der adder ny client
                gem denne socket i en liste
                brug socket liste i parameter
                smid liste ned
                tag sidste addede socket og tilføj i liste
                 */
                //System.out.println("ny connection " + mySocket);
                ThreadHandler handler = new ThreadHandler(mySocket);
                handler.start();
            }
            catch (IOException e)
            {
                System.out.println("couldnt connect");
                System.exit(1);
            }


        }while(true);
    }

}
