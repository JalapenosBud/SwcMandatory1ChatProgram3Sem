package com.company.Server;

import com.company.Client.Client;
import com.company.Utilities.ColorCoder;
import com.company.Utilities.StringUtilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.company.Utilities.StringUtilities.splitAndClean_DATA_ProtocolFromSymbols;
import static com.company.Utilities.StringUtilities.splitAndClean_JOIN_protocolFromSymbols;
import static com.company.Utilities.StringUtilities.splitAndReturnOnlyProtocolMsg;
import static com.company.Utilities.StringsForProtocols.*;

public class ClientHandler extends Thread
{
    
    private Socket clientSocket;
    private Scanner input;
    private PrintWriter output;
    
    private boolean addedClientToClientList = false;
    
    //cache temp user name here to later remove them from list
    private String userName = "";
    
    public ClientHandler(Socket socket)
    {
        //set this objects socket to whatever is incoming
        clientSocket = socket;
        try
        {
            input = new Scanner(clientSocket.getInputStream());
            output = new PrintWriter(clientSocket.getOutputStream(), true);
        }
        catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
    }
    
    public void run()
    {
        try
        {
            while (!addedClientToClientList)
            {
                String messageFromClient = input.nextLine();
                String[] tmpInfo = messageFromClient.split(" ", 2);
                if (tmpInfo[0].equals(JOIN))
                {
                    System.out.println("received: " + tmpInfo[0] + " from: " + tmpInfo[1]);
                    userName = splitAndClean_JOIN_protocolFromSymbols(messageFromClient)[1];
                    
                    Client tempClient = new Client(userName, clientSocket, true);
                    
                    if (ServerMain.clientArrayList.size() > 0)
                    {
                        if (doesUserExistInClientList(ServerMain.clientArrayList, userName))
                        {
                            output.println(J_ERR);
                            addedClientToClientList = false;
                        }
                        else if (!doesUserExistInClientList(ServerMain.clientArrayList, userName))
                        {
                            addNewClientToClientList(tempClient);
                            printListOfActiveUsersToClient(USER_JOINED);
                        }
                        
                    }
                    else if (ServerMain.clientArrayList.size() == 0)
                    {
                        addNewClientToClientList(tempClient);
                        printListOfActiveUsersToClient(USER_JOINED);
                    }
                }
            }
            while (addedClientToClientList)
            {
                String receivedMessageFromClient = input.nextLine();
                
                String splitOnDataAndImavProtocol = splitAndReturnOnlyProtocolMsg(receivedMessageFromClient);
                
                if (splitOnDataAndImavProtocol.equals(DATA))
                {
                    System.out.println("received DATA");
                    String[] theSplitMessageFromDataProtocol = StringUtilities.splitAndClean_DATA_ProtocolFromSymbols(receivedMessageFromClient);
                    
                    userName = theSplitMessageFromDataProtocol[1];
                    
                    if (theSplitMessageFromDataProtocol[2].contains(LIST))
                    {
                        printListOfActiveUsersToClient(USERS_ONLINE);
                    }
                    else if (theSplitMessageFromDataProtocol[2].contains(QUIT))
                    {
                        System.out.println("received QUIT");
                        try
                        {
                            ServerMain.removeClientAndUpdateClientList(userName);
                            
                            printListOfActiveUsersToClient(USER_LEFT);
                            
                            clientSocket.close();
                            
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        sendToAllUsers(ColorCoder.ANSI_BLUE + userName + "> " + ColorCoder.ANSI_BLACK + theSplitMessageFromDataProtocol[2],userName);
                    }
                }
                if (splitOnDataAndImavProtocol.equals(IMAV))
                {
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
                ServerMain.removeClientAndUpdateClientList(userName);
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
    
    private boolean doesUserExistInClientList(ArrayList<Client> clientArrayList, String usernameToCompare)
    {
        for (Client clientInClientList : clientArrayList)
        {
            if (clientInClientList.getName().equals(usernameToCompare))
            {
                return true;
            }
        }
        return false;
    }
    
    private void addNewClientToClientList(Client tempClient)
    {
        ServerMain.clientArrayList.add(tempClient);
        output.println(J_OK);
        System.out.println("J_OK sent\nUser " + userName + " joined.");
        addedClientToClientList = true;
    }
    
    private void printListOfActiveUsersToClient(String eventMessage)
    {
        String tmp = "";
        
        for (Client c : ServerMain.clientArrayList)
        {
            tmp += c.getName() + ", ";
        }
        
        for (Client c : ServerMain.clientArrayList)
        {
            try
            {
                output = new PrintWriter(c.getSocket().getOutputStream(),true);
                output.println(ColorCoder.ANSI_BLACK + eventMessage + ColorCoder.ANSI_PURPLE + "Now online: " + ColorCoder.ANSI_RED + tmp);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
    }
    
    private void sendToAllUsers(String msg, String thisClientUsername)
    {
        for (Client c : ServerMain.clientArrayList)
        {
            if(!c.getName().equals(thisClientUsername))
            {
                try
                {
                    output = new PrintWriter(c.getSocket().getOutputStream(), true);
        
                    output.println(msg);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            
            
           
        }
    }
}
