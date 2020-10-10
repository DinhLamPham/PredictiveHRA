/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Dashboard;

import Helper.Common.MessageHelper;
import Helper.Common.Variables;
import static Helper.Common.Variables.keySeparateInside;
import static Helper.Common.Variables.keyWordSeparate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.JTable;

/**
 *
 * @author phamdinhlam
 */
public class AutoGenerateHelper {
    
    
    
    
    public static List<String> GenerateTraceRandomize(Integer _steps, Integer _traces, String _beginActivity, JTable _tblActivitySet)
    {
        Set<String> inputActSet = ColumnTableToSet(_tblActivitySet, 0); inputActSet.add("END");
        Integer maxTry = inputActSet.size() * 3;
        List<String> outputTraceList = new LinkedList<>(); String currentTrace = "";
        for(Integer i=0; i<_traces; i++)
        {
            currentTrace = "";
            String currentAct = _beginActivity;
            String currentPer = RandomMerberInSet(Variables.activityAffiliationMap.get(currentAct));
            String currentTime = Variables.defaultTimeInsert;
            currentTrace += currentAct + keySeparateInside + currentPer + keySeparateInside + currentTime;
            
            for(Integer j=1; j<_steps; j++)
            {
                String nextAct="UNKNOW", nextPer="UNKNOW", nextTime = Variables.defaultTimeInsert;
                Integer count=0; boolean flagDone = false;
                while(count<maxTry && !flagDone)
                {
                    nextAct =  RandomMerberInSet(Variables.activityRelationMap.get(currentAct));
                    if(inputActSet.contains(nextAct))
                    {
                        flagDone = true;
                        currentAct = nextAct;
                        break;
                    }
                    else 
                        count++;
                }
                if(!flagDone)
                    {nextAct = "UNKNOW"; nextPer = "UNKNOW";}
                else
                {
                    nextPer = RandomMerberInSet(Variables.activityAffiliationMap.get(nextAct));
                    currentTrace += keyWordSeparate + (nextAct + keySeparateInside + nextPer + keySeparateInside + nextTime);
                    if(currentAct.equals("END"))
                        break;
                }
            }
            outputTraceList.add(currentTrace);
        }
        return outputTraceList;
        
    }
    
    private static String RandomMerberInSet(Set<String> _inputSet)
    {
        List<String> inputList = new LinkedList<>();
        Integer setSize = _inputSet.size();
        Random rand = new Random(); String result="";
        Integer randPos = rand.nextInt(setSize);
        for(String member:_inputSet)
            inputList.add(member);
        try {
            result = inputList.get(randPos);
        } catch (Exception e) {
            MessageHelper.Error(e.toString());
        }
        return result;
    }
    
    private static String RandomMerberInStringFormal(String _inputMembers)
    {
        Random rand = new Random();
        String[] arrayMembers = _inputMembers.split(Variables.keySeparateInside);
        Integer randPos = rand.nextInt(arrayMembers.length);
        
        return arrayMembers[randPos];
    }
    
    private static Set<String> ColumnTableToSet(JTable _inputTable, Integer _colIdx)
    {
        Set<String> outputSet = new HashSet<>();
        for(Integer row=0; row< _inputTable.getRowCount(); row++)
        {
            Object currentVal = _inputTable.getValueAt(row, _colIdx);
            if(currentVal!=null)
                outputSet.add(currentVal.toString());
        }
        
        return outputSet;
    }
}
