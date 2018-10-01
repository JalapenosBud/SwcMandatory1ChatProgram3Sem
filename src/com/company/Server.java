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

    public List<Client> getClients() {
        return clients;
    }

    //gets length of list - 1
    public int getNumberOfLastAddedClient()
    {
        return clients.size() - 1;
    }

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

}
