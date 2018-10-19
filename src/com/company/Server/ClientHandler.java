package com.company.Server;

import com.company.Client.Client;
import com.company.Utilities.ColorCoder;
import com.company.Utilities.StringUtilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.company.Utilities.ClientUtilities.returnNewClient;
import static com.company.Utilities.StringUtilities.inputDataOutputMessage;
import static com.company.Utilities.StringUtilities.splitFirst;
import static com.company.Utilities.StringUtilities.splitJoinProtocol;

public class ClientHandler extends Thread {
    
    private Socket clientSocket;
    private Scanner input;
    private PrintWriter output;
    
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
    
    public void run()
    {
        //string to pass incoming string to from the clien'ts input stream, ie what gets sent to us
        String received;
        try{
            do {
                //here we do that^
                received = input.nextLine();
    
                if(received != null)
                {
                    switch (splitFirst(received))
                    {
                        case "JOIN":
                            System.out.println("received a join message");
                            try {
                                checkIfUserJoins(received,clientSocket);
    
                                sendToAllUsers(showAllClients());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "DATA":
                            String[] tmpInfo = StringUtilities.splitDataProtocol(received);
                            if(tmpInfo[2].contains("LIST"))
                            {
                                sendToAllUsers(showAllClients());
                                break;
                            }
                            else if(tmpInfo[2].contains("QUIT"))
                            {
                                
                                try {
                                    System.out.println("user wants to quit, dc'ing them..");
                                    System.out.println("list size before: " + ServerMain.clients.size());
                                    ServerMain.removeAndUpdateList(userName);
    
                                    System.out.println("list size now: " + ServerMain.clients.size() + " user " + userName +" has been removed.");
    
                                    sendToAllUsers(showAllClients());
                                    clientSocket.close();
                                    break;
                                    
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                               
                            }else
                            {
                                String colorName = ColorCoder.ANSI_RED + tmpInfo[1];
                                String colorMessage = ColorCoder.ANSI_BLACK + tmpInfo[2];
                                sendToAllUsers(colorName + ": " + colorMessage);
                            }
                            break;
    
                        case "IMAV":
        
                            String[] duoArr = received.split(" ");
                            System.out.println(duoArr[1] + " is alive");
                            //received = "";
                            break;
                    }
                }
            //repeat untill QUIT is received
            }while(!received.equals("QUIT"));
            
        }catch (NoSuchElementException noele)
        {
            System.out.println("Noone's typing");
        }
        try{
            //its only possible to terminate a connection if the client exists
            if(clientSocket != null)
            {
                ServerMain.removeAndUpdateList(userName);
                sendToAllUsers(showAllClients());
                System.out.println("Closing down connection");
                clientSocket.close();
            }
        }catch (IOException ioex)
        {
            System.out.println("Unable to disconnect");
        }
    }
    
    public String showAllClients()
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
    
    private void checkIfUserJoins(String incoming, Socket socket) throws IOException {
        String[] tmpInfo = splitJoinProtocol(incoming);
        System.out.println("name is : " + tmpInfo[1] + " before checking JOIN message");
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
    }
    
    private void addClientToList(Socket socket)
    {
        Client tmpClient = new Client(userName,socket,true);
        ServerMain.clients.add(tmpClient);
    }
}
