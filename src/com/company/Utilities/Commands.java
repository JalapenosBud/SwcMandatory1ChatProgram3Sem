package com.company.Utilities;

public class Commands {

    public static String send_JOIN(String name, String ipAddress, int port)
    {
        return "JOIN " + name + ", <<" + ipAddress + ">>:<<" + port+">>";
    }

    public static String send_DATA(String clientName, String message)
    {
        return "DATA <<" + clientName + ">>:" + "<<" + message + ">>";
    }

    public static String sendJ_OK()
    {
        return "";
    }

    public static String sendJ_ERR()
    {
        return "";
    }

    public static String send_IMAV(String name)
    {
        return "IMAV " + name;
    }

    public static String send_QUIT()
    {
        return "QUIT";
    }

    public static String send_LIST()
    {
        return "LIST";
    }
}
