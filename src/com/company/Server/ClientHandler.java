package com.company.Server;

import com.company.Client.Client;
import com.company.Utilities.StringUtilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.company.Utilities.StringUtilities.splitOnFirstArrayElement;

public class ClientHandler extends Thread {
    
    private Socket clientSocket;
    private Scanner input;
    private PrintWriter output;
    
    private boolean addedToClientList = false;
    
    //cache temp user name here to later remove them from list
    private String userName = "";
    
    public ClientHandler(Socket socket)
    {
        //set this objects socket to whatever is incoming
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
    
    private boolean doesUserExistInClientList(ArrayList<Client> thelist, String name)
    {
        for (Client notherName : thelist)
        {
            if(notherName.getName().equals(name))
            {
                return true;
            }
        }
        return false;
    }
    
    public void run()
    {
        try
        {
            while(!addedToClientList)
            {
                String received = input.nextLine();
                String[] tmpInfo = received.split(" ",2);
                if (tmpInfo[0].equals("JOIN"))
                {
                    System.out.println("received JOIN");
                    userName = tmpInfo[1];
    
                    System.out.println("name is : " + userName + " before checking JOIN message");
    
                    //if there already are people on the server
                    if(ServerMain.clients.size() > 0)
                    {
                        if(doesUserExistInClientList(ServerMain.clients,userName))
                        {
                            output.println("J_ERR");
                            addedToClientList = false;
                        }
                        else if(!doesUserExistInClientList(ServerMain.clients,userName))
                        {
                            Client tmpClient = new Client(userName,clientSocket,true);
                            ServerMain.clients.add(tmpClient);
                            output.println("J_OK");
                            System.out.println("J_OK sent\nUser " + userName + " joined.");
                            addedToClientList = true;
                        }
        
                    }
                    else if(ServerMain.clients.size() == 0)
                    {
                        Client tmpClient = new Client(userName,clientSocket,true);
                        ServerMain.clients.add(tmpClient);
                        output.println("J_OK");
                        System.out.println("J_OK sent\nUser " + userName + " joined.");
                        addedToClientList = true;
                    }
                }
            }
            while(addedToClientList)
            {
                String receivedMessageFromClient = input.nextLine();
                
                String splitOnDataAndImavProtocol = splitOnFirstArrayElement(receivedMessageFromClient);
                
                if (splitOnDataAndImavProtocol.equals("DATA")) {
                    
                    System.out.println("received DATA");
                    
                    String[] anothertmpInfo = StringUtilities.splitDataProtocol(receivedMessageFromClient);
                    
                    if (anothertmpInfo[2].contains("LIST")) {
                        sendToAllUsers(printListOfActiveUsersToClient());
                    }
                    else if (anothertmpInfo[2].contains("QUIT"))
                    {
                        System.out.println("received QUIT");
                        try {
                            System.out.println("user wants to quit, dc'ing them..");
                            System.out.println("list size before: " + ServerMain.clients.size());
                            ServerMain.removeAndUpdateList(userName);
            
                            System.out.println("list size now: " + ServerMain.clients.size() + " user " + userName + " has been removed.");
            
                            sendToAllUsers(printListOfActiveUsersToClient());
                            clientSocket.close();
            
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
        
                    }
                    else
                    {
                        sendToAllUsers(anothertmpInfo[2]);
                    }
                }
                if (splitOnDataAndImavProtocol.equals("IMAV"))
                {
                    System.out.println("received IMAV");
    
                    String[] duoArr = receivedMessageFromClient.split(" ");
                    System.out.println(duoArr[1] + " is alive");
                }
            }
        }
        catch (NoSuchElementException noele)
        {
            System.out.println("Noone's typing");
        }
    
        /*try
        {
        //its only possible to terminate a connection if the client exists
            if(clientSocket != null)
            {
                ServerMain.removeAndUpdateList(userName);
                sendToAllUsers(printListOfActiveUsersToClient());
                System.out.println("Closing down connection");
                clientSocket.close();
            }
        }
        catch (IOException ioex)
        {
            System.out.println("Unable to disconnect");
        }*/
    }
    
    public String printListOfActiveUsersToClient()
    {
        String tmp = "";
        for (Client c : ServerMain.clients)
        {
            tmp += c.getName() + ", ";
        }
        return tmp;
    }

    private void sendToAllUsers(String msg)
    {
        for (Client s :
                ServerMain.clients) {
            try {
                output = new PrintWriter(s.getSocket().getOutputStream(), true);
                output.println(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
