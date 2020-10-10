/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.File;

import Helper.Common.MessageHelper;
import ProcessingBar.ProgressHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 *
 * @author phamdinhlam
 */
public class XESReadingHelper {
    
 /**
 * Function AnalyXmlLine: 
 * Input xmlLine. E.x: <string key="org:resource" value="Reference alignment processor"/>
 * output array String result [3]: 
 *      result[0]: Type
 *      result[1]: key
 *      result[2]: value
 * 
     * @param xmlLine
     * @return 
 */
    
    public String[] AnalyXmlLine(String xmlLine)
    {
        // Forexample: <string key="org:resource" value="dde669"/>
        // result = {string, org:resource, dde669}
        String[] result = new String[3];
        if (!xmlLine.contains("key") && !xmlLine.contains("value"))
            return result;
        
        String keyWordForKey = "key=\"";
        String keyWordForValue = "value=\"";

          String type,key,value;
          Integer startInteger,endInteger;

          startInteger = 1;
          endInteger = xmlLine.indexOf(" ", startInteger);
          type=xmlLine.substring(startInteger, endInteger);
          result[0]=type;
          
          startInteger=xmlLine.indexOf(keyWordForKey, endInteger);
          endInteger=xmlLine.indexOf("\"", startInteger+keyWordForKey.length());
          key=xmlLine.substring(startInteger+keyWordForKey.length(), endInteger); // key=" => 5 characters 
          result[1]=key;
          
          startInteger = xmlLine.indexOf(keyWordForValue,endInteger);
          endInteger=xmlLine.indexOf("\"",startInteger+keyWordForValue.length());  // value=" => 7 characters;
          value=xmlLine.substring(startInteger+keyWordForValue.length(),endInteger);
          result[2]=value;

        return result;
    }
    
      public List<String> ReadXESLog(String filePath, String[] eventKeyToGet,String keyWordSeparate, String keySeparateInside)
        {
            List<String> listXESLog = new LinkedList<String>();  //Need to move to the top of Startform.java
            try {
            String[] listValue; 
            String startString="";String endString="";
            listValue = new String[eventKeyToGet.length];
            for (Integer i=0;i<eventKeyToGet.length-1;i++)
            {
                listValue[i]="NULL";
                startString=startString+"START"+keySeparateInside;
                endString=endString+"END"+keySeparateInside;
            }
            listValue[eventKeyToGet.length-1]="NULL";
            startString=startString+"START";
            endString=endString+"END";

            boolean startTrace =false;
            boolean startEvent = false;
            boolean endEvent = false;
           // String typeNetwork="org:resource"; //Already exit in the Startform.java
           ProgressHelper.RunProgress("Reading file...");

           File f = new File(filePath);
//            Count total number of Line in File.
            Path path = Paths.get(filePath);
            
            int countCharacter=0;
            Scanner scan = new Scanner(f,"UTF-8");
            scan.useDelimiter(">");  
            long totalCharacter = f.length();
            long currentCharacterLong=0;
            
            try
            {
                
                XESReadingHelper helper = new XESReadingHelper();
                String currentTrace="";
                String[] s;
                String line="";
                
                while (scan.hasNext()) 
                {
                    
                    line = scan.next();
                    line+=">";
                    currentCharacterLong += line.length();
                    ProgressHelper.SynchronizeProgress(currentCharacterLong, totalCharacter); // Update the process.
                    
                    line=line.trim();
                    if (line.equals("<trace>"))
                    startTrace=true;

                    if(line.equals("<event>"))
                    {
                        startEvent=true;
                        endEvent=false;
                    }
                    startEvent=true;

                    if(line.equals("</event>"))
                    {
                        startEvent=false;
                        endEvent=true;
                    }

                    if(line.equals("</trace>"))
                        {
                            startTrace=false;
                            currentTrace = startString + keyWordSeparate +currentTrace + endString;
                            listXESLog.add(currentTrace);
                            currentTrace="";
                        }
                    if (startTrace && startEvent)
                        for (Integer j=0;j<eventKeyToGet.length;j++)
                            if(line.contains(eventKeyToGet[j]))
                            {
                                s = helper.AnalyXmlLine(line);
                                String value = s[2];
                                listValue[j] = value;
                                break;
                            }

                    if(startTrace && !startEvent && endEvent)
                    {
                        String currentContent="";
                        for (Integer j=0;j<eventKeyToGet.length-1;j++)
                            currentContent=currentContent+listValue[j]+keySeparateInside;
                        currentContent+=listValue[listValue.length-1];
                        currentTrace+=currentContent + keyWordSeparate;
                    }
                    
                    
                }
            } 
            catch(Exception e)
            {
                
            }
            
            } catch (Exception ex) {
                MessageHelper.Warning(ex.toString());
            }
            ProgressHelper.SynchronizeProgress(100, 100); // Update the process.
            return listXESLog;
        }
      

}
