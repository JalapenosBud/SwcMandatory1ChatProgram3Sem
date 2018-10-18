package com.company.Client;

import com.company.Utilities.ColorCoder;
import com.company.Utilities.Commands;
import com.company.Utilities.StringUtilities;
import com.sun.deploy.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Pattern;

import static com.company.Utilities.StringUtilities.STRIPTHEFUCKINGSLASHOFFMYIPADDRESS;
import static com.company.Utilities.StringUtilities.splitDataProtocol;

public class ClientMain {
    
    private static InetAddress host;
    private static final int PORT = 1234;
    
    static String username;
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
                //System.out.println("enter message ('QUIT' to exit): ");
                //here we set the message string to the scanner object's nextline method
                //so it just reads whatever gets pressed on the keyboard
                message = userEntry.nextLine();
                username = message;
                //here we use the printwriter supplied with the socket's output stream to send the message over the network
                networkOutput.println(Commands.send_JOIN(message,STRIPTHEFUCKINGSLASHOFFMYIPADDRESS(socket),PORT));
    
                response = networkInput.nextLine();
                
            }while(!response.equals("J_OK") && response.equals("J_ERR"));
    
            System.out.println("we outside j ok now");
            //start listener thread
            Thread clientListener = new Thread(clientListen);
            clientListener.start();
            
            //TODO: heartbeat, ugly implementation lol
            Client client = new Client(username,socket);
            
            Thread clientHearbeat = new Thread(new ClientHeartbeat(networkOutput,username));
            clientHearbeat.start();
            
            //client.heartbeatIsAlive(networkOutput,5);
            
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
    
    
    
    
    /*private static InetAddress host;
    private static final int PORT = 1234;
    
    public static void main(String[] args) {
        
        
        try
        {
            host = InetAddress.getLocalHost();
        }
        catch(UnknownHostException uhEx)
        {
            System.out.println("\nHost ID not found!\n");
            System.exit(1);
        }
        accessServer();
    }
    
    private static void accessServer()
    {
        boolean canWeTalkNow = false;
        Socket socket = null;
    
        Client client;
        
        try
        {
            //instantiate new socket here
            socket = new Socket(host,PORT);
            
            
            Scanner networkInput = new Scanner(socket.getInputStream());
            PrintWriter networkOutput = new PrintWriter(socket.getOutputStream(),true);
            //for console write for user
            Scanner userEntry = new Scanner(System.in);
            //this is the response client gets from the server
            String response = "";
    
            //this is the message the client sends
            String message = "";
            
            //----START------CLIENT CHECK REGISTRATION-------------------//
            do
            {
                System.out.println("still in loop");
                System.out.println("please enter your username:");
                String name = userEntry.nextLine();
                
                client = new Client(name,STRIPTHEFUCKINGSLASHOFFMYIPADDRESS(socket),socket.getPort());
                
                //sent join msg with new compiled client
                networkOutput.println(client.sendJOIN());
                
                response = networkInput.nextLine();
                
                if(response.contains("J_OK"))
                {
                    System.out.println("Server accepted connection.");
                    canWeTalkNow = true;
                    System.out.println("going to next loop");
                }
                else if(response.contains("J_ERR"))
                {
                    System.out.println("PROTOCOL: " + response);
                    System.out.println("username already exists, try another");
                    canWeTalkNow = false;
                }
                
            }while(!canWeTalkNow);
            //----END------CLIENT CHECK REGISTRATION-------------------//
            
            //This is a thread that listens for input from server constantly
            Thread listener = new Thread(new ClientListen(client.getSocket()));
            listener.start();
            //----START------CLIENT CHAT SEND-------------------//
            System.out.println("Welcome to the chat room, prefix message with LIST to see who's online\nOther wise no prefix is required to chat regularly.");
            do {
                message = userEntry.nextLine();
                networkOutput.println(Commands.send_DATA(client.getName(),message));
                
            }while (canWeTalkNow && !message.equals("QUIT"));
            //----END------CLIENT CHAT SEND-------------------//
            
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
    }*/
    
}
