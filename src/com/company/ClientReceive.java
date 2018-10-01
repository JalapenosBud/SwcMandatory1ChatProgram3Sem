package com.company;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientReceive implements Runnable {

    private static InetAddress host;
    private static final int PORT = 1234;

    @Override
    public void run() {

        try
        {
            host = InetAddress.getLocalHost();
        }
        catch(UnknownHostException uhEx)
        {
            System.out.println("\nHost ID not found!\n");
            System.exit(1);
        }

        Socket socket = null;
        try
        {
            socket = new Socket(host,PORT);
            String response;
            Scanner networkInput = new Scanner(socket.getInputStream());

            response = networkInput.nextLine();

            System.out.println("\nSERVER> " + response);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }




    }
}
