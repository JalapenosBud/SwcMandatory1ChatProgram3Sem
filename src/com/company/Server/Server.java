package com.company.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server extends Thread{

    private Socket client;
    private Scanner input;

    private PrintWriter output;

    public Server(Socket socket) {
        //Set up reference to associated socket…
        client = socket;
        try {
            input = new Scanner(client.getInputStream());
            output = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    public void run ()
    {
        String received;
        do {
            //Accept message from client on the socket's input stream…
            received = input.nextLine();
            System.out.println("message received: " + received);
            //Echo message back to client on the socket's output stream…

            output.println("ECHO: " + received);
            //Repeat above until 'QUIT' sent by client…
        } while (!received.equals("QUIT"));
        try {
            if (client != null) {
                System.out.println("Closing down connection…");
                client.close();
            }
        } catch (IOException ioEx) {
            System.out.println("Unable to disconnect!");
        }
    }

}
