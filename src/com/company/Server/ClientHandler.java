package com.company.Server;

import com.company.Client.Client;
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

public class ClientHandler implements Runnable {

private Scanner input;
private PrintWriter output;
    
    String incoming;
    
    Socket socket;

    public ClientHandler(Socket socket)
    {
        this.socket = socket;

        try{

            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (IOException ioEx)
        {
            ioEx.printStackTrace();
        }
    }
    
    private void sendToAllUsers(String message)
    {
        if(ClientListManager.getInstance().getSize() <= 1)
            return;
    
        try {
            for (Client c : ClientListManager.getInstance().getClients())
            {
                output = new PrintWriter(c.getSocket().getOutputStream(), true);
                output.println(c.getName() + ": " + message);
    
                System.out.println("sending to: " + c.getName() + " listening on: " + c.getSocket());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        do {
            //string to check what protocol is incoming
            try{
                incoming = input.nextLine();
                //saving the full string for further usage, ie splitting up the message into parts, for specific user name or message element
                
            }
            catch (NoSuchElementException ioe)
            {
                System.out.println(ioe);
            }
            String saveString = incoming;
            
            switch (splitFirst(incoming))
            {
                case "JOIN":
                    System.out.println("received a join message");
                    try {
                        checkIfUserJoins(saveString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "DATA":
                    String[] tmpInfo = StringUtilities.splitDataProtocol(incoming);
                    sendToAllUsers(tmpInfo[2]);
                    
                case "LIST":
                    output.println(ClientListManager.getInstance().showAllClients());
                    break;
            }
        }while (!incoming.equals("CLOSE_SERVER"));
        
        try {
            if (socket != null) {
                System.out.println("Closing down connection…");
                socket.close();
            }
        } catch (IOException ioEx) {
            System.out.println("Unable to disconnect!");
        }
    }
    
    private void checkIfUserJoins(String incoming) throws IOException {
        String[] tmpInfo = splitJoinProtocol(incoming);
        System.out.println("name is : " + tmpInfo[1] + " before checking JOIN message");
        
        Client tmpClient = null;
        
        if(ClientListManager.getInstance().getSize() == 0)
        {
            System.out.println("size is in == 0" + ClientListManager.getInstance().getSize());
            tmpClient = returnNewClient(incoming);
            
            ClientListManager.getInstance().addToList(tmpClient);
            System.out.println("\nNow added" + tmpClient.getName());
            //tryToAddUser(incoming);
            output.println("J_OK");
            System.out.println("J_OK sent");
            
        }
        //if there already are people on the server
        else if(ClientListManager.getInstance().getSize() > 0)
        {
            tmpClient = returnNewClient(incoming);
            
            for(int i = 0; i < ClientListManager.getInstance().getSize(); i++)
            {
                //get name of clients and check if exists
                //if user name exists
                if(ClientListManager.getInstance().getClient(i).getName().equalsIgnoreCase(tmpClient.getName()))
                {
                    output.println("J_ERR");
                    //set to false and start loop over?
                    System.out.println(tmpClient.getName() + " already exists on server\n");
                    break;
                }
            }
            ClientListManager.getInstance().addToList(tmpClient);
            output.println("J_OK");
        }
    }
}
