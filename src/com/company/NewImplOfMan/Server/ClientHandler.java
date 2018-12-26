package com.company.NewImplOfMan.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class ClientHandler extends Thread
{
    private Socket client;
    private Scanner input;
    private PrintWriter output;
    
    public ClientHandler(Socket socket)
    {
        client = socket;
        try
        {
            input = new Scanner(client.getInputStream());
            output = new PrintWriter(client.getOutputStream(), true);
        }
        catch (IOException ioEx)
        {
            System.out.println(ioEx.getMessage());
        }
    }
    
    public void run ()
    {
        String received;
        do
        {
            received = input.nextLine();
            if(received.equals("JOIN"))
            {
                output.println("ECHO: " + received);
            }
        } while (!received.equals("QUIT"));
        try
        {
            if (client != null)
            {
                System.out.println("Closing down connection…");
                client.close();
            }
        }
        catch (IOException ioEx)
        {
            System.out.println("Unable to disconnect!");
        }
    }
}
