package com.company;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private List<Client> clients = new ArrayList<>();


    public void addInterestedClientsForBroadcast(Client client)
    {
        clients.add(client);
    }

    public void notifyAllClientsNewClientJoined()
    {
        for(Client cl : clients)
        {
            System.out.println(cl.broadcastThis(clients.get(clients.size()-1)));
        }
    }

    void heartbeatIsAlive(float timeToCheck)
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
                addInterestedClientsForBroadcast(new Client("Client", clients.size()-1));

                //System.out.println("2 seconds has passed");
                notifyAllClientsNewClientJoined();
                //reset timer
                test = System.currentTimeMillis();
                System.out.println("new connection established!");
                System.out.println("******\n");
            }

        }

    }
}
