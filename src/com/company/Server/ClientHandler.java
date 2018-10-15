package com.company.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {

private Socket socket;
private Scanner input;
private PrintWriter output;

    public ClientHandler(Socket socket)
    {
        this.socket = socket;

        try{

            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (IOException ioEx)
        {
            ioEx.printStackTrace();
        }
    }

    @Override
    public void run() {
        MessageReceiver receiver = new MessageReceiver(socket, input, output);
        //MessageSender sender = new MessageSender(client);

        receiver.start();
        //sender.start();
    }
}
