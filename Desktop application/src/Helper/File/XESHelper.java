/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.File;

import Helper.Common.MessageHelper;
import Helper.Common.Variables;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static Helper.Common.Variables.*;
import ProcessingBar.ProgressHelper;
import com.google.common.collect.HashBiMap;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author phamdinhlam
 */
public class XESHelper {
    
    public static void SaveListLogToFile(List<String> _inputLog, String _filePath) throws IOException, InterruptedException
    {
        File fileToSave = new File(_filePath);
        FileWriter fileWriter = new FileWriter(fileToSave);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(Variables.keyWordSeparate);
        printWriter.println(Variables.keySeparateInside);
        
        Integer listSize = _inputLog.size();
        for(Integer i=0; i<listSize; i++)
        {
           printWriter.println(_inputLog.get(i));
        }
        printWriter.close();
        
//        MessageHelper.Info("File was saved! File path: " + fileToSave.getAbsolutePath());
        
    }
    
    
    
    public static void SaveXESLogToFile(List<String> _inputLog, File fileToSave) throws IOException, InterruptedException
    {
        
        FileWriter fileWriter = new FileWriter(fileToSave);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(Variables.keyWordSeparate);
        printWriter.println(Variables.keySeparateInside);
        
        Integer listSize = _inputLog.size();
        for(Integer i=0; i<listSize; i++)
        {
           printWriter.println(_inputLog.get(i));
        }
        printWriter.close();
        
        MessageHelper.Info("File was saved! File path: " + fileToSave.getAbsolutePath());
        
    }
    
    public static String GetAllInformationInOneTraceInXESLog(List<String> xesLogListStrings, Integer tracePosition)
    {
        String outputTrace = xesLogListStrings.get(tracePosition);
        return outputTrace.trim();
    }
    

    public static String GetSpecificTypeInOneTraceInXESLog(List<String> xesLogListStrings, Integer tracePosition, String typeNetwork)
    {
        String outputTrace ="";
        Integer posOfTypeNetWork = -1;
        String[] arrayOutputTrace = xesLogListStrings.get(tracePosition).split(keyWordSeparate);
        
        String[] arrayNode = arrayOutputTrace[0].split(keySeparateInside);
        for (Integer x = 0; x < arrayNode.length; x++) {
            if (typeNetwork.equals(eventKeyToGet[x])) {
                posOfTypeNetWork = x;
                break;
            }
        }
        String currentNode;
        for (Integer i=0;i<arrayOutputTrace.length-1;i++)
        {
            arrayNode = arrayOutputTrace[i].split(keySeparateInside);
            currentNode = arrayNode[posOfTypeNetWork];
            outputTrace = outputTrace + currentNode + keyWordSeparate;
        }
        arrayNode = arrayOutputTrace[arrayOutputTrace.length-1].split(keySeparateInside);
        currentNode = arrayNode[posOfTypeNetWork];
        outputTrace = outputTrace + currentNode;
        
        return outputTrace.trim();
    }
    
    
    
    public static List<String> GetTracesWithSpecificTypeContent(List<String> xesLogListStrings, String typeNetwork, Integer[] arrayPrint)
    {
        Integer n = xesLogListStrings.size();
        List<String> tracesWithSpecificTypeContent = new LinkedList<String>();
        
        Integer posOfTypeNetWork = -1;
        String[] arrayOutputTrace = xesLogListStrings.get(0).split(keyWordSeparate);
        
        String[] arrayNode = arrayOutputTrace[0].split(keySeparateInside);
        for (Integer x = 0; x < arrayNode.length; x++) {
            if (typeNetwork.equals(eventKeyToGet[x])) {
                posOfTypeNetWork = x;
                break;
            }
        }
        
        for(Integer i=0; i<n;i++)
        {
            if(arrayPrint[i]!=1)
                continue;
            String currentTrace[] = xesLogListStrings.get(i).split(keyWordSeparate);
            Integer m = currentTrace.length;
            String currentTraceSpecificContent= "";
            for (Integer j=0;j<m;j++)
            {
                String currentEvent[] = currentTrace[j].split(keySeparateInside);
                String valueToGet = currentEvent[posOfTypeNetWork];
                if(j==0)
                    currentTraceSpecificContent = valueToGet;
                else
                    currentTraceSpecificContent = currentTraceSpecificContent + keySeparateInside + valueToGet;
            }
            
            tracesWithSpecificTypeContent.add(currentTraceSpecificContent);
        }
        
        return tracesWithSpecificTypeContent;
    }
    public static Map<String, String> CategoryCows(List<String> traceList)
    {
        Map<String, String> traceCategoryMap = new HashMap<String, String>();
        Integer traceSize = traceList.size();
        for (Integer i=0;i<traceSize;i++)
        {
            String currentTrace = traceList.get(i);
            if(!traceCategoryMap.containsKey(currentTrace))
                {
                    String value = i.toString();
                    traceCategoryMap.put(currentTrace, value);
                }
                else
                {
                    String value=","+i.toString();
                    traceCategoryMap.put(currentTrace, traceCategoryMap.get(currentTrace)+value);
                }
        }
        
        return traceCategoryMap;
    }
    
    public static Map<String, Map<String, Double>> CaculatePerformersProportional(List<String> xesLogListStrings, Integer[] arrayPrintXesLog, Integer activityPosition, Integer performerPosition)
    {
        Map<String, Map<String, Double>> performerProportionalMap = new HashMap<String, Map<String, Double>>();
        Integer logSize = xesLogListStrings.size();
        
        for(Integer i=0;i<logSize;i++)
        {
            if(arrayPrintXesLog[i]==1)
            {
                String[] currentTrace = xesLogListStrings.get(i).split(keyWordSeparate);
                Integer traceSize = currentTrace.length;
                for (Integer j=0;j<traceSize;j++)
                {
                    String[] currentNode = currentTrace[j].split(keySeparateInside);
                    String currentActivity = currentNode[activityPosition];
                    String currentPerformer = currentNode[performerPosition];
                    if(!performerProportionalMap.containsKey(currentActivity)) // If This Activity is not exist in Map
                    {
                        Map<String, Double> performerAndOccurentMap = new HashMap<String, Double>();
                        performerAndOccurentMap.put(currentPerformer, 1.0);
                        performerProportionalMap.put(currentActivity, performerAndOccurentMap);
                    }
                    else
                    {
                        // Check the performer is exist or not
                        Map<String, Double> performerAndOccurentMap = new HashMap<String, Double>();
                        performerAndOccurentMap  =  performerProportionalMap.get(currentActivity);
                        
                        if(!performerAndOccurentMap.containsKey(currentPerformer))
                        {
                            performerAndOccurentMap.put(currentPerformer, 1.0);
                        }
                        else // Update occurent of the current Performer.
                        {
                            performerAndOccurentMap.put(currentPerformer, performerAndOccurentMap.get(currentPerformer)+1);
                        }
                        
                        performerProportionalMap.put(currentActivity, performerAndOccurentMap);
                    }
                    
                }
                
            }
        }
        return performerProportionalMap;
    }
    
}
