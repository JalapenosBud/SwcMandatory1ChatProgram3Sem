package com.company.Server;

import com.company.Client.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ClientListManager {

    //TODO: look into making this completely thread safe..
    private static ClientListManager instance = null;

    private List<Client> clients = new ArrayList<>();

    public static ClientListManager getInstance()
    {
        if(instance == null)
        {
            instance = new ClientListManager();
        }
        return instance;
    }
    
    public void echoToAllClients(String message)
    {
        PrintWriter pw;
        
        if(clients.size() <= 1)
            return;
        
        try {
            for (Client c : clients)
            {
                //TODO: this throws nullpointer exception, are sockets null?
                pw = new PrintWriter(c.getSocket().getOutputStream(), true);
                pw.println(c.getName() + ": " + message);
                System.out.println("sending to: " + c.getName() + " listening on: " + c.getSocket());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public List<Client> getClients()
    {
        return clients;
    }
    
    public String showAllClients()
    {
        String tmp = "";
        for (Client c : clients)
        {
            tmp += c.getName() + ", ";
        }
        return tmp;
    }

    public void addToList(Client client)
    {
        clients.add(client);
    }

    public int getSize()
    {
        return clients.size();
    }

    public Client getClient(int i)
    {
        return clients.get(i);
    }

}
