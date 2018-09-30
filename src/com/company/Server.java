package com.company;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private List<Observable> clients = new ArrayList<>();


    public void addInterestedClientsForBroadcast(Observable client)
    {
        clients.add(client);
    }
}
