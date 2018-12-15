package com.company.Client;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientListen implements Runnable {
    
    private Socket socket;
    public ClientListen(Socket socket)
    {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        
        String message = "";
        Scanner input = null;
        
        try {
            input = new Scanner(socket.getInputStream());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        while(!message.equals("QUIT"))
        {
            try{
                message = input.nextLine();
                
                System.out.println(message);
            }
            catch (NoSuchElementException nse)
            {
                System.exit(1);
            }
        }
    }
}
