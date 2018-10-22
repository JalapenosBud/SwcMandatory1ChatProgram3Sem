package com.company.Utilities;

import java.util.ArrayList;

public class TestClass
{
    
    public static void main(String[] args)
    {
        String[] tmp = StringUtilities.splitAndClean_DATA_ProtocolFromSymbols(Commands.send_JOIN("bob","127.0.0.1",1234));
    
        for (String tt : tmp)
        {
            System.out.println(tt);
        }
        
        
        
        
        /*
        ArrayList<String> array = new ArrayList<>();
        array.add("hans");
        array.add("peter");
        array.add("bob");
        
        
        Scanner input = new Scanner(System.in);
        boolean added = false;
        
        while (!added)
        {
            System.out.println("type to add a user");
            String myinput = input.nextLine();
            
            if (contains(array, myinput))
            {
                System.out.println(myinput + " does exist");
                added = false;
            }
            else
            {
                System.out.println("list has: " + array.size() + " elements before.");
                System.out.println("doesnt exist, adding");
                array.add(myinput);
                System.out.println(myinput + " was added now.");
                System.out.println("list has: " + array.size() + " elements after.");
                added = true;
            }
            
        }*/
    }
    
    static boolean contains(ArrayList<String> thelist, String name)
    {
        for (String notherName : thelist)
        {
            if (notherName.equals(name))
            {
                return true;
            }
        }
        return false;
    }
        
        /*//TODO: JEG TROR = 13,21
        ifElseMystery(3,20);
        
        //TODO: JEG TROR = 5,6
        ifElseMystery(4,5);
        
        //TODO: JEG TROR = 6,5
        ifElseMystery(5,5);
        
        //TODO: JEG TROR = 7,11
        ifElseMystery(6,10);
        */
        /*int num = -1;
        
        //det her er sandt først, så derfor eksekverer det
        if(num > 0)
        {
            System.out.println("its greater than 0");
        }
        else if(num == 0)
        {
            System.out.println("equal to 0");
        }
        else
        {
            System.out.println("less than 0");
        }*/
    //men hvis den første case er falsk, så kører den alt der er inde i else hvis de matcher
        /*else if( myNum >= 3){
            System.out.println("greater than or equal to 3");
            //her kører kun det første if statement, selvom i teorien, så er 3 også større end 0
            //men fordi den først er mindre end 4, så vil det her kun køre, fordi det hedder IF ELSE, og ikke IF IF, altså hvis det her, og længere ned hvis det her
            //nej det er bare passer det her?? ja ok så bare kør fordi den ikke er falsk
            //hvis den derimod var falsk, ville den gå ned i else blokken og køre
            if(myNum > 4)
            {
                System.out.println("its less than 4");
            }
            else
            {
                System.out.println("just else");
            }
        }*/
    
    
    //PROTOCOL: DATA <<user_name>>: <<free text…>>
       /*String[] tmpArr = splitAndClean_DATA_ProtocolFromSymbols("DATA <<bob>>:<<Hello mr i am se looter>>");
       
       for (String str : tmpArr)
       {
           System.out.println(str);
       }*/
    
    
    public static void ifElseMystery(int x, int y)
    {
        int z = 4;
        if (z <= x)
        {
            z = x + 1;
        }
        else
        {
            z = z + 9;
        }
        if (z <= y)
        {
            y++;
        }
        System.out.println(z + " " + y);
    }
    
    
    //old code this is from clienthandler
    /*public void oldRun()
    {
        //string to pass incoming string to from the clien'ts input stream, ie what gets sent to us
        String received;
        try{
            do {
                //here we do that^
                received = input.nextLine();
    
                if(received != null)
                {
                    switch (splitAndReturnOnlyProtocolMsg(received))
                    {
                        case "JOIN":
                            System.out.println("received a join message");
                            try {
                                checkIfUserJoins(received,clientSocket);
    
                                sendToAllUsers(printListOfActiveUsersToClient());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "DATA":
                            String[] tmpInfo = StringUtilities.splitAndClean_DATA_ProtocolFromSymbols(received);
                            if(tmpInfo[2].contains("LIST"))
                            {
                                sendToAllUsers(printListOfActiveUsersToClient());
                                break;
                            }
                            else if(tmpInfo[2].contains("QUIT"))
                            {
                                
                                try {
                                    System.out.println("user wants to quit, dc'ing them..");
                                    System.out.println("list size before: " + ServerMain.clientArrayList.size());
                                    ServerMain.removeClientAndUpdateClientList(userName);
    
                                    System.out.println("list size now: " + ServerMain.clientArrayList.size() + " user " + userName +" has been removed.");
    
                                    sendToAllUsers(printListOfActiveUsersToClient());
                                    clientSocket.close();
                                    break;
                                    
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                               
                            }else
                            {
                                sendToAllUsers(tmpInfo[2]);
                            }
                            break;
    
                        case "IMAV":
        
                            String[] duoArr = received.split(" ");
                            System.out.println(duoArr[1] + " is alive");
                            //received = "";
                            break;
                    }
                }
            //repeat untill QUIT is received
            }while(!received.equals("QUIT"));
            
        }catch (NoSuchElementException noele)
        {
            System.out.println("Noone's typing");
        }
        try{
            //its only possible to terminate a connection if the client exists
            if(clientSocket != null)
            {
                ServerMain.removeClientAndUpdateClientList(userName);
                sendToAllUsers(printListOfActiveUsersToClient());
                System.out.println("Closing down connection");
                clientSocket.close();
            }
        }catch (IOException ioex)
        {
            System.out.println("Unable to disconnect");
        }
    }*/
    
}
