package com.company.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Server extends Thread{

    String[] clientInfo = new String[3];
    
    private Socket client;
    private Scanner input;

    private PrintWriter output;

    public Server(Socket socket) {
        //Set up reference to associated socket…
        client = socket;
        try {
            input = new Scanner(client.getInputStream());
            output = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
    
    public void splitOnCrocs(String info)
    {
        String[] tempInfo = new String[3];
        tempInfo = info.split(Pattern.quote(","));
    }

    public void run ()
    {
        boolean hasClientConnected = false;
        String received;
        
        do {
            
            received = input.nextLine();
            clientInfo = received.split(Pattern.quote("<<"));
            if(received.contains("JOIN")) hasClientConnected = true;
            
        }while(!received.contains("JOIN") && !hasClientConnected);
        
        
        do {
            //output.println("Hello" + );
            received = input.nextLine();
            
            System.out.println("message received: " + received);

            output.println("ECHO: " + received);
            
        } while (!received.equals("QUIT") && hasClientConnected);
        try {
            if (client != null) {
                System.out.println("Closing down connection…");
                client.close();
            }
        } catch (IOException ioEx) {
            System.out.println("Unable to disconnect!");
        }
    }

}
