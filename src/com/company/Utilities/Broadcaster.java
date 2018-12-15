package com.company.Utilities;

import com.company.Client.Client;
import com.company.Server.ServerMain;

import java.io.IOException;
import java.io.PrintWriter;

public class Broadcaster {

    public void sendToAllUsers(PrintWriter output)
    {
        String tmpClientNames = "";
        for (Client s : ServerMain.clients)
        {
            try
            {
                tmpClientNames += s.getName() + ", ";
                output = new PrintWriter(s.getSocket().getOutputStream(), true);
                output.println(tmpClientNames);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
