package com.company.Utilities;

import java.util.ArrayList;

public class TestClass
{
    
    public static void main(String[] args)
    {
        for (int i = 0; i < 10; i++)
        {
            System.out.println();
            for (int j = 0; j < 10; j++)
            {
                System.out.print(" d: i "+ i + ", j " + j);
            }
            System.out.println("");
        }
        
        
        /*String[] tmp = StringUtilities.splitAndClean_DATA_ProtocolFromSymbols(Commands.send_JOIN("bob","127.0.0.1",1234));
    
        for (String tt : tmp)
        {
            System.out.println(tt);
        }*/
    
        //TestClass testClass = new TestClass();
        //testClass.testMethod();
        
    }
    
    public void testMethod()
    {
        NewTest newTest = new NewTest();
        newTest.letsDoThis("bob");
        
        NewTest anotherTest = new AnotherTest();
        anotherTest.letsDoThis("peter");
    
    
        NewTest newTestAnotherTest = new LastTest();
        newTestAnotherTest.letsDoThis("hansi");
    
        ((LastTest) newTestAnotherTest).cmonDude("11");
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
        ifElseMystery(6,10);*/
    
    
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
    
    public class NewTest
    {
        public void letsDoThis(String name)
        {
            System.out.println("yea lets do it " + name);
        }
    }
    
    public class AnotherTest extends NewTest
    {
        @Override
        public void letsDoThis(String name)
        {
            System.out.println("no lets not do this " + name);
        }
        
        public void cmonDude(String aa)
        {
            System.out.println(aa);
        }
    
        @Override
        public String toString()
        {
            return "amotherTest";
        }
    }
    
    public class LastTest extends AnotherTest
    {
        @Override
        public void cmonDude(String aa)
        {
            super.cmonDude("from super " + super.toString()  + aa);
        }
    
        @Override
        public void letsDoThis(String name)
        {
            super.letsDoThis(name);
        }
    }
}

