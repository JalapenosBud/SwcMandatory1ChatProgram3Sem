package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientSend implements Runnable {

    private static InetAddress host;
    private static final int PORT = 1234;

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

            Scanner userEntry = new Scanner(System.in);
            String message;
            do
            {
                System.out.print( "Enter message ('QUIT' to exit): ");
                message = userEntry.nextLine();
                networkOutput.println(message);

            }while (!message.equals("QUIT"));
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
