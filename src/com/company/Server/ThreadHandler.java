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
        String received;

        //------incoming connection---------
        System.out.println("waiting for client to connect");

        while(programAlive)
        {
            if(!hasClientConnected)
            {
                //client msg
                received = input.nextLine();

                String[] tmpInfo = splitOnCrocs(received);

                switch (tmpInfo[0])
                {
                    case "JOIN":
                    {
                        //if there already are people on the server
                        if(ClientListManager.getInstance().clients.size() > 0)
                        {
                            //loop through
                            for(int i = 0; i < ClientListManager.getInstance().clients.size(); i++)
                            {
                                System.out.println("looping over: #" + ClientListManager.getInstance().clients.get(i) + " client.");
                                //get name of clients and check if exists
                                //if user name exists
                                //TODO: check j err in before connection accepted in client
                                if(!ClientListManager.getInstance().clients.get(i).getName().equals(tmpInfo[1]))
                                {

                                    //create temporary client
                                    Client tmpClient = null;
                                    try {
                                        tmpClient = returnNewClient(received);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    //addto list
                                    ClientListManager.getInstance().clients.add(tmpClient);

                                    System.out.println(tmpClient.getName() + " was added to the server");
                                    //set boolean to true cause now we want to withhold a connection
                                    hasClientConnected = true;
                                }
                                else
                                {
                                    //set to false and start loop over?
                                    System.out.println(tmpInfo[1] + " already exists on server");
                                    hasClientConnected = false;
                                }
                            }
                        }
                        else if(ClientListManager.getInstance().clients.size() == 0)
                        {
                            Client tmpClient = null;
                            try {
                                tmpClient = returnNewClient(received);
                                ClientListManager.getInstance().clients.add(tmpClient);

                                System.out.println(tmpClient.getName() + " was added");
                                hasClientConnected = true;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        //output.println("J_OK you have joined");
                        if(hasClientConnected)
                        {
                            output.println("J_OK");
                        }
                        else
                        {
                            output.println("J_ERR");
                        }

                    }
                }
            }
            if(hasClientConnected)
            {
                received = input.nextLine();
                System.out.print(">" + received);

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
