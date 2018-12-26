package com.company.NewImplOfMan.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ClientHandler extends Thread
{
    private Socket client;
    private Scanner input;
    private PrintWriter output;
    
    Broadcaster broadcaster = new Broadcaster();
    
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
            
            String regex=",|/|:|<<|>>";
            Matcher m = Pattern.compile(regex).matcher(received);
            received = m.replaceAll("");
            
            String[] msg = received.split(" ");
            if(msg[0].equals("JOIN"))
            {
                System.out.println(received);
                
                
                if(Server.clients.containsKey(msg[1]))
                {
                    output.println("J_ERR");
                }
                else{
                    Server.clients.put(msg[1],client);
                    output.println("J_OK");
                }
            }
            if(msg[0].equals("LIST"))
            {
                broadcaster.sendToAllUsers(output);
            }
            
        } while (!received.equals(""));
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
