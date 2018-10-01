package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Client implements Observable{

    public Socket mySocket;

    public Client()
    {
        startClient();
    }

    public int getId() {
        return id;
    }

    //id for new client
    private int id = 0;

    //user name for client
    private String chatAlias;

    //client's port num
    private int portNum;

    //clients ipAddress
    private InetAddress ipAddress;


    //denne her bliver sat til true når forbindelse til serveren er oprettet
    //til false når der dc'es
    //noget condition her**
    private boolean isAlive;

    public Client(String name, int id, Socket socket)
    {
        this.chatAlias = name;
        this.id = id;
        mySocket = socket;
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
            if(System.currentTimeMillis() - test > timeToCheck)
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


    @Override
    public void broadcastThis(Client client) {

        //ha et client object i parameter
        //i client class ha et id der bliver incrementet for hver gang en ny client bliver lavet i systemet
        //måske lav mere persistent på længere sigt, så en gammel client der bliver fjernet kan joine igen
        //altså hvis det er  1 2 3 og client 2 dc'er, så skal en ny client ikke ha hans id men
        //han kan komme tilbage som id 2 og fortsætte hva han lavede

        System.out.println("\ni am " + chatAlias + ", my id is: " + id + "\nand " + client.toString() + " joined my room\n");
    }

    @Override
    public String toString() {
        return chatAlias + id;
    }

}
