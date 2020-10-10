/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.File;

/**
 *
 * @author phamdinhlam
 */
public class ReadingXMLHelper {
    
    public static String GetTagKey(String xmlLine)
    {
        String result="";
        
        Integer left= xmlLine.indexOf("<");
        Integer right = xmlLine.indexOf(">");
        String arrayTag[] = xmlLine.substring(left+1, right).split(" ");
        result = arrayTag[0];
        return result;
    }
    
    public static String GetTagSpecificProperty(String xmlLine, String property)
    {
        String result ="NULL";
        
        Integer left= xmlLine.indexOf("<");
        Integer right = xmlLine.indexOf("/>");
        String arrayTag[] = xmlLine.substring(left+1, right).split(" ");
        for(Integer i=1; i<arrayTag.length;i++)
        {
            String currentValueArray[] = arrayTag[i].split("=");
            if(currentValueArray[0].equals(property))
                result=currentValueArray[1];
        }
        result = result.replaceAll("\"", "");
        result = result.replaceAll("//", "");
        result = result.replaceAll("'", "");
        return result;
    }
    
    public static boolean CheckTagContainsProperties(String xmlLine, String property)
    {
        boolean result=false;
        
        Integer left= xmlLine.indexOf("<");
        Integer right = xmlLine.indexOf(">");
        String arrayTag[] = xmlLine.substring(left+1, right).split(" ");
        for(Integer i=1; i<arrayTag.length;i++)
        {
            String currentValueArray[] = arrayTag[i].split("=");
            String valueToCheck= currentValueArray[1].replaceAll("'","");
            if(valueToCheck.equals(property))
            {
                return true;
            }
        }
        
        
        return result;
    }
//    <Data Name='stepName'>abc</Data>
    public static String GetNameProperty(String xmlLine) 
    {
        Integer left= xmlLine.indexOf("<");
        Integer right = xmlLine.indexOf(">");
        String arrayTag[] = xmlLine.substring(left+1, right).split(" ");
        for(Integer i=1; i<arrayTag.length;i++)
        {
            String currentValueArray[] = arrayTag[i].split("=");
            String key= currentValueArray[0];
            String nameProperty= currentValueArray[1].replaceAll("'","");
            if(key.equals("Name"))
                return nameProperty;
        }
        return "";
    }
    
    public static String GetTagValue(String xmlLine)
    {
        String result="NULL";
   //     SAI O DAY. xmlLine chi chua <....> khong phai ca gia tri!!!
        Integer left = xmlLine.indexOf(">");
        Integer right = xmlLine.indexOf("</");
        if(right<0 || left>=right)
            return result;
        result = xmlLine.substring(left+1, right);
        if(result.equals(""))
            result="NULL";
        return result;
    }
    
    
}
