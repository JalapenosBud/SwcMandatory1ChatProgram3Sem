package com.company.Utilities;

import com.company.Client.Client;

import java.io.IOException;
import java.net.Socket;

import static com.company.Utilities.StringUtilities.STRIPTHEFUCKINGSLASHOFFMYIPADDRESS;
import static com.company.Utilities.StringUtilities.splitJoinProtocol;

public class ClientUtilities {

    public static Client returnNewClient(String stringFromClient) throws IOException {
        /**
         * 0, = JOIN MSG
         * 1, = USERNAME
         * 2, = IP ADDRESS
         * 3, = PORT
         */
        String[] tmpArr = splitJoinProtocol(stringFromClient);
        //pass in ip address
        Socket tmpSocket = new Socket(tmpArr[2],1234);
        return new Client(tmpArr[1], STRIPTHEFUCKINGSLASHOFFMYIPADDRESS(tmpSocket),Integer.parseInt(tmpArr[3]));
    }
}
