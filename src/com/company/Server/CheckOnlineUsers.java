package com.company.Server;

import com.company.Client.Client;
import com.company.Utilities.Commands;

public class CheckOnlineUsers implements Runnable
{
    
    @Override
    public void run() {
    
        boolean countingTime = true;

        long test = System.currentTimeMillis();
        
        boolean removethisniggpls = false;
    
        while(countingTime)
        {
            if(System.currentTimeMillis() - test > 10 * 1000)
            {
                String clientToRemove = "";


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

