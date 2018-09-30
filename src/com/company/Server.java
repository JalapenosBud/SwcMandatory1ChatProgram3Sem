package com.company;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private List<Observable> clients = new ArrayList<>();


    public void addInterestedClientsForBroadcast(Observable client)
    {
        clients.add(client);
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
                addInterestedClientsForBroadcast(new Client());
                for(Observable cl : clients)
                {
                    System.out.println(cl.broadcastThis());
                }
                System.out.println("2 seconds has passed");
                test = System.currentTimeMillis();
            }

        }

    }
}
