/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableHelper;

import Helper.Common.MessageHelper;
import Helper.Common.TimeHelper;
import Helper.Common.Variables;
import java.util.LinkedList;
import java.util.List;
import static Helper.Common.Variables.*;
import static TableHelper.TableController.RemoveAllTableData;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static TableHelper.TableController.InitializeBlankTableWithHeader;
/**
 *
 * @author DinhLam Pham
 */
public class xesLogToTable {
    final static Class[] columnClass = new Class[] {
    String.class, Integer.class, Integer.class, String.class
};
    
    // Analyze activity information in xesLogList -> Put the data to JTable.
    public static void ToPerformerTable(List<String> _xesLogList)
    {
        try {
            globalPerformerSet = new HashSet<>(); globalPerformerDuration = new HashMap<>();
             //...
            Map<String, Integer> involveActivityMap = new HashMap<>();
            Map<String, Integer> involveTraceMap = new HashMap<>();

            for(Integer pos=0; pos<_xesLogList.size(); pos++)  //...
            {
                String trace = _xesLogList.get(pos); // ...
                String[] currentTrace = trace.split(keyWordSeparate);
                List<String> localPerformerList = new LinkedList<>();

                for(int j=1; j<currentTrace.length-1; j++)
                {
                    String[] currentGroup = currentTrace[j].split(keySeparateInside);
                    String currentPerformer = currentGroup[posOfPer];
                    
                    String currentTime = currentGroup[posOfTime];
                    String nextTime = ""; Duration thisDuration = Duration.ZERO;
                    if(j<currentTrace.length-2)
                    {
                        nextTime = currentTrace[j+1].split(keySeparateInside)[posOfTime];
                        thisDuration =TimeHelper.CaculateDurationBetweenReturnDuration(currentTime, nextTime);
                    }
                    
                    
                    if(!globalPerformerSet.contains(currentPerformer))
                    {
                        globalPerformerSet.add(currentPerformer);
                        involveActivityMap.put(currentPerformer, 1);
                        globalPerformerDuration.put(currentPerformer, thisDuration);
                        
                        
                        
                    }
                    else
                    {
                        involveActivityMap.put(currentPerformer, involveActivityMap.get(currentPerformer) + 1);
                        globalPerformerDuration.put(currentPerformer, globalPerformerDuration.get(currentPerformer).plus(thisDuration));
                        
                       
                    }

                    if(!localPerformerList.contains(currentPerformer))
                        localPerformerList.add(currentPerformer);

                    // Calculate for duration... It's a little difficult...
                }

                for (String per : localPerformerList)
                {
                    if(!involveTraceMap.containsKey(per))
                        involveTraceMap.put(per, 1);
                    else
                        involveTraceMap.put(per, involveTraceMap.get(per) + 1);
                }
            }
            List<String> performerList = new LinkedList<>();

            for(String per: globalPerformerSet)
            {
               performerList.add(logName.replace(".xes", "") + keySeparateInside + per + keySeparateInside + involveTraceMap.get(per).toString() + keySeparateInside + involveActivityMap.get(per)
                        + keySeparateInside + TimeHelper.DurationToStringFormat(globalPerformerDuration.get(per)));
            }
            globalPerformerTable = new JTable();
            globalPerformerTable = ListStringToTable(performerList, performerTableHeader);
        } catch (Exception e) {MessageHelper.Error("<ToPerformerTable>\n"+ e.toString());
        }
    }
    
