package com.company.Client;

import com.company.Utilities.Commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientMain {
    
    private static InetAddress host;
    private static final int PORT = 1234;
    private static String username;
    
    public static void main(String[] args)
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
        try{
            socket = new Socket(host,PORT);
            
            Scanner networkInput = new Scanner(socket.getInputStream());
            PrintWriter networkOutput = new PrintWriter(socket.getOutputStream(),true);
            
            //set up keyboard input from the user at his computer
            Scanner userEntry = new Scanner(System.in);
            
            ClientListen clientListen = new ClientListen(socket);
            
            String message, response;

            do {
                System.out.println("Enter your username: ");
                message = userEntry.nextLine();
                username = message;
                networkOutput.println(Commands.send_JOIN(message,socket.getInetAddress().toString(),PORT));
    
                response = networkInput.nextLine();
                
            }while(!response.equals("J_OK") && response.equals("J_ERR"));

            Thread clientListener = new Thread(clientListen);
            clientListener.start();
            
            Thread clientHearbeat = new Thread(new ClientHeartbeat(networkOutput,username));
            clientHearbeat.start();
            
            while (!message.equals("QUIT"))
            {
                message = userEntry.nextLine();
                networkOutput.println(Commands.send_DATA(username,message));
            }
        }
        catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
        finally
        {
            try{
                
                System.out.println("closing\n");
                socket.close();
            }
            catch (IOException ioex)
            {
                System.out.println("cant dc");
                System.exit(1);
            }
        }
    }
}
