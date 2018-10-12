package com.company.Utilities;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class StringUtilities {

    public static String STRIPTHEFUCKINGSLASHOFFMYIPADDRESS(Socket ip) throws UnknownHostException {
        return ip.getLocalAddress().toString().replaceAll("/","");
    }


    //PROTOCOL: DATA <<user_name>>: <<free text…>>
    //[0] = DATA
    //[1] = user name
    //[2] = message
    public static String[] splitDataProtocol(String info)
    {
        //[0] = DATA PROTOCOL, [1] = <<user_name>> : <<free text...>>
        String[] splitArrayWhitespace = info.split(" ", 2);

        //[0] = <<user_name>>, [1] <<free text...>>
        String[] splitArrayColon = splitArrayWhitespace[1].split(":");

        //[1] = <<free text>>
        String message = splitArrayColon[1];

        //temporary array to hold all values to strip of weird characters
        String[] tempArray = {splitArrayWhitespace[0],splitArrayColon[0], message};

        String tmp = "";

        for (int i = 0; i < tempArray.length; i++) {
            //^ in regex is remove the following char ie: ^<< after begnning of string
            //$ in regex is to remove the char before it ie $>> at the end of the string
            //tmp = tempArray[i].replaceAll("^<<|>>$", "");

            tempArray[i] = tempArray[i].replaceAll("^<<|>>$", "");

        }
        //TODO:
        return tempArray;
    }

    /**
     * HELPER METHOD
     * @param info PROTOCL MESSAGE TO SPLIT
     * @return new clean array with all info for sending protocol
     */
    //to receive user string and strip it off << and >>
    //then save each value in an array
    //glemte selve beskeden hehe
    public static String[] splitJoinProtocol(String info)
    {
        //temporary array to store user name
        //TODO: COMMENTS for each element
        String[] splitArrayWhitespace = splitArrayWhitespace = info.split(Pattern.quote(" "));
        String[] splitArrayComma = splitArrayWhitespace[1].split(Pattern.quote(","));
        String[] splitArrayColon = splitArrayWhitespace[2].split(Pattern.quote(":"));
        //TODO: split further up and save port in its own string
        /**
         * 0, tempInfo[0] = JOIN MSG
         * 1, tempinfo2[0] = USERNAME
         * 2, newTmp[0] = IP ADDRESS
         * 3, newTmp[1] = PORT
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

        return  tempArray;
    }

    /**
     * this method gets data protocol and outputs message user writes
     * @param input
     * @return
     */
    public static String inputDataOutputMessage(String input)
    {
        String[] tmpArr = splitDataProtocol(input);
        //TODO: split incoming data up and use message element only
        //TODO: is done?
        return tmpArr[2];
    }

}
