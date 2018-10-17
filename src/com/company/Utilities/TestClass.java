package com.company.Utilities;
import com.company.Utilities.StringUtilities;

import static com.company.Utilities.StringUtilities.splitDataProtocol;

public class TestClass {
    
    public static void main(String[] args)
    {
        //PROTOCOL: DATA <<user_name>>: <<free text…>>
       String[] tmpArr = splitDataProtocol("DATA <<bob>>:<<Hello mr i am se looter>>");
       
       for (String str : tmpArr)
       {
           System.out.println(str);
       }
    }
    
}
