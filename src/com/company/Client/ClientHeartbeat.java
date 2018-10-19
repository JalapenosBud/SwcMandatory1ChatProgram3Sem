package com.company.Client;

import com.company.Utilities.Commands;

import java.io.PrintWriter;

public class ClientHeartbeat implements Runnable {
    
    PrintWriter printWriter;
    String name;
    
    public ClientHeartbeat(PrintWriter pw, String name)
    {
        this.printWriter = pw;
        this.name = name;
    }
    
    @Override
    public void run()
    {
        heartbeatIsAlive(printWriter,8,name);
    }
    
    private void heartbeatIsAlive(PrintWriter pw, float timeToCheck, String name)
    {
        //til at køre loop
        boolean countingTime = true;
        //tager tiden nu
        long test = System.currentTimeMillis();
        
        while(countingTime)
        {
            //hvis det tidspunkt nu minus der hvor vi startede er større end 2 sek
            //print out
            if(System.currentTimeMillis() - test > timeToCheck * 1000)
            {
                //System.out.println("heartbeat");
                pw.println(Commands.send_IMAV(name));
                test = System.currentTimeMillis();
                //countingTime = false;
            }
        }
    }
}
