package com.company.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientSend implements Runnable {

    private static InetAddress host;
    private static final int PORT = 1234;

    Client client;

    @Override
    public synchronized void  run() {

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

            PrintWriter networkOutput = new PrintWriter(socket.getOutputStream(),true);
            Scanner networkInput = new Scanner(socket.getInputStream());


            Scanner userEntry = new Scanner(System.in);
            String message;
            String response = "";
            do
            {
                //Først send user name med join protocol
                //På server vent som den første besked kun på dem med join protocol, ellers return
                System.out.println("please enter your username:");
                String name = userEntry.nextLine();
                
                client = new Client(name,socket.getInetAddress(),socket.getPort());
                networkOutput.println(client.sendJOIN());
                response = networkInput.nextLine();

            }while (!response.equals("J_OK"));
        }
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
        finally
        {
            try
            {
                System.out.println("\nClosing connection…");
                socket.close();
            }
            catch(IOException ioEx)
            {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    }
}
