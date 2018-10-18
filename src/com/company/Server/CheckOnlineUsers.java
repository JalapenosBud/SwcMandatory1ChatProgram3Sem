package com.company.Server;

import com.company.Client.Client;
import com.company.Utilities.Commands;

public class CheckOnlineUsers implements Runnable
{
    @Override
    public void run() {
    
        boolean countingTime = true;
        //tager tiden nu
        long test = System.currentTimeMillis();
    
        while(countingTime)
        {
            //hvis det tidspunkt nu minus der hvor vi startede er større end 2 sek
            //print out
            if(System.currentTimeMillis() - test > 10 * 1000)
            {
                String clientToRemove = "";
                
                for (Client c : ServerMain.clients)
                {
                    if(!c.isAmIAlive())
                    {
                        clientToRemove = c.getName();
                    }
                }
                ServerMain.removeAndUpdateList(clientToRemove);
                test = System.currentTimeMillis();
            }
        }
    }
}

