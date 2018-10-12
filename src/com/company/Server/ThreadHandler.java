package com.company.Server;

import com.company.Client.Client;
import com.company.Utilities.StringUtilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static com.company.Utilities.StringUtilities.STRIPTHEFUCKINGSLASHOFFMYIPADDRESS;
import static com.company.Utilities.StringUtilities.splitDataProtocol;
import static com.company.Utilities.StringUtilities.splitJoinProtocol;


public class ThreadHandler extends Thread{

    private Socket client;


    public ThreadHandler(Socket socket) {
        //Set up reference to associated socket…
        client = socket;
    }


    //TODO: omg misunderstood this shit. this is the server, it will reset execution over and over
    public void run ()
    {
        //------incoming connection---------


        //TODO: warning here cause bool doesn't get flipped.


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








}
