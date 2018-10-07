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
    List<Client> clients = new ArrayList<>();

    private PrintWriter output;

    private Socket client;
    private Scanner input;
    boolean hasAuthenticated = false;

    public ThreadHandler(Socket socket, List<Client> clients) {
        //Set up reference to associated socket…
        client = socket;
        this.clients = clients;
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
        do {
            //client msg
            received = input.nextLine();

            try {
                Client tmpClient = returnNewClient(received);
                //if msg = join and list is not 0
                if(received.contains("JOIN") && clients.size() > 0)
                {
                    System.out.println(clients.size() + " exists on server");
                    for(int i = 0; i < clients.size(); i++)
                    {
                        if(clients.get(i).getName().equals(tmpClient.getName()))
                        {
                            output.println("J_ERR" + " sorry, " + tmpClient.getName() + " already exists, try again");
                            output.println();
                            hasClientConnected = false;
                        }
                        else
                        {

                            output.println("J_OK");
                            clients.add(tmpClient);
                            System.out.println(tmpClient.getName() + " was added to the server");
                            hasClientConnected = true;
                        }
                    }
                } else if(received.contains("JOIN") && clients.size() == 0)
                {
                    //send msg back
                    output.println("J_OK");
                    //add client
                    System.out.println("server is empty");
                    clients.add(tmpClient);

                    System.out.println(tmpClient.getName() + " was added to the server");
                    hasClientConnected = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while(!hasClientConnected);
        //------incoming connection---------

        //----------when client has connected--------
        do {
            received = input.nextLine();
            
            System.out.println("message received: " + received);

            output.println("ECHO: " + received);
            
        } while (!received.equals("QUIT") && hasClientConnected);
        //----------when client has connected--------

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
        String[] tmpArr = splitOnCrocs(stringFromClient);
        Socket tmpSocket = new Socket(tmpArr[2],1234);
        return new Client(tmpArr[1], STRIPTHEFUCKINGSLASHOFFMYIPADDRESS(tmpSocket),Integer.parseInt(tmpArr[3]));
    }




}
