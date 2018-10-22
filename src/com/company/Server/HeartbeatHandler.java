package com.company.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.company.Utilities.StringUtilities.splitOnFirstArrayElement;

public class HeartbeatHandler implements Runnable {
    
    Socket socket;
    public HeartbeatHandler(Socket socket)
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
        
        //TODO: need to research why this doesnt work.. something about not receiving properly??????
        
        while(!message.contains("QUIT"))
        {
            try{
                message = input.nextLine();
                if(splitOnFirstArrayElement(message).equals("IMAV"))
                {
                    String[] duoArr = message.split(" ");
                    System.out.println(duoArr[1] + " is alive");
    
                    System.out.println(message);
                    message = "";
                }
                
                
            }
            catch (NoSuchElementException nse)
            {
                System.exit(1);
            }
        }
    }
}
