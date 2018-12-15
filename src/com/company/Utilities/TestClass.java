package com.company.Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TestClass {
    
    public static void main(String[] args)
    {
        Pattern p = Pattern.compile(":");
        Matcher matcher = p.matcher("hehe:hehe");
        boolean b = matcher.matches();
    }
    
}
