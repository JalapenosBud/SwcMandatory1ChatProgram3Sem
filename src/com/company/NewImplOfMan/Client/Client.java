package com.company.NewImplOfMan.Client;

import com.company.Client.ClientListen;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client
{
    private static InetAddress host;
    private static final int PORT = 1234;
    private static String name;
    private static boolean hasConnected;
    
    private static boolean holdConnnection()
    {
        return hasConnected;
    }
    
    public static void main(String[] args)
    {
        try
        {
            host = InetAddress.getLocalHost();
        }
        catch (UnknownHostException uhEx)
        {
            System.out.println("\nHost ID not found!\n");
            System.exit(1);
        }
        while(!hasConnected)
        {
            getUserNameFromClient();
        }
        if(hasConnected)
        {
            chat();
        }
        
    }
    
    private static void chat()
    {
        Socket socket = null;
        
            try
            {
                socket = new Socket(host,PORT);
                Scanner networkInput = new Scanner(socket.getInputStream());
                Scanner userInput = new Scanner(System.in);
                PrintWriter networkOutput = new PrintWriter(socket.getOutputStream(),true);
                
                ClientListen clientListen = new ClientListen(socket);
                Thread clientListenThread = new Thread(clientListen);
                clientListenThread.start();
                String message, response;
                do
                {
                    message = userInput.nextLine();
                    networkOutput.println("DATA <<" + name + ">>: <<"+message + ">>");
                    response = networkInput.nextLine();
                    System.out.println(response);
                }while (userInput.equals("*QUIT*"));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            
        System.out.println("chatting");
    }
    
    private static void getUserNameFromClient()
    {
        Socket socket = null;
        try{
            socket = new Socket(host,PORT);
            Scanner networkInput = new Scanner(socket.getInputStream());
            Scanner userInput = new Scanner(System.in);
            PrintWriter networkOutput = new PrintWriter(socket.getOutputStream(),true);
            String response;
    
            do
            {
                System.out.println("Enter username");
                name = userInput.nextLine();
                networkOutput.println("JOIN " + name + ", <<" + host.getHostAddress() + ">>:<<" + PORT + ">>");
                response = networkInput.nextLine();
                if(response.equals("J_ERR"))
                {
                    System.out.println("username exists, reenter pls");
                }
                else {
                    System.out.println("welcome to the server");
                    hasConnected = true;
                    break;
                }
            }while(!hasConnected);
        }
        catch (IOException iox)
        {
            iox.getStackTrace();
        }
        
    }
}