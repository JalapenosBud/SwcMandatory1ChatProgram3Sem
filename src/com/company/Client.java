package com.company;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Client implements Observable{

    //pæn sout med time, min, sec
    void test()
    {
        LocalDateTime.now();
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

    //lav en anden metode med dato også og fuld timestamp uden milisec
    void printWholeDate()
    {
        LocalDateTime.now();
    }

    //client's port num
    int portNum;

    //clients ipAddress
    InetAddress ipAddress;

    //user name for client
    String chatAlias;

    //denne her bliver sat til true når forbindelse til serveren er oprettet
    //til false når der dc'es
    //noget condition her**
    boolean isAlive;



    @Override
    public String broadcastThis() {

        //ha et client object i parameter
        //i client class ha et id der bliver incrementet for hver gang en ny client bliver lavet i systemet
        //måske lav mere persistent på længere sigt, så en gammel client der bliver fjernet kan joine igen
        //altså hvis det er  1 2 3 og client 2 dc'er, så skal en ny client ikke ha hans id men
        //han kan komme tilbage som id 2 og fortsætte hva han lavede
        return "i joined hello hehehe";
    }

}
