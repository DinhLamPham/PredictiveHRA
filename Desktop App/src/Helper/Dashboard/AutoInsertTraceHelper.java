/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Dashboard;

import static FormTemplate.CenterPanel.tblInputTraceList;
import Helper.Common.Variables;
import static Helper.Common.Variables.globalXESLogListStrings;
import static Helper.Common.Variables.keySeparateInside;
import static Helper.Common.Variables.keyWordSeparate;
import static Helper.Common.Variables.posOfAct;
import static TableHelper.TableController.InitTableFromListString;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author phamdinhlam
 */
public class AutoInsertTraceHelper {
    public static List<String> GenerateFromLog(Integer _numberStep, String _beginAct, Integer _fromTrace, Integer _totalTrace)
    {
        Integer steps = _numberStep;
        Integer numberTraces = _totalTrace;
        String activity = _beginAct;
        List<String> outputTraceList = new LinkedList<>();
        Integer count = 0;
        for(Integer i=_fromTrace; i<globalXESLogListStrings.size(); i++)
        {
            if(count==_totalTrace)
                break;
            String[] currentTrace = globalXESLogListStrings.get(i).split(keyWordSeparate);
            for(Integer pos=0; pos<currentTrace.length; pos++)
            {
                String event = currentTrace[pos];
                String currentAct = event.split(keySeparateInside)[posOfAct];
                if(currentAct.equals(_beginAct))
                {
                    String currentOutputTrace = event;
                    for(Integer k=pos+1; k<pos+_numberStep; k++)
                        if(k<currentTrace.length)
                            currentOutputTrace += Variables.keyWordSeparate + currentTrace[k];
                    
                    outputTraceList.add(currentOutputTrace);
                    count++;
                    break;
                }
            }
        }

        return outputTraceList;
    }
}
