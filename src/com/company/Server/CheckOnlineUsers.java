package com.company.Server;

import com.company.Client.Client;

public class CheckOnlineUsers implements Runnable
{
    
    @Override
    public void run()
    {
        
        boolean countingTime = true;
        //tager tiden nu
        long test = System.currentTimeMillis();
        
        boolean removethisniggpls = false;
        
        while (countingTime)
        {
            if (System.currentTimeMillis() - test > 10 * 1000)
            {
                String clientToRemove = "";
                
                for (Client c : ServerMain.clientArrayList)
                {
                    if (!c.isAmIAlive())
                    {
                        clientToRemove = c.getName();
                        removethisniggpls = true;
                    }
                    else
                    {
                        System.out.println(c.getName() + " is alive FROM CHECKONLINEUSERS CLASS");
                    }
                }
                if (removethisniggpls)
                {
                    System.out.println("removing " + clientToRemove);
                    ServerMain.removeClientAndUpdateClientList(clientToRemove);
                    removethisniggpls = false;
                }
                
                test = System.currentTimeMillis();
            }
        }
    }
}

