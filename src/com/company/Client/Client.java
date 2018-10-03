package com.company.Client;

import java.net.InetAddress;
import java.util.Scanner;

public class Client{

    String name;
    InetAddress ipAddress;
    int port;

    public Client()
    {
        startClient();
    }

    public Client(String name, InetAddress ipAddress, int port)
    {
        this.name = name;
        this.ipAddress = ipAddress;
        this.port = port;
    }
    
    public void enterUserName()
    {
        System.out.println("Please enter your username");
        Scanner input = new Scanner(System.in);

        name = input.nextLine();
        

    }

    public String sendJOIN()
    {
        return "JOIN " + name + ", <<" + ipAddress + ">>:<<" + port+">>";
    }


    //heartbeat isalive method every minute to the server
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
            if(System.currentTimeMillis() - test > timeToCheck * 1000)
            {
                System.out.println("2 seconds has passed");
                test = System.currentTimeMillis();
            }

        }

    }

    public void startClient()
    {
        ClientReceive clientReceive = new ClientReceive();
        ClientSend clientSend = new ClientSend();

        Thread t1 = new Thread(clientReceive);
        Thread t2 = new Thread(clientSend);

        t1.start();
        t2.start();
    }

}
