package com.company.Server;

import com.company.Client.Client;
import com.company.Utilities.StringUtilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static com.company.Utilities.ClientUtilities.returnNewClient;
import static com.company.Utilities.StringUtilities.inputDataOutputMessage;
import static com.company.Utilities.StringUtilities.splitJoinProtocol;

public class MessageReceiver extends Thread {

    String incoming;
    boolean hasClientConnected = false;

    private Scanner input;
    private PrintWriter output;

    Socket client;

    public MessageReceiver(Socket client, Scanner input, PrintWriter output)
    {
        this.client = client;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
<<<<<<< HEAD
=======

        do {
            incoming = input.nextLine();
            //if not JOIN msg received
            if(!incoming.contains("JOIN")) {
                System.out.println("Waiting for client to connect...");
                //output.println("enter username...");
            }
            else if(incoming.contains("JOIN"))
            {
                checkIfUserJoins(incoming);
            }
            else if(incoming.contains("DATA"))
            {
                String[] tmpInfo = StringUtilities.splitDataProtocol(incoming);
                //TODO: this will never execute cause tmpinfo isnt updated since the client has joined
                //TODO: it needs to read and break up the new message
                switch (tmpInfo[0])
                {
                    case "DATA":
                        //TODO: print out to all other uses
                        output.println(inputDataOutputMessage(incoming));
                        // break;
                }
            }
        }while (!incoming.equals("**QUIT**"));
>>>>>>> 765d7b34e195d8f66d97254acab5c63681fb497f

        try {
            if (client != null) {
                System.out.println("Closing down connection…");
                client.close();
            }
        } catch (IOException ioEx) {
            System.out.println("Unable to disconnect!");
        }
    }

    private void checkIfUserJoins(String received)
    {
        hasClientConnected = false;
        while(!hasClientConnected)
        {
            String[] tmpInfo = splitJoinProtocol(received);
            System.out.println("name is : " + tmpInfo[1] + " before checking JOIN message");

            switch (tmpInfo[0])
            {
                case "JOIN":
                {
                    if(ClientListManager.getInstance().getSize() == 0)
                    {

                        System.out.println("size is in == 0" + ClientListManager.getInstance().getSize());
                        Client tmpClient = null;
                        try {
                            tmpClient = returnNewClient(received);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ClientListManager.getInstance().addToList(tmpClient);

                        System.out.println(tmpClient.getName() + " was added");
                        output.println("J_OK");

                        System.out.println("J_OK sent");
                        break;

                    }
                    //if there already are people on the server
                    else if(ClientListManager.getInstance().getSize() > 0)
                    {
                        //loop through
                        for(int i = 0; i < ClientListManager.getInstance().getSize(); i++)
                        {
                            System.out.println("looping over: #" + i + ", "+ClientListManager.getInstance().getClient(i) + " client.");
                            System.out.println("size is " + ClientListManager.getInstance().getSize() + " in > 0");
                            System.out.println("current incoming client name is: " + tmpInfo[1]);

                            //get name of clients and check if exists
                            //if user name exists
                            //TODO: client index i new client is assigned before tmpInfo[1] is checked??
                            if(tmpInfo[1].equals(ClientListManager.getInstance().getClient(i).getName()))
                            {

                                output.println("J_ERR");
                                //set to false and start loop over?
                                System.out.println(tmpInfo[1] + " already exists on server");

                            }
                            else
                            {
                                //create temporary client
                                Client tmpClient = null;
                                try {
                                    tmpClient = returnNewClient(received);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                //addto list
                                ClientListManager.getInstance().addToList(tmpClient);
                                System.out.println(tmpClient.getName() + " was added to the server");
                                //set boolean to true cause now we want to withhold a connection
                                break;
                            }
                        }
                    }
                }
            }
            hasClientConnected = true;
<<<<<<< HEAD

            try {
                if (client == null) {
                    System.out.println("Closing down connection…");
                    client.close();
                }
            } catch (IOException ioEx) {
                System.out.println("Unable to disconnect!");
            }
=======
>>>>>>> 765d7b34e195d8f66d97254acab5c63681fb497f
        }
    }

}
