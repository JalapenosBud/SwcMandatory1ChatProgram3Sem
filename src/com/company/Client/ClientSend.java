package com.company.Client;

import com.company.Utilities.ColorCoder;

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
    public synchronized void run() {

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

            //for console write for user
            Scanner userEntry = new Scanner(System.in);

            //this is the response client gets from the server
            String response = "";

            //this is the message the client sends
            String message = "";

            while(true)
            {
                //first send join msg to server
                //if server sends j-ok back then go into another if statement
                if(!connectionEstablished || (!connectionEstablished && response.contains("J_ERR")))
                {
                    System.out.println("please enter your username:");
                    String name = userEntry.nextLine();
                    client = new Client(name,STRIPTHEFUCKINGSLASHOFFMYIPADDRESS(socket),socket.getPort());

                    //sent join msg with new compiled client
                    networkOutput.println(client.sendJOIN());

                    response = networkInput.nextLine();

                    if(response.contains("J_OK") && !connectionEstablished)
                    {
                        System.out.println("Server accepted connection.");
                        connectionEstablished = true;
                    }
                    //get response message protocol J_ERR
                    if(response.contains("J_ERR") && !connectionEstablished)
                    {
                        System.out.println("username already exists, try another");
                        connectionEstablished = false;
                    }
                }
                if(connectionEstablished && message.contains("J_OK"))
                {
                    //TODO: LAV en command class der har J_OK, J_ERR, DATA, IMAV
                    //TODO: formatér streng der bliver sendt afsted til at ændre = JOIN TIL DATA
                    //TODO: og i threadhandler; læs [0] og hvis DATA er sendt, så læs beskeden
                    //System.out.print(ColorCoder.ANSI_CYAN + "> ");
                    message = userEntry.nextLine();
                }
            }
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
