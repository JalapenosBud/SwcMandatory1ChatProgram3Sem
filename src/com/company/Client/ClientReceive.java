package com.company.Client;

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
            System.out.println("ClientRecieve waits for response from server");
            socket = new Socket(host,PORT);
            String response = "";
            Scanner networkInput = new Scanner(socket.getInputStream());

            do{
                response = networkInput.nextLine();

                if(response.contains("J_ERR"))
                {
                    System.out.println("user name already exists");
                }
                else if(response.contains("J_OK"))
                {
                    System.out.println("\nSERVER> " + response);
                }

            }while(!response.equals("QUIT"));

        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }




    }
}
