package com.company.Server;

import com.company.Client.Client;
import com.company.Utilities.StringUtilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static com.company.Utilities.ClientUtilities.returnNewClient;
import static com.company.Utilities.StringUtilities.inputDataOutputMessage;
import static com.company.Utilities.StringUtilities.splitFirst;
import static com.company.Utilities.StringUtilities.splitJoinProtocol;

public class ClientHandler extends Thread {

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
    
    @Override
    public void run() {
        do {
            incoming = input.nextLine();
            String saveString = incoming;
            
            switch (splitFirst(incoming))
            {
                case "JOIN":
                    System.out.println("received a join message");
                    checkIfUserJoins(saveString);
                    break;
                case "DATA":
                    String[] tmpInfo = StringUtilities.splitDataProtocol(incoming);
                    //TODO: fix and input data protocol
                    output.println(inputDataOutputMessage(incoming));
                    System.out.println("got a data message: " + tmpInfo[2]);
                    break;
                    
                    default:
                        System.out.print("NO DATA");
            }
        }while (!incoming.equals("**QUIT**"));
        
        try {
            if (socket != null) {
                System.out.println("Closing down connection…");
                socket.close();
            }
        } catch (IOException ioEx) {
            System.out.println("Unable to disconnect!");
        }
    }
    
    private void tryToAddUser(String received)
    {
        Client tmpClient = null;
        try {
            tmpClient = returnNewClient(received);
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            ClientListManager.getInstance().addToList(tmpClient);
            System.out.println(tmpClient.getName() + " was added to the server");
        
            output.println("J_OK");
            System.out.println("J_OK sent");
        }
    }
    
    private void checkIfUserJoins(String incoming)
    {
        String[] tmpInfo = splitJoinProtocol(incoming);
        System.out.println("name is : " + tmpInfo[1] + " before checking JOIN message");
        
        if(ClientListManager.getInstance().getSize() == 0)
        {
            System.out.println("size is in == 0" + ClientListManager.getInstance().getSize());
            tryToAddUser(incoming);
            
        }
        //if there already are people on the server
        else if(ClientListManager.getInstance().getSize() > 0)
        {
            //TODO: error happens cause it reaches and element that isnt equal the one incoming... lol..
            //loop through
            System.out.println("size is " + ClientListManager.getInstance().getSize() + " in > 0");
            System.out.println("current incoming client name is: " + tmpInfo[1]);
            System.out.println("entering loop\n");
            for(int i = 0; i < ClientListManager.getInstance().getSize(); i++)
            {
                System.out.println("looping over: #" + i + ", "+ClientListManager.getInstance().getClient(i) + " client.\n");
                
                //get name of clients and check if exists
                //if user name exists
                if(tmpInfo[1].equals(ClientListManager.getInstance().getClient(i).getName()))
                {
                    System.out.println("sending J_ERR");
                    output.println("J_ERR");
                    //set to false and start loop over?
                    System.out.println(tmpInfo[1] + " already exists on server\n");
                    break;
                }
                else
                {
                    System.out.println("now adding: " + tmpInfo[1] + "\n");
                    tryToAddUser(incoming);
                    break;
                }
            }
            
        }
    }
}
