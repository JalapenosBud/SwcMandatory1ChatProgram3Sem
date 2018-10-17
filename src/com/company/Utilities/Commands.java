package com.company.Utilities;

public class Commands {

    public static String send_JOIN()
    {
        return "";
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

    public static String send_IMAV()
    {
        return "";
    }

    public static String send_QUIT()
    {
        return "";
    }

    public static String send_LIST()
    {
        return "LIST";
    }
}
