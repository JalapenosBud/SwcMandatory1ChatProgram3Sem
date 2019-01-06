package com.company.Server;

import com.company.Client.Client;
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

    private Socket clientSocket;
    private Scanner input;
    PrintWriter output = null;
    private String userName = "";

    public ClientHandler(Socket socket)
    {
        clientSocket = socket;
        try{
            input = new Scanner(clientSocket.getInputStream());
            output = new PrintWriter(clientSocket.getOutputStream(),true);
        }
        catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
    }

    private void checkJoin(String message)
    {
        String[] tmpInfo = message.split(" ");

        String regex="/|:|^<<|>>$";
        Matcher m = Pattern.compile(regex).matcher(message);
        message = m.replaceAll(" ");
        System.out.println(message);

        if(ServerMain.clientArrayList.size() == 0)
        {
            addClientToList(clientSocket);
            output.println("J_OK");
            System.out.println("J_OK sent\nUser " + userName + " joined.");
        }
        else
        {
            for(int i = 0; i < ServerMain.clientArrayList.size(); i++)
            {
                if(tmpInfo[1].equals(ServerMain.clientArrayList.get(i).getName()))
                {
                    output.println("J_ERR");
                }
                else {
                    output.println("J_OK");
                    break;
                }
            }
            addClientToList(clientSocket);
            System.out.println("J_OK sent\nUser " + userName + " joined.");
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
                ServerMain.removeClientAndUpdateClientList(userName);
                broadcaster.sendToAllUsers(output);
                clientSocket.close();

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
            if(clientSocket != null)
            {
                ServerMain.removeClientAndUpdateClientList(userName);
                broadcaster.sendToAllUsers(output);
                System.out.println("Closing down connection");
                clientSocket.close();
            }
        }
        catch (IOException ioex)
        {
            System.out.println("Unable to disconnect");
        }
    }

    private void addClientToList(Socket socket)
    {
        Client tmpClient = new Client(userName,socket,true);
        ServerMain.clientArrayList.add(tmpClient);
    }
}
