package com.company.Client;

import com.company.Utilities.StringUtilities;
import com.sun.deploy.util.StringUtils;

import java.util.regex.Pattern;

public class ClientMain {
    public static void main(String[] args)
    {
        Client client = new Client();

        //TEST
        //PROTOCOL: DATA <<user_name>>: <<free text…>>
        /*String data = "DATA <<bobby>>:<<hello guys whats up how u doin hehh3h3h3>>";
        String[] tmp = StringUtilities.splitDataProtocol(data);
        for (String hh :
                tmp) {
            System.out.println(tmp);
        }*/

    }
    
}
