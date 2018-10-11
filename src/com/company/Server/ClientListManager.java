package com.company.Server;

import com.company.Client.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientListManager {

    private static ClientListManager instance = null;

    private List<Client> clients = new ArrayList<>();

    public synchronized static ClientListManager getInstance()
    {
        if(instance == null)
        {
            instance = new ClientListManager();
        }
        return instance;
    }

    public synchronized void addToList(Client client)
    {
        clients.add(client);
    }

    public synchronized int getSize()
    {
        return clients.size();
    }

    public synchronized Client getClient(int i)
    {
        return clients.get(i);
    }

}
