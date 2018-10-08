package com.company.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import static com.company.Utilities.StringUtilities.STRIPTHEFUCKINGSLASHOFFMYIPADDRESS;

public class ClientSend implements Runnable {

    boolean connectionEstablished = false;
    private static InetAddress host;
    private static final int PORT = 1234;

    Client client;

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
            System.out.println("ClientSend waits to send message");
            socket = new Socket(host,PORT);

            PrintWriter networkOutput = new PrintWriter(socket.getOutputStream(),true);
            Scanner networkInput = new Scanner(socket.getInputStream());


            Scanner userEntry = new Scanner(System.in);

            //this is the message the client sends
            String message = "";

            //this is the response client gets from the server
            String response = "";
            do
            {
                //read respond message from server



                //Først send user name med join protocol
                //På server vent som den første besked kun på dem med join protocol, ellers return
                System.out.println("please enter your username:");
                String name = userEntry.nextLine();

                //create temp client
                client = new Client(name,STRIPTHEFUCKINGSLASHOFFMYIPADDRESS(socket),socket.getPort());

                //sent join msg with new compiled client
                networkOutput.println(client.sendJOIN());

                response = networkInput.nextLine();
                System.out.println("SERVER> " + response);
                //get response message protocol J_OK
                if(response.contains("J_OK"))
                {
                    System.out.println("connection established");
                    System.out.println("SERVER> " + response);
                    connectionEstablished = true;
                } //get response message protocl J_ERR
                else if(response.contains("J_ERR"))
                {
                    System.out.println("username already exists, try another");
                    connectionEstablished = false;
                }
                
            }while (!response.contains("J_OK") && !connectionEstablished);
            
            do {
                System.out.println("please enter a message");
                message = userEntry.nextLine();
                //response = "";
                networkOutput.println(message);
                
                System.out.println("SERVER> " + response);
            }while(connectionEstablished && !message.equals("***QUIT***"));
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
