package com.company.Server;

import java.net.Socket;

public class ClientHandler extends Thread {

    Socket client;

    public ClientHandler(Socket client)
    {
        this.client = client;
    }

    @Override
    public void run() {
        MessageReceiver receiver = new MessageReceiver(client);
        MessageSender sender = new MessageSender(client);

        receiver.start();
        sender.start();
    }
}
