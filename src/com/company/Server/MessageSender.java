package com.company.Server;

import com.company.Utilities.StringUtilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static com.company.Utilities.StringUtilities.inputDataOutputMessage;

public class MessageSender extends Thread{

    private Scanner input;
    private PrintWriter output;

    Socket client;

    public MessageSender(Socket client)
    {
        this.client = client;
    }

    @Override
    public synchronized void run() {

        try {
            input = new Scanner(client.getInputStream());
            output = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

        while(true)
        {
            System.out.println("waiting for message...");

            //TODO:
            String received1 = input.nextLine();
            //the whole message we receive
            System.out.print(received1);

            while(!received1.equals("**QUIT**"))
            {
                String[] tmpInfo = StringUtilities.splitDataProtocol(received1);
                //TODO: this will never execute cause tmpinfo isnt updated since the client has joined
                //TODO: it needs to read and break up the new message
                switch (tmpInfo[0])
                {
                    case "DATA":
                        //TODO: print out to all other uses
                        output.println(inputDataOutputMessage(received1));
                        // break;
                }
            }
        }
    }
}
