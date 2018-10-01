package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{

    ServerSocket serverSocket = null;
    int port = 1234;
    static int clientNo = 0;
    Client tmpClient = null;
    String fromUser = "";

    private List<Client> clients = new ArrayList<>();


    public Server()
    {
        super("MySuperServer");
    }

    //TODO: refactor for low coupling
    public void addInterestedClientsForBroadcast(Client client)
    {
        clients.add(client);
    }

    //TODO: refactor for low coupling
    public void notifyAllClientsNewClientJoined()
    {
        for(Client cl : clients)
        {
            System.out.println(cl.broadcastThis(clients.get(clients.size()-1)));
        }
    }

    /*void heartbeatIsAlive(float timeToCheck)
    {
        //til at køre loop
        boolean countingTime  = true;
        //tager tiden nu
        long test = System.currentTimeMillis();

        while(countingTime)
        {
            //hvis det tidspunkt nu minus der hvor vi startede er større end 2 sek
            //print out
            if(System.currentTimeMillis() - test > (timeToCheck * 1000))
            {
                System.out.println("\n******");
                System.out.println("new connection inbound!");
                addInterestedClientsForBroadcast(new Client("Client", clients.size()));

                //System.out.println("2 seconds has passed");
                notifyAllClientsNewClientJoined();
                //reset timer
                test = System.currentTimeMillis();
                System.out.println("new connection established!");
                System.out.println("******\n");
            }

        }*/

}
