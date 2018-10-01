package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ServerMain {

    public String getSendThisMSG() {
        return sendThisMSG;
    }

    public void setSendThisMSG(String sendThisMSG) {
        this.sendThisMSG = sendThisMSG;
    }

    private String sendThisMSG="";

    static ServerSocket serverSocket;
    private static final int port = 1234;
    private List<Client> clients = new ArrayList<>();



    public List<Client> getClients() {
        return clients;
    }

    //gets length of list - 1
    public int getNumberOfLastAddedClient()
    {
        return clients.size() - 1;
    }

    public static void main(String[] args) {
        ServerMain serverMain = new ServerMain();
        Client tmpClient = new Client("bob",0,null);
        try{
            serverSocket = new ServerSocket(port);
        }catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("\nunable to set up port");
            System.exit(1);
        }

        do {

            try{
                tmpClient.mySocket = serverSocket.accept();
                //her få clients real data instead of hardcode
                serverMain.addInterestedClientsForBroadcast(tmpClient);
                serverMain.notifyAllClientsNewClientJoined();
            }
            catch (IOException e)
            {
                System.out.println("couldnt connect");
                System.exit(1);
            }
            Server handler = new Server(tmpClient.mySocket);
            handler.start();

        }while(true);
    }

    //TODO: refactor for low coupling
    public void addInterestedClientsForBroadcast(Client client)
    {
        clients.add(client);
    }

    //TODO: refactor for low coupling
    public void notifyAllClientsNewClientJoined()
    {
        PrintWriter output = null;
        String received;

        boolean notifiedReceived = false;
        for(Client cl : clients)
        {
            do {
                try{
                    output = new PrintWriter(cl.mySocket.getOutputStream(), true);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                //Accept message from client on the socket's input stream…
                received = cl.toString();
                //Echo message back to client on the socket's output stream…
                System.out.println(received);
                System.out.println(clients.get(clients.size()-1));
                System.out.println("current socket" + cl.mySocket);
                System.out.println(clients.size());
                output.println("who joined? " + received);
                notifiedReceived = true;
                //Repeat above until 'QUIT' sent by client…
            } while (!notifiedReceived);

        }
    }


}
