/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Common;

import Helper.Common.MessageHelper;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static Helper.Common.Variables.*;
import java.text.SimpleDateFormat;
import static javafx.util.Duration.millis;
import org.apache.commons.lang3.time.DurationFormatUtils;


/**
 *xesLogListString: 
 *    START,START,START,START,START!@#
 *    00F76F16-4E65-4282-94B5-58F3AF7BD0F8,mail income,0;n/a,2015-05-08T00:00:00.000+02:00,Payment application!@#
 *    11F70E83-9957-4422-A740-1965E20DC61C,mail valid,0;n/a,2015-05-12T00:00:00.000+02:00,Entitlement application!@#
 * @author phamdinhlam
 */
public class TimeHelper {
    
    
    public static String CaculateDurationBetweenReturnString(String _beginT, String _endT)
    {
        if(_beginT.equals("") || _endT.equals("") || _beginT.equals("NULL") || _endT.equals("NULL"))
            return "00:00:00";
        
        ZonedDateTime time1 = ZonedDateTime.parse(_beginT);
        ZonedDateTime time2 = ZonedDateTime.parse(_endT);
        Duration duration = Duration.between(time1, time2);
        
        SimpleDateFormat myFormat = new SimpleDateFormat("HH:mm:ss");
        return DurationFormatUtils.formatDuration(duration.toMillis(), "HH:mm:ss", true);
    
    }
    
    public static String DurationToStringFormat(Duration _inputDuration)
    {
        SimpleDateFormat myFormat = new SimpleDateFormat("HH:mm:ss");
        return DurationFormatUtils.formatDuration(_inputDuration.toMillis(), "HH:mm:ss", true);
    }
    
    public static Duration CaculateDurationBetweenReturnDuration(String _beginT, String _endT)
    {
        if(_beginT.equals("") || _endT.equals("") || _beginT.equals("NULL"))
            return Duration.ZERO;
        
        ZonedDateTime time1 = ZonedDateTime.parse(_beginT);
        ZonedDateTime time2 = ZonedDateTime.parse(_endT);
        Duration duration = Duration.between(time1, time2);
        
        return duration;
    }
    
    public static void CalculateDurationPlus()
    {
        
    }
    
    public static void AssignTimeOfTraceToMap(List<String> xesLogListStrings,Map<Integer, ZonedDateTime> traceStartTimeMap,Map<Integer, ZonedDateTime> traceEndTimeMap,Map<Integer, Duration> traceDurationMap,String[] eventKeyToGet,String keyWordSeparate)
    {
        Integer lengthOfLog = xesLogListStrings.size();
        Integer count=0;
        for (Integer i=0;i<lengthOfLog;i++)
        {
                String currentCase = xesLogListStrings.get(i);  // Analy each case
                String[] listEvent = currentCase.split(keyWordSeparate);
                Integer lengthOfEvent = listEvent.length;
                
                String[] firstEvent=listEvent[1].split(keySeparateInside); // {[0]="START,START,START,START", [1]="a1,p1,d1,t1",...}
                String firstTimestampString = firstEvent[posOfTime];
                
                String[] lastEvent=listEvent[lengthOfEvent-2].split(keySeparateInside); // {[n-1]="END,END,END,END", [n-2]="a n-2,p n-2,d n-2,t n-2",...}
                String lastTimestampString = lastEvent[posOfTime];
                
            try {
                count++;
                
                ZonedDateTime firstTimestampDateTtime = ZonedDateTime.parse(firstTimestampString);
                traceStartTimeMap.put(count-1,firstTimestampDateTtime);
                
                ZonedDateTime lastTimestampDateTime = ZonedDateTime.parse(lastTimestampString);
                traceEndTimeMap.put(count-1, lastTimestampDateTime);
                
                Duration duration = Duration.between(firstTimestampDateTtime, lastTimestampDateTime);
                traceDurationMap.put(count-1, duration);
                
                
                
            } catch (Exception e) {
                MessageHelper.Warning("Time is not in the format - Trace number: " + i.toString());
            }
                
        }
    }
    
    
    public static Map<Integer, Duration> SortByDuration(Map<Integer, Duration> unsortMap) 
    {
        // 1. Convert Map to List of Map
        List<Map.Entry<Integer, Duration>> list =
                new LinkedList<Map.Entry<Integer, Duration>>(unsortMap.entrySet());
 
        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<Integer, Duration>>() {
            public int compare(Map.Entry<Integer, Duration> o1,
                               Map.Entry<Integer, Duration> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
 
        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<Integer, Duration> sortedMap = new LinkedHashMap<Integer, Duration>();
        for (Map.Entry<Integer, Duration> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
 
        return sortedMap;
    }
    
    
    public static Map<Integer, ZonedDateTime> SortByTime(Map<Integer, ZonedDateTime> unsortMap) 
    {
        // 1. Convert Map to List of Map
        List<Map.Entry<Integer, ZonedDateTime>> list =
                new LinkedList<Map.Entry<Integer, ZonedDateTime>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<Integer, ZonedDateTime>>() {
            public int compare(Map.Entry<Integer, ZonedDateTime> o1,
                               Map.Entry<Integer, ZonedDateTime> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<Integer, ZonedDateTime> sortedMap = new LinkedHashMap<Integer, ZonedDateTime>();
        for (Map.Entry<Integer, ZonedDateTime> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
    public static String GetCurrentDateTime()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        return dtf.format(now);
    }
    public static String GetNameByDateTime()
    {
        String name= GetCurrentDateTime();
        name = name.replaceAll("/", "-");
        name = name.replaceAll(":", "-");
        return name;
    }
    
    public static String GetIdByDateTime()
    {
        String name= GetCurrentDateTime();
        name = name.replaceAll("/", "");
        name = name.replaceAll(":", "");
        name = name.replaceAll(" ", "");
        return name;
    }
}
