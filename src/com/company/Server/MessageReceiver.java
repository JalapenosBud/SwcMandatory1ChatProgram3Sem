package com.company.Server;

import com.company.Client.Client;
import com.company.Utilities.StringUtilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static com.company.Utilities.ClientUtilities.returnNewClient;
import static com.company.Utilities.StringUtilities.inputDataOutputMessage;
import static com.company.Utilities.StringUtilities.splitJoinProtocol;

public class MessageReceiver extends Thread {

    String incoming;

    private Scanner input;
    private PrintWriter output;

    Socket client;

    public MessageReceiver(Socket client, Scanner input, PrintWriter output)
    {
        this.client = client;
        this.input = input;
        this.output = output;
    }

    

}
