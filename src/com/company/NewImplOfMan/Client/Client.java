package com.company.NewImplOfMan.Client;

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
        getUserNameFromClient();
    }
    
    private static void getUserNameFromClient()
    {
        Socket socket = null;
        try{
            socket = new Socket(host,PORT);
            Scanner networkInput = new Scanner(socket.getInputStream());
            Scanner userInput = new Scanner(System.in);
            PrintWriter networkOutput = new PrintWriter(socket.getOutputStream(),true);
            String name, response;
            do
            {
                System.out.println("Enter username");
                name = userInput.nextLine();
                networkOutput.println("JOIN " + name + ", <<" + host.getHostAddress() + ">>:<<" + PORT + ">>");
                response = networkInput.nextLine();
                if(response.equals("J_ERR"))
                {
                    System.out.println("username exists, reenter pls");
                    break;
                }
                System.out.println(response);
            }while(!response.equals("J_OK"));
        }
        catch (IOException iox)
        {
            iox.getStackTrace();
        }
        finally
        {
            try
            {
                System.out.println("\nClosing connection…");
                socket.close();
            }
            catch (IOException ioEx)
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
                catch (IOException ioEx)
                {
                    System.out.println("Unable to disconnect!");
                    System.exit(1);
                }
            }
        }
    }
}