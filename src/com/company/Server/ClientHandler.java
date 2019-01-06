package com.company.Server;

import com.company.Utilities.Broadcaster;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClientHandler extends Thread {

    Broadcaster broadcaster = new Broadcaster();

    private Socket client;
    private Scanner input;
    PrintWriter output = null;
    private String userName = "";

    public ClientHandler(Socket socket)
    {
        client = socket;
        try{
            input = new Scanner(client.getInputStream());
            output = new PrintWriter(client.getOutputStream(),true);
        }
        catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
    }

    private void checkJoin(String message)
    {
        String[] msg = message.split(" ");

        String regex="/|:|^<<|>>$";
        Matcher m = Pattern.compile(regex).matcher(message);
        message = m.replaceAll(" ");
        System.out.println(message);

        if(Server.clients.containsKey(msg[1]))
        {
            output.println("J_ERR");
        }
        else {
            Server.clients.put(msg[1],client);
            output.println("J_OK");
        }
        broadcaster.sendToAllUsers(output);
    }

    private void checkData(String message)
    {
        String[] tmpInfo = message.split(" ");
        if(tmpInfo[0].contains("LIST"))
        {
            broadcaster.sendToAllUsers(output);
        }
        else if(tmpInfo[1].contains("QUIT"))
        {
            try {
                broadcaster.sendToAllUsers(output);
                client.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            broadcaster.sendToAllUsers(output);
        }
    }

    public void run()
    {
        String received;
        try{

            do {
                received = input.nextLine();

                String[] message = received.split(" " ,2);

                if(message[0].equals("JOIN"))
                {
                    checkJoin(received);

                }
                else if(message[0].equals("DATA"))
                {
                    checkData(received);


                }
                else if(message[0].equals("IMAV"))
                {

                }

            }while(received != null);

        }catch (NoSuchElementException noele)
        {
            System.out.println("Noone's typing");
        }
        try
        {
            if(client != null)
            {
                broadcaster.sendToAllUsers(output);
                System.out.println("Closing down connection");
                client.close();
            }
        }
        catch (IOException ioex)
        {
            System.out.println("Unable to disconnect");
        }
    }
}
