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
        
        boolean removethisniggpls = false;
    
        while(countingTime)
        {
            //hvis det tidspunkt nu minus der hvor vi startede er større end 2 sek
            //print out
            if(System.currentTimeMillis() - test > 10 * 1000)
            {
                String clientToRemove = "";
                
                //TODO: im tired... this isnt what it should do..
                //TODO:.. it should calculate time and time stamps, not just check a boolean that is never flipped.. goodnight
                for (Client c : ServerMain.clients)
                {
                    if(!c.isAmIAlive())
                    {
                        clientToRemove = c.getName();
                        removethisniggpls = true;
                    }
                    else
                    {
                        System.out.println(c.getName() + " is alive FROM CHECKONLINEUSERS CLASS");
                    }
                }
                if(removethisniggpls)
                {
                    System.out.println("removing " + clientToRemove);
                    ServerMain.removeAndUpdateList(clientToRemove);
                    removethisniggpls = false;
                }
                
                test = System.currentTimeMillis();
            }
        }
    }
}

