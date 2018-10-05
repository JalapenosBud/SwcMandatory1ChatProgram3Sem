package com.company.Server;

import com.company.Client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ThreadHandler extends Thread{

    String[] clientInfo = new String[3];
    List<Client> clients = new ArrayList<>();

    Map<Client,Socket> clientSocketMap;

    private Socket client;
    private Scanner input;

    private PrintWriter output;

    public ThreadHandler(Socket socket, Map<Client,Socket> clientSocketMap) {
        //Set up reference to associated socket…
        client = socket;
        this.clientSocketMap = clientSocketMap;
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



        //------incoming connection---------
        System.out.println("waiting for client to connect");
        do {
            received = input.nextLine();
            try {
                clientSocketMap.put(returnNewClient(received),new Socket(client.getInetAddress(),client.getPort()));
            }catch (IOException e) {
                e.printStackTrace();
            }
            if(received.contains("JOIN"))
            {
                output.println("J_OK");
                System.out.println("client: " + received);

                //clientSocketMap.put(new Client(splitOnCrocs(received)),client);
                hasClientConnected = true;
            }
            
        }while(!received.contains("JOIN") && !hasClientConnected);
        //------incoming connection---------

        //----------when client has connected--------
        do {
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
    //----------when client has connected--------
    
    //to receive user string and strip it off << and >>
    //then save each value in an array
    //glemte selve beskeden hehe
    public static String[] splitOnCrocs(String info)
    {
        //temporary array to store user name
        String[] tempInfo;
    
        tempInfo = info.split(Pattern.quote(" "));
        for (String str: tempInfo)
        {
            System.out.println(str);
        }
        //System.out.println("tempinfo " + tempInfo[0] + ", " + tempInfo[1]);
        String[] tempinfo2;
        tempinfo2 = tempInfo[1].split(Pattern.quote(","));
        //System.out.println("tempinfo2 " + tempInfo[0] + ", " + tempInfo[1]);
        //--
        String[] newTmp;
    
        newTmp = tempInfo[2].split(Pattern.quote(":"));

        /**
         * 0, tempInfo[0] = JOIN MSG
         * 1, tempinfo2[0] = USERNAME
         * 2, newTmp[0] = IP ADDRESS
         * 3, newTmp[1] = PORT
         */
        String[] finalTemp = {tempInfo[0],tempinfo2[0],newTmp[0],newTmp[1]};
    
        String tmp = "";
        for (int i = 0; i < finalTemp.length; i++) {
            //^ in regex is remove the following char ie: ^<< after begnning of string
            //$ in regex is to remove the char before it ie $>> at the end of the string
            tmp = finalTemp[i].replaceAll("^<<|>>$","");
            finalTemp[i] = tmp;
            //System.out.println(finalTemp[i]);
        }
    
        return  finalTemp;
    }

    public Client returnNewClient(String stringFromClient) throws UnknownHostException {
        String[] tmpArr = splitOnCrocs(stringFromClient);
        //System.out.println(tmpArr);
        return new Client(tmpArr[1], InetAddress.getByName(tmpArr[2]),Integer.parseInt(tmpArr[3]));
    }

}
