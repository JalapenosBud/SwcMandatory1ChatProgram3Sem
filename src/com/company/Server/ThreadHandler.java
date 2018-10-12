package com.company.Server;

import com.company.Client.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static com.company.Utilities.StringUtilities.STRIPTHEFUCKINGSLASHOFFMYIPADDRESS;
import static com.company.Utilities.StringUtilities.splitDataProtocol;
import static com.company.Utilities.StringUtilities.splitJoinProtocol;


public class ThreadHandler extends Thread{

    String[] clientInfo = new String[3];
    //List<Client> clients = new ArrayList<>();

    private PrintWriter output;

    private Socket client;
    private Scanner input;

    boolean programAlive = true;

    public ThreadHandler(Socket socket) {
        //Set up reference to associated socket…
        client = socket;
        //this.clients = clients;
        try {
            input = new Scanner(client.getInputStream());
            output = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    public void run ()
    {
        boolean hasClientConnected = false;
        String received = "";

        //------incoming connection---------
        System.out.println("waiting for client to connect");



                //client msg
                received = input.nextLine();

                String[] tmpInfo = splitJoinProtocol(received);
                System.out.println("name is : " + tmpInfo[1] + " before checking JOIN message");

                while(!hasClientConnected)
                {
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

                                hasClientConnected = true;

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
                                        hasClientConnected = true;
                                        System.out.println(tmpClient.getName() + " was added to the server");
                                        //set boolean to true cause now we want to withhold a connection
                                        break;
                                    }
                                }
                            }

                        }
                    }

                }


            if(hasClientConnected)
            {
                System.out.println("waiting for message...");

                received = input.nextLine();
                System.out.print(received);

                while(!received.equals("**QUIT**"))
                {
                    switch (tmpInfo[0])
                    {
                        case "DATA":
                            //TODO: print out to all other uses
                            output.println(inputDataOutputMessage(received));
                            break;
                    }


                }
            }


        try {
            if (client != null) {
                System.out.println("Closing down connection…");
                client.close();
            }
        } catch (IOException ioEx) {
            System.out.println("Unable to disconnect!");
        }
    }
    //----------when client has connected--------

    /**
     * this method gets data protocol and outputs message user writes
     * @param input
     * @return
     */
    public String inputDataOutputMessage(String input)
    {
        String[] tmpArr = splitDataProtocol(input);
        //TODO: split incoming data up and use message element only
        //TODO: is done?
        return tmpArr[2];
    }

    public Client returnNewClient(String stringFromClient) throws IOException {
        /**
         * 0, = JOIN MSG
         * 1, = USERNAME
         * 2, = IP ADDRESS
         * 3, = PORT
         */
        String[] tmpArr = splitJoinProtocol(stringFromClient);
        //pass in ip address
        Socket tmpSocket = new Socket(tmpArr[2],1234);
        return new Client(tmpArr[1], STRIPTHEFUCKINGSLASHOFFMYIPADDRESS(tmpSocket),Integer.parseInt(tmpArr[3]));
    }




}
