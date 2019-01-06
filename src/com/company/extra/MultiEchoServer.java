package com.company.extra;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiEchoServer
{
    private static ServerSocket serverSocket;
    private static final int PORT = 1234;
    
    public static void main(String[] args)
            throws IOException
    {
        try
        {
            serverSocket = new ServerSocket(PORT);
        }
        catch (IOException ioEx)
        {
            System.out.println("\nUnable to set up port!");
            System.exit(1);
        }
        do
        {
            Socket client = serverSocket.accept();
            System.out.println("\nNew client accepted.\n");
            ClientHandler handler =
                    new ClientHandler(client);
            handler.start();
        }while (true);
    }
}
