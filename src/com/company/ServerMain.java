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
        try{
            serverSocket = new ServerSocket(port);
        }catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("\nunable to set up port");
            System.exit(1);
        }

        do {
            Socket clientSocket = null;
            try{
                clientSocket = serverSocket.accept();
                //her få clients real data instead of hardcode
                serverMain.addInterestedClientsForBroadcast(new Client("doglover420",1,clientSocket));
                serverMain.notifyAllClientsNewClientJoined();
            }
            catch (IOException e)
            {
                System.out.println("couldnt connect");
                System.exit(1);
            }
            Server handler = new Server(clientSocket);
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
        for(Client cl : clients)
        {
            PrintWriter output = null;
            String received;
            do {
                try{
                    output = new PrintWriter(cl.mySocket.getOutputStream(), true);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                //Accept message from client on the socket's input stream…
                received =  cl.broadcastThis(clients.get(clients.size()-1));
                System.out.println("message received: " + received);
                //Echo message back to client on the socket's output stream…

                output.println("ECHO: " + received);
                //Repeat above until 'QUIT' sent by client…
            } while (!received.equals("QUIT"));
            try {
                if (cl.mySocket != null) {
                    System.out.println("Closing down connection…");
                    cl.mySocket.close();
                }
            } catch (IOException ioEx) {
                System.out.println("Unable to disconnect!");
            }

        }
    }


}
