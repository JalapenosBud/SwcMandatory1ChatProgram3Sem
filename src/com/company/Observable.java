package com.company;

import java.io.PrintWriter;

public interface Observable {
    public void broadcastThis(Client client);
}
