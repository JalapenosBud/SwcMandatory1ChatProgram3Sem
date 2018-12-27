package com.company.NewImplOfMan.Server;

import com.company.Server.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Broadcaster
{
    public void sendToAllUsers(PrintWriter output)
    {
        String tmpClientNames = "";
        for (String s : Server.clients.keySet())
        {
            try
            {
                for (Socket socket : Server.clients.values())
                {
                    tmpClientNames += s + ", ";
                    output = new PrintWriter(socket.getOutputStream(), true);
                    output.println(tmpClientNames);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
