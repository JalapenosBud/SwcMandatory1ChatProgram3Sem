package com.company.Server;

import com.company.Client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import static com.company.Utilities.StringUtilities.STRIPTHEFUCKINGSLASHOFFMYIPADDRESS;
import static com.company.Utilities.StringUtilities.splitOnCrocs;


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

                String[] tmpInfo = splitOnCrocs(received);
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

                                System.out.println(tmpClient.getName() + " was added");
                                output.println("J_OK");
                                hasClientConnected = true;
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
                                        hasClientConnected = false;

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
                                        output.println("J_OK");
                                        hasClientConnected = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                }


            if(hasClientConnected)
            {
                while(!received.equals("**QUIT**"))
                {
                    received ="";
                    received = input.nextLine();
                    System.out.print(received);
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

    public Client returnNewClient(String stringFromClient) throws IOException {
        /**
         * 0, = JOIN MSG
         * 1, = USERNAME
         * 2, = IP ADDRESS
         * 3, = PORT
         */
        String[] tmpArr = splitOnCrocs(stringFromClient);
        //pass in ip address
        Socket tmpSocket = new Socket(tmpArr[2],1234);
        return new Client(tmpArr[1], STRIPTHEFUCKINGSLASHOFFMYIPADDRESS(tmpSocket),Integer.parseInt(tmpArr[3]));
    }




}
