package com.company.Server;

import com.company.Client.Client;
import com.company.Utilities.ColorCoder;
import com.company.Utilities.StringUtilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class ClientHandler extends Thread {
    
    private Socket clientSocket;
    private Scanner input;

    private String userName = "";
    
    public ClientHandler(Socket socket)
    {
        PrintWriter output = null;

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

    private void checkJoin()
    {
        try
        {
            PrintWriter output = null;

            String[] tmpInfo = splitJoinProtocol(incoming);

            String[] splitArrayWhitespace = info.split(" ", 2);

            String[] splitArrayColon = splitArrayWhitespace[1].split(":");

            String message = splitArrayColon[1];

            String[] tempArray = {splitArrayWhitespace[0],splitArrayColon[0], message};

            String tmp = "";

            for (int i = 0; i < tempArray.length; i++) {

                tempArray[i] = tempArray[i].replaceAll("^<<|>>$", "");

            }

            userName = tmpInfo[1];


            if(ServerMain.clients.size() == 0)
            {
                addClientToList(socket);
                output.println("J_OK");
                System.out.println("J_OK sent\nUser " + userName + " joined.");
            }
            //if there already are people on the server
            else if(ServerMain.clients.size() > 0)
            {
                for(int i = 0; i < ServerMain.clients.size(); i++)
                {
                    //get name of clients and check if exists
                    //if user name exists
                    if(tmpInfo[1].equals(ServerMain.clients.get(i).getName()))
                    {
                        output.println("J_ERR");
                        System.out.println("JERR SENT");
                    }
                    else {
                        output.println("J_OK");
                        break;
                    }
                }
                addClientToList(socket);
                System.out.println("J_OK sent\nUser " + userName + " joined.");
            }

            sendToAllUsers();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkData()
    {
        String[] tmpInfo = StringUtilities.splitDataProtocol(received);
        if(tmpInfo[2].contains("LIST"))
        {
            sendToAllUsers();
        }
        else if(tmpInfo[2].contains("QUIT"))
        {

            try {
                ServerMain.removeAndUpdateList(userName);

                System.out.println("list size now: " + ServerMain.clients.size() + " user " + userName +" has been removed.");

                sendToAllUsers();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else
        {
            String colorName = ColorCoder.ANSI_RED + tmpInfo[1];
            String colorMessage = ColorCoder.ANSI_BLACK + tmpInfo[2];
            sendToAllUsers();
        }

    }

    private void checkIMAV()
    {

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

                }
                else if(message[0].equals("DATA"))
                {

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
                ServerMain.removeAndUpdateList(userName);
                sendToAllUsers();
                System.out.println("Closing down connection");
                clientSocket.close();
            }
        }
        catch (IOException ioex)
        {
            System.out.println("Unable to disconnect");
        }
    }



    private void checkIfUserJoins(String incoming, Socket socket) throws IOException
    {

    }
    
    private void addClientToList(Socket socket)
    {
        Client tmpClient = new Client(userName,socket,true);
        ServerMain.clients.add(tmpClient);
    }
}
