package com.company.Client;

import com.sun.deploy.util.StringUtils;

import java.util.regex.Pattern;

public class ClientMain {
    public static void main(String[] args)
    {
        //Client client = new Client();
        //client.heartbeatIsAlive(2);

        String JOIN = "JOIN <<myusername>>,<<127.0.0.1>>:<<8080>>";
        for(String str : splitOnCrocs(JOIN))
        {
            System.out.println(str);
        }
    }
    
    //to receive user string and strip it off << and >>
    //then save each value in an array
    //glemte selve beskeden hehe
    public static String[] splitOnCrocs(String info)
    {
        //temporary array to store user name
        String[] tempInfo;
        
        tempInfo = info.split(Pattern.quote(" "));
        
        String[] tempinfo2;
        tempinfo2 = tempInfo[1].split(Pattern.quote(","));
        
        String[] newTmp;
        
        newTmp = tempinfo2[1].split(Pattern.quote(":"));
        
        String[] finalTemp = {tempInfo[0],tempinfo2[0],newTmp[0],newTmp[1]};
        
        String tmp = "";
        for (int i = 0; i < finalTemp.length; i++) {
            tmp = finalTemp[i].replaceAll("^<<|>>$","");
            finalTemp[i] = tmp;
        }
        
        return  finalTemp;
    }
}
