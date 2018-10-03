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

    public void run ()
    {
        boolean hasClientConnected = false;
        String received;
        
        do {
            
            received = input.nextLine();
            
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
    
    
    //to receive user string and strip it off << and >>
    //then save each value in an array
    //glemte selve beskeden hehe
    public static String[] splitOnCrocs(String info)
    {
        //temporary array to store user name
        String[] tempInfo;
        
        tempInfo = info.split(Pattern.quote(" "));
        
        tempInfo = info.split(Pattern.quote(","));
        
        String[] newTmp;
        
        newTmp = tempInfo[1].split(Pattern.quote(":"));
        
        String[] finalTemp = {tempInfo[0],newTmp[0],newTmp[1]};
        
        String tmp = "";
        for (int i = 0; i < finalTemp.length; i++) {
            tmp = finalTemp[i].replaceAll("^<<|>>$","");
            finalTemp[i] = tmp;
        }
        
        return  finalTemp;
    }

}
