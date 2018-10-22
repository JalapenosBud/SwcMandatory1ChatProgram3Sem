package com.company.Client;

import com.company.Utilities.ColorCoder;
import com.company.Utilities.Commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import static com.company.Utilities.StringUtilities.STRIPTHEFUCKINGSLASHOFFMYIPADDRESS;
import static com.company.Utilities.StringsForProtocols.J_ERR;
import static com.company.Utilities.StringsForProtocols.J_OK;
import static com.company.Utilities.StringsForProtocols.QUIT;

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
        boolean areWeIn = false;
        
        Socket socket = null;
        try{
            //here we initialize the socket with given host ie up address and port supplied
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
                networkOutput.println(Commands.send_JOIN(message,STRIPTHEFUCKINGSLASHOFFMYIPADDRESS(socket),PORT));
    
                response = networkInput.nextLine();
                //one liner boolean
                
                if(response.equals(J_ERR))
                {
                    System.out.println("Username taken.");
                    areWeIn = false;
                }
                else if(response.equals(J_OK))
                {
                    areWeIn = true;
                }
                
            }while(!areWeIn);
    
            if(areWeIn)
            {
                //start listener thread
                Thread clientListener = new Thread(clientListen);
                clientListener.start();
    
                Thread clientHearbeat = new Thread(new ClientHeartbeat(networkOutput,username));
                clientHearbeat.start();
            }
            
            while (!message.equals(QUIT))
            {
                System.out.println(ColorCoder.ANSI_BLACK + "You> ");
                message = userEntry.nextLine();
                networkOutput.println(Commands.send_DATA(username,message));
            }
        }
        catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
        finally {
            try{
                
                System.out.println("closing\n");
                socket.close();
            }
            catch (IOException ioex)
            {
                //this exception makes sure that if the socket can't close connection, we force quit the program
                System.out.println("cant dc");
                System.exit(1);
            }
        }
    }
}
