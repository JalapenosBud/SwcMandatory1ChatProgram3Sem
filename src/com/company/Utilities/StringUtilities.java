package com.company.Utilities;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class StringUtilities {

    public static String STRIPTHEFUCKINGSLASHOFFMYIPADDRESS(Socket ip) throws UnknownHostException {
        return ip.getLocalAddress().toString().replaceAll("/","");
    }

    /**
     * @param protocol protocol
     * @return array with protocol information
     */
    public static String[] splitJoinProtocol(String protocol)
    {

        String[] splitArrayWhitespace = splitArrayWhitespace = protocol.split(Pattern.quote(" "));
        String[] splitArrayComma = splitArrayWhitespace[1].split(Pattern.quote(","));
        String[] splitArrayColon = splitArrayWhitespace[2].split(Pattern.quote(":"));
        /**
         * 0, splitArrayWhitespace[0] = JOIN
         * 1, splitArrayComma[0] = USERNAME
         * 2, splitArrayColon[0] = IP ADDRESS
         * 3, splitArrayColon[1] = PORT
         */
        String[] tempArray = {splitArrayWhitespace[0],splitArrayComma[0],splitArrayColon[0],splitArrayColon[1]};

        String tmp = "";
        String slashTmp = "";
        for (int i = 0; i < tempArray.length; i++) {
            //^ in regex is remove the following char ie: ^<< after begnning of string
            //$ in regex is to remove the char before it ie $>> at the end of the string
            tmp = tempArray[i].replaceAll("^<<|>>$","");
            if(tmp.contains("/"))
            {
                slashTmp = tmp.replaceAll("/","");
                tempArray[i] = slashTmp;
            }
            else
            {
                tempArray[i] = tmp;
            }
        }
        return tempArray;
    }

}
