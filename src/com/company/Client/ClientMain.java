package com.company.Client;

import com.sun.deploy.util.StringUtils;

import java.util.regex.Pattern;

public class ClientMain {
    public static void main(String[] args)
    {
        //Client client = new Client();
        //client.heartbeatIsAlive(2);

        String JOIN = "<<hi>>,<<127.0.0.1>>:<<8080>>";
    
        for (String str :
                splitOnCrocs(JOIN)) {
            System.out.println(str);
        }
    }
    
    public static String[] splitOnCrocs(String info)
    {
        Pattern stripCrocs = Pattern.compile("([^<>]+)");
        
        String[] tempInfo = new String[2];
        tempInfo = info.split(Pattern.quote(","));
        
        String[] newTmp = new String[2];
        
        newTmp = tempInfo[1].split(Pattern.quote(":"));
        
        String[] finalTemp = {tempInfo[0],newTmp[0],newTmp[1]};
        
        String tmp = "";
        for (int i = 0; i < finalTemp.length; i++) {
            tmp = finalTemp[i].replaceAll("^<<|>>$","");
            finalTemp[i] = tmp;
        }
        
        //return new string[] = {}
        
        return  finalTemp;
    }
}