    // Analyze activity information in xesLogList -> Put the data to JTable.
    public static void ToActivityTable(List<String> _xesLogList)
    {
        try {
            globalActivitySet = new HashSet<>(); globalActivityDuration = new HashMap<>();
            
            Map<String, Integer> occurenceMap = new HashMap<>();
            Map<String, Integer> involveTraceMap = new HashMap<>();

            for(Integer pos=0; pos<_xesLogList.size(); pos++)  //...
            {
                String trace = _xesLogList.get(pos); // ...
                String[] currentTrace = trace.split(keyWordSeparate);
                List<String> localActivityList = new LinkedList<>();

                for(int j=1; j<currentTrace.length-1; j++)
                {
                    String[] currentGroup = currentTrace[j].split(keySeparateInside);
                    String currentActivity = currentGroup[posOfAct];
                    
                    String currentTime = currentGroup[posOfTime];
                    String nextTime = ""; Duration thisDuration = Duration.ZERO;
                    if(j<currentTrace.length-2)
                    {
                        nextTime = currentTrace[j+1].split(keySeparateInside)[posOfTime];
                        thisDuration =TimeHelper.CaculateDurationBetweenReturnDuration(currentTime, nextTime);
                    }
                    
                    if(!globalActivitySet.contains(currentActivity))
                    {
                        globalActivitySet.add(currentActivity);
                        occurenceMap.put(currentActivity, 1);
                        globalActivityDuration.put(currentActivity, thisDuration);
                    }
                    else
                    {
                        occurenceMap.put(currentActivity, occurenceMap.get(currentActivity) + 1);
                        globalActivityDuration.put(currentActivity, globalActivityDuration.get(currentActivity).plus(thisDuration));
                    }

                    if(!localActivityList.contains(currentActivity))
                        localActivityList.add(currentActivity);

                    
                }

                for (String act : localActivityList)
                {
                    if(!involveTraceMap.containsKey(act))
                        involveTraceMap.put(act, 1);
                    else
                        involveTraceMap.put(act, involveTraceMap.get(act) + 1);
                }
            }
            List<String> activityList = new LinkedList<>();

            for(String act: globalActivitySet)
            {
               activityList.add(logName.replace(".xes", "") + keySeparateInside + act + keySeparateInside + involveTraceMap.get(act).toString() + keySeparateInside + occurenceMap.get(act)
                        + keySeparateInside + TimeHelper.DurationToStringFormat(globalActivityDuration.get(act)));
            }
            globalActivityTable = new JTable();
            globalActivityTable = ListStringToTable(activityList, activityTableHeader);
        } catch (Exception e) {MessageHelper.Error("<ToActivityTable> \n"+ e.toString());
        }
    }
    
    
    public static JTable ToTraceTable(List<String> _xesLogList, Integer _fromPos, Integer _toPos)
    {
        JTable _inputTable = new JTable(); maxEventInTrace = 0; minEventInTrace=1000;
        Integer sumTotalEvent = 0;
        try {
            globalCurrentViewingTraceList = new LinkedList<>();
            for(int i=_fromPos; i<=_toPos; i++){
                if(i>=_xesLogList.size())
                    break;
                String[] currentTrace = _xesLogList.get(i).split(keyWordSeparate);
                
                /* Calculate number of event in traces*/
                if(maxEventInTrace < currentTrace.length)
                {
                    maxEventInTrace = currentTrace.length;
                    maxEventID = i;
                }
                if(minEventInTrace > currentTrace.length)
                    minEventInTrace = currentTrace.length;
                sumTotalEvent += currentTrace.length;
                /*-----------------------------------------*/
                
                Integer traceId = i;
                Set<String> activitySet = new HashSet<>();
                Set<String> performerSet = new HashSet<>();
                
                for(int j=1; j<currentTrace.length-1; j++){  //0: START; last-1: END
                    String[] currentGroup = currentTrace[j].split(keySeparateInside);
                    // 0: Activiy; 1: Performer; 2: Timestamp
                    if (!activitySet.contains(currentGroup[posOfAct]))
                        activitySet.add(currentGroup[posOfAct]);
                    if (!performerSet.contains(currentGroup[posOfPer]) && !currentGroup[posOfPer].equals("NULL") && !currentGroup[1].toUpperCase().equals("NONE"))
                        performerSet.add(currentGroup[posOfPer]);
                }

                 // Calculate for duration...
                String traceDuration = TimeHelper.CaculateDurationBetweenReturnString(currentTrace[1].split(keySeparateInside)[posOfTime], 
                                                                    currentTrace[currentTrace.length-2].split(keySeparateInside)[posOfTime]);
                String currentRow = traceId.toString() + keySeparateInside + activitySet.size() 
                        + keySeparateInside + performerSet.size() + keySeparateInside + traceDuration;
                globalCurrentViewingTraceList.add(currentRow);
            }
            Variables.averageEventInTrace = sumTotalEvent/(_toPos - _fromPos + 1);
            
            _inputTable = ListStringToTable(globalCurrentViewingTraceList, traceTableHeader);
        } catch (Exception e) {MessageHelper.Error("<ToTraceTable> function: \n" + e.toString());
        }
        return _inputTable;
    }
    
/*
  Convert List String to Table
 */
    private static JTable ListStringToTable(List<String> _inputList, String[] _header)
    {
        JTable table = new JTable();
        try {
            TableController.InitializeBlankTableWithHeader(table, _header);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for(int i=0; i<_inputList.size(); i++)
            {
                String[] currentRow = _inputList.get(i).split(keySeparateInside);
                model.addRow(currentRow);
            }
        } catch (Exception e) {MessageHelper.Error("<ListStringToTable> \n"+e.toString());
        }
         
        return table;
    }
    
 
}
