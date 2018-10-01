package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class ServerMain {

    static ServerSocket serverSocket;
    private static final int port = 1234;
    public static void main(String[] args) {
        try{
            serverSocket = new ServerSocket(port);
        }catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("\nunable to set up port");
            System.exit(1);
        }

        Server server = new Server();
        server.start();

        do {
            Socket clientSocket = null;
            try{
                clientSocket = serverSocket.accept();
            }
            catch (IOException e)
            {
                System.out.println("couldnt connect");
                System.exit(1);
            }
            ClientHandler handler = new ClientHandler(clientSocket);
            handler.start();

        }while(true);



    }


}
