package com.company.Server;

import com.company.Client.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientListManager {

    private static ClientListManager instance = null;

    public List<Client> clients = new ArrayList<>();

    public static ClientListManager getInstance()
    {
        if(instance == null)
        {
            instance = new ClientListManager();
        }
        return instance;
    }


}
