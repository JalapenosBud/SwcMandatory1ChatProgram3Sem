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

    private static InetAddress host;
    private static final int PORT = 1234;

    public Client()
    {
        startClient();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChatAlias() {
        return chatAlias;
    }

    public void setChatAlias(String chatAlias) {
        this.chatAlias = chatAlias;
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

    public Client(String name, int id)
    {
        this.chatAlias = name;
        this.id = id;
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
        try
        {
            host = InetAddress.getLocalHost();
        }
        catch(UnknownHostException uhEx)
        {
            System.out.println("\nHost ID not found!\n");
            System.exit(1);
        }
        sendMessages();
    }
    private static void sendMessages()
    {
        Socket socket = null;
        try
        {
            socket = new Socket(host,PORT);
            Scanner networkInput = new Scanner(socket.getInputStream());
            PrintWriter networkOutput = new PrintWriter(socket.getOutputStream(),true);

            Scanner userEntry = new Scanner(System.in);
            String message, response;
            do
            {
                System.out.print( "Enter message ('QUIT' to exit): ");
                message = userEntry.nextLine();
                networkOutput.println(message);
                response = networkInput.nextLine();

                System.out.println("\nSERVER> " + response);
            }while (!message.equals("QUIT"));
        }
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
        finally
        {
            try
            {
                System.out.println("\nClosing connection…");
                socket.close();
            }
            catch(IOException ioEx)
            {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    }


    @Override
    public String broadcastThis(Client client) {

        //ha et client object i parameter
        //i client class ha et id der bliver incrementet for hver gang en ny client bliver lavet i systemet
        //måske lav mere persistent på længere sigt, så en gammel client der bliver fjernet kan joine igen
        //altså hvis det er  1 2 3 og client 2 dc'er, så skal en ny client ikke ha hans id men
        //han kan komme tilbage som id 2 og fortsætte hva han lavede
        if(client.getId() == this.id)
            return "";
        return "\ni am " + chatAlias + ", my id is: " + id + "\nand " + client.toString() + " joined my room\n";
    }

    @Override
    public String toString() {
        return "client" + id;
    }
}
